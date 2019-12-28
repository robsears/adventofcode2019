package com.company;

import java.util.HashMap;

public class IntCodeTests extends IntCode {

	public void testAll() {
		Day02Tests day2 = new Day02Tests();
		Day05Tests day5 = new Day05Tests();
		Day07Tests day7 = new Day07Tests();

		day2.runTests();
		day5.runTests();
		day7.runTests();
	}

	public String compareStates(HashMap<Integer, Integer> map1, HashMap<Integer, Integer> map2) {
		for (Integer address : map1.keySet()) {
			if (!map1.get(address).equals(map2.get(address))) {
				return "Failed";
			}
		}
		return "Passed";
	}

	public String compareIntegers(Integer received, Integer expected) {
		if (expected.equals(received)) { return "Passed"; }
		return String.format("Failed (expected %d, got %d)", expected, received);
	}
}
