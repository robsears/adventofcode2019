#!/usr/bin/env perl
use strict;
use warnings;

# Maintain a list of bodies that exist
my @bodies = ();

# Use a class for defining body relationships
package Body;
sub new {
   my $class = shift;
   my $self = {
      _name     => shift,
      _moons    => [ @_ ], # Array of other Body objects
      _parent   => 0,
      _direct   => 0,
      _indirect => 0
   };
   bless $self, $class;
   return $self;
}
sub getName {
  my( $self ) = @_;
  return $self->{_name};
}
sub addParent {
  my ( $self, $parent ) = @_;
  $self->{_parent} = $parent;
  return;
}
sub countParents {
  my ( $self, $parent ) = @_;
  my $rents = 0;
  if ($self->{_parent} && ($parent != $self)) {
    $rents = 1 + countParents($self->{_parent}, $parent);
  }
  return $rents;
}

# Get an Array of all parent Bodies for a given Body.
sub parentArray {
  my ( $self ) = @_;
  my @parents = ();
  my $parent = $self->{_parent};
  while($parent) {
    push(@parents, $parent);
    $parent = $parent->{_parent};
  }
  return @parents;
}

# Traverse the family tree and find the first shared relative between two bodies
sub sharedParent {
  my ( $you, $san )  = @_;
  my @your_parents   = $you->parentArray();
  my @santas_parents = $san->parentArray();
  for (my $i=0; $i < @your_parents; $i++) {
    my $parent = $your_parents[$i];
    for (my $j=0; $j < @santas_parents; $j++) {
      if ($parent == $santas_parents[$j]) {
        return $parent;
      }
    }
  }
  return 0;
}

# Takes a Body name and looks to see if the object has been created. If so, it
# returns the Body. Otherwise it creates a new Body object, adds it to the
# global manifest and returns it.
sub findOrCreateBy {
  my $body        = $_[0];
  my $body_object = 0;
  if (@bodies) {
    foreach my $var (@bodies) {
      if ($var->getName() eq $body) {
        $body_object = $var;
      }
    }
  }
  if ($body_object == 0) {
    $body_object = new Body($body, ());
    push(@bodies, $body_object);
  }
  return $body_object;
}

# Open the input file and process it.
sub readInput {
  open my $info, 'input' or die "Could not open input file: $!";
  while( my $line = <$info>)  {
    chomp($line); # get rid of the newline
    (my $body, my $moon) = split /\)/, $line;
    my $body_object = findOrCreateBy($body);
    my $moon_object = findOrCreateBy($moon);
    $moon_object->addParent($body_object);
  }
  close $info;
}

# Recursive function to count all orbits of a parent body ("COM") is all that is
# necessary for Part 1
sub partOneSolution {
  my $com = findOrCreateBy("COM");
  my $orbits = 0;
  foreach my $body (@bodies) {
    $orbits += countParents($body, $com);
  }
  print "Part 1: ", $orbits, "\n";
}

# Find the shared relative Body between YOU and SAN, then count the parent
# bodies in each tree. Take away one for the shared parent itself. That's all
# for part 2.
sub partTwoSolution {
  my $you    = findOrCreateBy("YOU");
  my $san    = findOrCreateBy("SAN");
  my $parent = sharedParent($you, $san);
  my $x = countParents($you, $parent) - 1;
  my $y = countParents($san, $parent) - 1;
  print "Part 2: ", $x + $y, "\n";
}

readInput();
partOneSolution();
partTwoSolution();
