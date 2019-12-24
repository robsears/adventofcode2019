package com.company;

import java.util.HashMap;

public class Day02Tests extends IntCodeTests {
	public static String day = "Day 02";

	public static void runTests() {
		HashMap<Integer, Integer> testMemory = new HashMap<>();


		run("tests/day02/test1");
		testMemory.put(0, 2);
		testMemory.put(1, 0);
		testMemory.put(2, 0);
		testMemory.put(3, 0);
		testMemory.put(4, 99);
		System.out.printf("%s Test 1: %s\n", day, compareStates(memory, testMemory));

		testMemory.clear();
		run("tests/day02/test2");
		testMemory.put(0, 2);
		testMemory.put(1, 3);
		testMemory.put(2, 0);
		testMemory.put(3, 6);
		testMemory.put(4, 99);
		System.out.printf("%s Test 2: %s\n", day, compareStates(memory, testMemory));

		testMemory.clear();
		run("tests/day02/test3");
		testMemory.put(0, 2);
		testMemory.put(1, 4);
		testMemory.put(2, 4);
		testMemory.put(3, 5);
		testMemory.put(4, 99);
		testMemory.put(5, 9801);
		System.out.printf("%s Test 3: %s\n", day, compareStates(memory, testMemory));

		testMemory.clear();
		run("tests/day02/test4");
		testMemory.put(0, 30);
		testMemory.put(1, 1);
		testMemory.put(2, 1);
		testMemory.put(3, 4);
		testMemory.put(4, 2);
		testMemory.put(5, 5);
		testMemory.put(6, 6);
		testMemory.put(7, 0);
		testMemory.put(8, 99);
		System.out.printf("%s Test 4: %s\n", day, compareStates(memory, testMemory));

		testMemory.clear();
		run("tests/day02/test5");
		testMemory.put(0, 3500);
		testMemory.put(1, 9);
		testMemory.put(2, 10);
		testMemory.put(3, 70);
		testMemory.put(4, 2);
		testMemory.put(5, 3);
		testMemory.put(6, 11);
		testMemory.put(7, 0);
		testMemory.put(8, 99);
		testMemory.put(9, 30);
		testMemory.put(10, 40);
		testMemory.put(11, 50);
		System.out.printf("%s Test 5: %s\n", day, compareStates(memory, testMemory));

		testMemory.clear();
		run(12, 2, "inputs/day02");
		System.out.printf("%s Test 6: %s\n", day, compareIntegers(memory.get(0), 5098658));
	}
}
