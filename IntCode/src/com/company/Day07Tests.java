package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Day07Tests extends IntCodeTests {
	public static String day = "Day 07";
	public static ArrayList<Integer> sequence = new ArrayList<>();

	public void runTests() {

		initializeSequence(new Integer[] { 4, 3, 2, 1, 0 });
		AmpStack stack = new AmpStack(sequence, "tests/day07/test1");
		stack.run(0);
		System.out.printf("%s Test 1: %s.\n", day, compareIntegers(stack.getOutput(), 43210));

		initializeSequence(new Integer[] { 0, 1, 2, 3, 4 });
		stack = new AmpStack(sequence, "tests/day07/test2");
		stack.run(0);
		System.out.printf("%s Test 2: %s.\n", day, compareIntegers(stack.getOutput(), 54321));

		initializeSequence(new Integer[] { 1, 0, 4, 3, 2 });
		stack = new AmpStack(sequence,"tests/day07/test3");
		stack.run(0);
		System.out.printf("%s Test 3: %s.\n", day, compareIntegers(stack.getOutput(), 65210));

//		initializeSequence(new Integer[] { 9,8,7,6,5 });
//		AmpStack stack = new AmpStack(sequence, "tests/day07/test4");
//		stack.loop(0);
//		System.out.printf("%s Test 4: %s.\n", day, compareIntegers(stack.getOutput(), 139629729));

	}

	public void initializeSequence(Integer[] testSequence) {
		sequence.clear();
		sequence.addAll(Arrays.asList(testSequence));
	}
}
