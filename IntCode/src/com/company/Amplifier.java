package com.company;

import java.util.ArrayList;

public class Amplifier {
	public IntCode computer = new IntCode();
	public String name;
	public Integer output;
	public ArrayList<Integer> inputs = new ArrayList<>();
	public Amplifier parent = null;
	public Amplifier child;
	public static String inputFile;

	// Signature for creating the first amp in the series
	public Amplifier(String instructions, String label, Integer phase) {
		name = label;
		inputFile = instructions;
		inputs.add(phase);
	}

	// Signature for creating subsequent amps in the series
	public Amplifier(String instructions, String label, Integer phase, Amplifier parentAmp) {
		name   = label;
		inputFile = instructions;
		parent = parentAmp;
		inputs.add(phase);
		parentAmp.setChild(this);
	}

	public void setChild(Amplifier childAmp) {
		child = childAmp;
	}

	public Boolean hasParent() {
		return parent != null;
	}

	public void run(Integer input) {
		inputs.add(input);
//		System.out.printf("%s\n", toString());
		computer.run(inputFile, inputs);
		output = computer.output;
	}

	public String toString() {
		if (hasParent()) {
			return String.format("Amp %s: Child of Amp %s, inputs %s.", name, parent.name, inputs.toString());
		}
		return String.format("Amp %s: No children, inputs %s.", name, inputs.toString());
	}

}
