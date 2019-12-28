package com.company;

public class Instruction extends IntCode {
	public static Integer opcode           = 0;
	public static Integer pointerIncrement = 0;

	public static Integer param1;
	public static Integer param2;
	public static Integer param3;

	public Instruction() {
		int param1Mode, param2Mode;
		String paddedInstruction = String.format("%05d", memory.get(instructionPointer));
		opcode = Integer.parseInt(paddedInstruction.substring(3,5));

		switch (opcode) {
		// Math opcodes
		case 1: case 2: case 7: case 8:
			pointerIncrement  = 4;
			param1Mode  = Integer.parseInt(paddedInstruction.substring(2,3));
			param2Mode  = Integer.parseInt(paddedInstruction.substring(1,2));
			param1      = evaluate(memory.get(instructionPointer + 1), param1Mode);
			param2      = evaluate(memory.get(instructionPointer + 2), param2Mode);
			param3      = memory.get(instructionPointer + 3);
			break;

		// Read Input
		case 3:
			pointerIncrement  = 2;
			param1 = memory.get(instructionPointer + 1);
			break;

		// Store output
		case 4:
			pointerIncrement  = 2;
			param1Mode  = Integer.parseInt(paddedInstruction.substring(2,3));
			param1 = evaluate(memory.get(instructionPointer + 1), param1Mode);
			break;

		// Jump opcodes
		case 5: case 6:
			pointerIncrement = 0; // to offset the incrementer in the IntCode loop.
			param1Mode  = Integer.parseInt(paddedInstruction.substring(2,3));
			param2Mode  = Integer.parseInt(paddedInstruction.substring(1,2));
			param1      = evaluate(memory.get(instructionPointer + 1), param1Mode);
			param2      = evaluate(memory.get(instructionPointer + 2), param2Mode);
			break;
		}
	}

	public void execute() {
		switch (opcode) {
		case 1: // Add params together, store to param3
			memory.put(param3, param1 + param2);
			break;
		case 2: // Multiply params together, store to param3
			memory.put(param3, param1 * param2);
			break;
		case 3: // Accept an input, store in param1
			if (inputs.size() > 0) {
				memory.put(param1, inputs.remove(0));
			} else { // If there are no inputs in the stack, then pause the computation
				pause = true;
				pointerIncrement  = 0;
			}
			break;
		case 4: // Write the value of param1 to the output
			output = param1;
			break;
		case 5: // Jump-if-true
			if (!param1.equals(0)) { instructionPointer = param2; }
			else { instructionPointer += 3; }
			break;
		case 6: // Jump if false
			if (param1.equals(0)) { instructionPointer = param2; }
			else { instructionPointer += 3; }
			break;
		case 7: // Less than
			memory.put(param3, ((param1 < param2) ? 1 : 0));
			break;
		case 8: // Equals
			memory.put(param3, (param1.equals(param2) ? 1 : 0));
			break;
		}
		instructionPointer += pointerIncrement;
	}

	public Integer evaluate(Integer value, Integer mode) {
		switch (mode) {
		case 0:
			return memory.get(value);
		case 1:
			return value;
		default:
			return 0;
		}
	}

	public String toString() {
		return String.format("OpCode: %d, Params: %d, %d, %d", opcode, param1, param2, param3);
	}
}
