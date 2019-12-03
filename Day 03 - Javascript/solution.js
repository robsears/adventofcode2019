wires = [];

// Open the input file and process it.
require('readline').createInterface({
  input: require('fs').createReadStream('input')
}).on('line', function (line) {
  wires.push(line)
}).on('close', function() {
  wire_1 = coordinates(wires[0]);
  wire_2 = coordinates(wires[1]);
  intersections = find_intersections(wire_1, wire_2);

  part_1 = manhattan_minumum(intersections);
  console.log("Part 1: " + part_1);

  part_2 = min_combined_steps(wire_1, wire_2, intersections);
  console.log("Part 2: " + part_2);
});

// Convert directions into an array of coordinates the line has traversed.
//
// Input: String of comma-separated directions
// Output: Array of String objects representing Cartesian coordinates
function coordinates(wire) {
  directions = wire.split(",");
  coords = [];
  x = 0;
  y = 0;
  for (i in directions) {
    units = parseInt(directions[i].replace(/[A-Z]/g, ''))
    switch(directions[i].charAt(0)) {
      case 'D':
        matrix = [0, -1];
        break;
      case 'U':
        matrix = [0, 1];
        break;
      case 'L':
        matrix = [-1, 0];
        break;
      case 'R':
        matrix = [1, 0];
        break;
    }
    for (i = 0; i < units; i++) {
      x += matrix[0];
      y += matrix[1];
      coords.push(x + "," + y);
    }
  }
  return coords;
}

// Find the coordinates where the wires intersect.
//
// Inputs:
//   - wire_1: Array of Strings representing the coordinates that Wire 1
//     traverses
//   - wire_2: Array of Strings representing the coordinates that Wire 2
//     traverses
// Output: Array of Strings representing where the wires intersect.
function find_intersections(wire_1, wire_2) {
  intersects = [];
  for (i in wire_2) {
    if (wire_1.includes(wire_2[i])) {
      intersects.push(wire_2[i]);
    }
  }
  return intersects;
}

// Find the minimum Manhattan distance from the origin.
//
// Input: Array of Strings representing intersection points
// Output: Integer representing the minimum Manhattan distance from the origin.
function manhattan_minumum(intersections) {
  minimum = Number.MAX_VALUE;
  for (i in intersections) {
    coords = intersections[i].split(",");
    manhattan = Math.abs(parseInt(coords[0])) + Math.abs(parseInt(coords[1]));
    if (manhattan < minimum) {
      minimum = manhattan;
    }
  }
  return minimum;
}

// Count the number of steps the wire takes to get to a given point.
//
// Inputs:
//   - wire: Array of Strings representing the coordinates that the wire
//     traverses
//   - point: String representing the coordinates of an intersection
//     traverses
// Output: The number of steps the wire takes to get to a given point.
function count_steps(wire, point) {
  steps = 0;
  while (wire[steps] != point) {
    steps++;
  }
  return steps;
}

// Find the minimum combined steps to reach an intersection point.
//
// Inputs:
//   - wire_1: Array of Strings representing the coordinates that Wire 1
//     traverses
//   - wire_2: Array of Strings representing the coordinates that Wire 2
//     traverses
//   - intersections: Array of Strings representing intersection points
// Output: The minimum combined steps to reach an intersection point.
function min_combined_steps(wire_1, wire_2, intersections) {
  minimum = Number.MAX_VALUE;
  for (i in intersections) {
    intersect = intersections[i];
    // +2 because we need to count the first step of each wire:
    steps = count_steps(wire_1, intersect) + count_steps(wire_2, intersect) + 2;
    if (steps < minimum) {
      minimum = steps;
    }
  }
  return minimum;
}
