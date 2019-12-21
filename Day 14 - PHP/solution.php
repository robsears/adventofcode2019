<?php

// Initialize some global arrays to hold some data structures
$reactions = array();
$processed = array();
$materials = array();
$leftovers = array();

// A data structure that associates an arbitrary number of inputs and volumes
// to an output material and volume
class Reaction {
  public $inputs = array();
  public $output = array();
  function __construct($inputs, $output) {
    foreach (preg_split('[, ]', $inputs) as $part) {
      list($amount, $name) = preg_split('[ ]', $part);
      array_push($this->inputs, array(
        'name'  => $name,
        'value' => intval($amount)
      ));
    }
    list($amount, $name) = preg_split('[ ]', $output);
    $this->output = array('name' => $name, 'value' => intval($amount));
  }
}

// Open the input file and read it into a data structure.
if ($fh = fopen("input", "r")) {
  while (($line = fgets($fh)) !== false) {
    list($inputs, $output) = preg_split('[ =\> ]', rtrim($line));
    $rxn = new Reaction($inputs, $output);
    $reactions[$rxn->output['name']] = $rxn;
  }
  fclose($fh);
} else {
  die("Could not open the input file.");
}

// Test if a reaction is created by ORE. Returns Boolean indicating whether or
// not the reaction has ORE as an input.
function createdFromOre($reaction) {
  return ($reaction->inputs[0]['name'] == 'ORE');
}

// Add a reaction to a computational layer and mark it as processed.
function processReaction($material, $reaction, $layer, $stages) {
  global $processed, $stages;
  if (!in_array($layer, array_keys($stages))) {
    $stages[$layer] = array();
  }
  if (!in_array($material, $processed)) {
    array_push($stages[$layer], $reaction);
    array_push($processed, $material);
  }
}

// Add a material and quantity to the materials array.
function addToMaterialsList($material, $amount) {
  global $materials;
  if (!in_array($material, array_keys($materials))) {
    $materials[$material] = 0;
  }
  $materials[$material] += $amount;
}

// Handle any leftovers from a reaction. If there are enough leftovers to
// decompose into simpler materials, then do that and update the materials
// array.
function handleLeftovers($produceable) {
  global $materials, $leftovers, $reactions;
  if ($materials[$produceable] > 0) {
    if (!in_array($produceable, array_keys($leftovers))) {
      $leftovers[$produceable] = 0;
    }
    $leftovers[$produceable] += $materials[$produceable];
    if ($produceable == 'FUEL') { return; }
    if ($leftovers[$produceable] >= $reactions[$produceable]->output['value']) {
      $reactions_needed = floor($leftovers[$produceable] / $reactions[$produceable]->output['value']);
      foreach ($reactions[$produceable]->inputs as $input) {
        $materials[$input['name']] += $reactions_needed * $input['value'];
      }
    }
  }
}

// Get all of the materials that can be produced within a computational layer.
function getProduceables($layer, $stages) {
  $produceables = array();
  foreach ($stages[$layer] as $rxn) {
    array_push($produceables, $rxn->output['name']);
  }
  return $produceables;
}

// Perform a reverse topological sort on the collection of reactions.
// Materials that can be composed from ORE are the simplest, and materials that
// can only be composed of things composed of ORE (and so on), are more complex.
// A reverse topological sort orders all of the reactions from the most complex
// (the one that produces FUEL) down to the least complex (materials created
// from ORE). This ordering allows us to systematically decompose the inputs of
// a complex reaction into its simplest components.
function reverseTopologicalSortReactions() {
  global $processed, $reactions, $stages;
  $layer = 0;
  $stages = array();
  while (count($processed) < count(array_keys($reactions))) {
    $processed_in_this_layer = array();
    foreach ($reactions as $output => $reaction) {
      if (!in_array($output, $processed)) {
        if (createdFromOre($reaction)) {
          array_push($processed_in_this_layer, $reaction);
        } else {
          $composable = true;
          foreach ($reaction->inputs as $input) {
            if (!in_array($input['name'], $processed)) { $composable = false; }
          }
          if ($composable) {
            array_push($processed_in_this_layer, $reaction);
          }
        }
      }
    }
    foreach ($processed_in_this_layer as $reaction) {
      processReaction($reaction->output['name'], $reaction, $layer, $stages);
    }
    $layer++;
  }
  return array_reverse($stages);
}

// Calculate the amount of ORE needed to produce a given amount of FUEL.
// Parameters: $amount_needed = the amount of FUEL to produce.
// Returns: An integer representing the amount of ORE needed to produce the
//          desired amount of FUEL.
function calculateOreNeeds($amount_needed) {
  global $materials, $reactions, $stages, $processed, $materials, $leftovers;

  $processed = array();
  $materials = array();
  $leftovers = array();

  $stages = reverseTopologicalSortReactions();
  $materials['FUEL'] = $amount_needed;

  for ($layer=0; $layer<count(array_keys($stages))-1; $layer++) {
    foreach (getProduceables($layer, $stages) as $produceable) {
      $reaction = $reactions[$produceable];
      $reactions_needed = ceil($materials[$produceable] / $reaction->output['value']);
      foreach ($reaction->inputs as $input) {
        addToMaterialsList($input['name'], $reactions_needed * $input['value']);
      }
      $materials[$produceable] = $reactions_needed * $reaction->output['value'] - $materials[$produceable];
      handleLeftovers($produceable);
      unset($materials[$produceable]);
    }
  }

  $ore_needed = 0;
  foreach ($materials as $material => $amt) {
    $reaction = $reactions[$material];
    $reactions_needed = ceil($amt/$reaction->output['value']);
    $ore_needed += $reactions_needed * $reaction->inputs[0]['value'];
  }
  return $ore_needed;
}

// Calculate the amount of fuel that can be produced by 1T units of ORE.
// We can use the answer from part 1 as a baseline. No need for brute force here
// if we know our quick mafs ;-)
// Parameters: $part_1_answer - the answer to Part 1.
// Returns: An integer representing how much FUEL can be produced.
function calculateFuelProduced($part_1_answer) {
  $ore_cap = 1000000000000; // 1 trillion ORE
  $x = floor($ore_cap / $part_1_answer);
  $y = $ore_cap / calculateOreNeeds($x);
  return round($x*$y) - 1;
}

// Part 1:
$ore_needed = calculateOreNeeds(1);
print("Part 1: " . $ore_needed . "\n");

// Part 2:
$fuel_produced = calculateFuelProduced($ore_needed);
print("Part 2: " . $fuel_produced . "\n");

?>
