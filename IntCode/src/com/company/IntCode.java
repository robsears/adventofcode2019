package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

public class IntCode {

	public static HashMap<Integer, Integer> memory = new HashMap<>();
	public static Integer input              = 0;
	public static Integer output             = 0;
	public static Integer instructionPointer = 0;

	public static void run(String instructionFile) {
		parseInput(instructionFile);
		process();
	}

	public static void run(String instructionFile, Integer userInput) {
		parseInput(instructionFile);
		process(userInput);
	}

	public static void run(int noun, int verb, String instructionFile) {
		parseInput(instructionFile);
		memory.put(1, noun);
		memory.put(2, verb);
		process();
	}

	public static void process(Integer userInput) {
		input = userInput;
		process();
	}

	public static void process() {
		instructionPointer = 0;
		while (memory.get(instructionPointer) != 99) {
			Instruction instruction = new Instruction();
			instruction.execute();
		}
	}

	public static void printState() {
		for (Integer address : memory.keySet()) {
			System.out.printf("%d,", memory.get(address));
		}
		System.out.println("");
	}

	// Read the input from an external file into a class variable for later use.
	public static void parseInput(String fileName) {
		memory = new HashMap<>();
		String file = String.format("/home/work/Sites/adventofcode2019/IntCode/src/com/company/%s", fileName);
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			stream.forEach( (line) -> {
				Integer address = 0;
				String[] codes = line.split(",");
				for (String opcode : codes) {
					memory.put(address, Integer.parseInt(opcode));
					address += 1;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
