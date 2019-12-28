package com.company;

import java.util.ArrayList;

public class AmpStack {
	public Amplifier firstAmp;
	public Amplifier lastAmp;
	public ArrayList<Amplifier> array = new ArrayList<>();
	public Integer output = 0;
	public Integer pauses = 0;
	public String inputFile;

	public AmpStack(ArrayList<Integer> sequence, String instructions) {
		int a            = "A".charAt(0);
		inputFile        = instructions;
		firstAmp         = new Amplifier(inputFile, "A", sequence.get(0));
		Amplifier parent = firstAmp;
		array.add(firstAmp);
		for (int i=1; i < sequence.size(); i++) {
			Amplifier amp = new Amplifier(inputFile, String.valueOf((char)(a+i)), sequence.get(i), parent);
			array.add(amp);
			parent = amp;
		}
		lastAmp = array.get(array.size()-1);
	}

	public void run(Integer input) {
		pauses = 0;
		for (int i=0; i < array.size(); i++) {
			Amplifier amp = array.get(i);
			amp.run(input);
			if (amp.computer.pause) {
//				System.out.printf("%s is paused, moving on\n", amp.name);
				pauses++;
			} else {
//				System.out.printf("%s is halted, dropping from array.\n", amp.name);
				array.remove(i);
				i--;
			}
			input = amp.output;
			output = amp.output;
		}
	}

	public void loop(Integer input) {
		while (true) {
			run(input);
			if (pauses == 0) { break;	}
			input = output;
		}
		System.out.printf("Last Amp %s has an output of %d\n", lastAmp.name, lastAmp.output);
	}

	public Integer getOutput() {
		return output;
	}
}
