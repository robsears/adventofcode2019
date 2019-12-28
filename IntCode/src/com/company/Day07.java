package com.company;

import java.util.ArrayList;
import java.util.Collections;

public class Day07 {

	public static ArrayList<Integer> sequence = new ArrayList<>();

	public Day07() {
		sequence.add(0);
		sequence.add(1);
		sequence.add(2);
		sequence.add(3);
		sequence.add(4);
	}

	public static void solve() {
		// Use a fuzzing algorithm. Not the fastest or most efficient, but the laziest way to probably get the right answer.
		part1();
	}

	public static void test() {
		Day07Tests challenge = new Day07Tests();
		challenge.runTests();
	}

	public static void part1() {
		// Considered implementing Heap's algorithm to get all permutations of the phase setting array, but too lazy. A
		// fuzzer is less code and has a high likelihood of generating the complete set.
		Integer maxThrust = 0;
		for (int i=0; i<1000; i++) {
			AmpStack stack = new AmpStack(sequence, "inputs/day07");
			stack.run(0);
			Integer thrust = stack.getOutput();
			maxThrust = (thrust > maxThrust) ? thrust : maxThrust;
			Collections.shuffle(sequence);
		}
		System.out.printf("Part 1: %d\n", maxThrust);
	}


}
