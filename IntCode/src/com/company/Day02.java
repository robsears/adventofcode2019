package com.company;

public class Day02 {
	public static void solve() {
		IntCode computer = new IntCode();
		computer.run(12, 2, "inputs/day02");
		System.out.printf("Part 1: %d\n", computer.memory.get(0));

		boolean breakout = false;
		for (int i=0; i<99; i++) {
			for (int j=0; j<99; j++) {
				computer.run(i, j, "inputs/day02");
				if (computer.memory.get(0) == 19690720) {
					System.out.printf("Part 2: %02d%02d\n", i, j);
					breakout = true;
					break;
				}
			}
			if (breakout) { break; }
		}
	}

	public static void test() {
		Day02Tests testComputer = new Day02Tests();
		testComputer.runTests();
	}
}
