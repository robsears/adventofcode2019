package com.company;

public class Day05 {
	public static void solve() {
		IntCode computer = new IntCode();
		computer.run("inputs/day05", 1);
		System.out.printf("Part 1: %d\n", computer.output);

		computer.run("inputs/day05", 5);
		System.out.printf("Part 2: %d\n", computer.output);
	}

	public static void test() {
		Day05Tests challenge = new Day05Tests();
		challenge.runTests();
	}
}
