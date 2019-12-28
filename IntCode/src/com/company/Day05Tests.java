package com.company;

import java.util.HashMap;

public class Day05Tests extends IntCodeTests {

	public String day = "Day 05";

	public void runTests() {
		HashMap<Integer, Integer> testMemory = new HashMap<>();

		// Should accept 420 as the input, and store that to the output
		run("tests/day05/test1", 420);
		System.out.printf("%s Test 1: %s\n", day, compareIntegers(output, 420));

		run("tests/day05/test2");
		testMemory.put(0, 1002);
		testMemory.put(1, 4);
		testMemory.put(2, 3);
		testMemory.put(3, 4);
		testMemory.put(4, 99);
		System.out.printf("%s Test 2: %s\n", day, compareStates(memory, testMemory));

		run("tests/day05/test3", 8);
		System.out.printf("%s Test 3: %s\n", day, compareIntegers(output, 1));

		run("tests/day05/test3", 9);
		System.out.printf("%s Test 4: %s\n", day, compareIntegers(output, 0));

		run("tests/day05/test4", 9);
		System.out.printf("%s Test 5: %s\n", day, compareIntegers(output, 0));

		run("tests/day05/test4", 7);
		System.out.printf("%s Test 6: %s\n", day, compareIntegers(output, 1));

		run("tests/day05/test5", 9);
		System.out.printf("%s Test 7: %s\n", day, compareIntegers(output, 0));

		run("tests/day05/test5", 8);
		System.out.printf("%s Test 8: %s\n", day, compareIntegers(output, 1));

		run("tests/day05/test6", 9);
		System.out.printf("%s Test 9: %s\n", day, compareIntegers(output, 0));

		run("tests/day05/test6", 7);
		System.out.printf("%s Test 10: %s\n", day, compareIntegers(output, 1));

		run("tests/day05/test7", 0);
		System.out.printf("%s Test 11: %s\n", day, compareIntegers(output, 0));

		run("tests/day05/test7", 5);
		System.out.printf("%s Test 12: %s\n", day, compareIntegers(output, 1));

		run("tests/day05/test8", 0);
		System.out.printf("%s Test 13: %s\n", day, compareIntegers(output, 0));

		run("tests/day05/test8", 5);
		System.out.printf("%s Test 14: %s\n", day, compareIntegers(output, 1));

		run("tests/day05/test9", 7);
		System.out.printf("%s Test 15: %s\n", day, compareIntegers(output, 999));

		run("tests/day05/test9", 8);
		System.out.printf("%s Test 16: %s\n", day, compareIntegers(output, 1000));

		run("tests/day05/test9", 9);
		System.out.printf("%s Test 17: %s\n", day, compareIntegers(output, 1001));

		run("inputs/day05", 1);
		System.out.printf("%s Test 18: %s\n", day, compareIntegers(output, 7839346));

		run("inputs/day05", 5);
		System.out.printf("%s Test 19: %s\n", day, compareIntegers(output, 447803));
	}
}
