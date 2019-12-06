def compute(input = nil)
  instructions    = File.read_lines("input").first.split(',').map { |s| s.to_i }
  pos             = 0
  output          = nil
  while instructions[pos] != 99
    instruction = interpret_instruction(instructions[pos])
    case instruction[:opcode]
    when 1 # Add the next two value, put into the third position
      instructions[instructions[pos+3]] = process_instruction(instructions, pos+1, instruction[:mode_1]) + process_instruction(instructions, pos+2, instruction[:mode_2])
      pos += 4
    when 2 # Multiply the next two value, put into the third position
      instructions[instructions[pos+3]] = process_instruction(instructions, pos+1, instruction[:mode_1]) * process_instruction(instructions, pos+2, instruction[:mode_2])
      pos += 4
    when 3 # Accept an input, save it to the address of the next position
      instructions[instructions[pos+1]] = input
      pos += 2
    when 4 # Read from the address of the next position, output it
      output = process_instruction(instructions, pos+1, instruction[:mode_1])
      pos += 2
    when 5 # Jump if true
      if !process_instruction(instructions, pos+1, instruction[:mode_1]).zero?
        pos = process_instruction(instructions, pos+2, instruction[:mode_2])
      else
        pos += 3
      end
    when 6 # Jump if false
      if process_instruction(instructions, pos+1, instruction[:mode_1]).zero?
        pos = process_instruction(instructions, pos+2, instruction[:mode_2])
      else
        pos += 3
      end
    when 7 # Less than
      if process_instruction(instructions, pos+1, instruction[:mode_1]) < process_instruction(instructions, pos+2, instruction[:mode_2])
        instructions[instructions[pos+3]] = 1
      else
        instructions[instructions[pos+3]] = 0
      end
      pos += 4
    when 8 # Equals
      if process_instruction(instructions, pos+1, instruction[:mode_1]) == process_instruction(instructions, pos+2, instruction[:mode_2])
        instructions[instructions[pos+3]] = 1
      else
        instructions[instructions[pos+3]] = 0
      end
      pos += 4
    end
  end
  output
end

def process_instruction(data, position, mode)
  if mode == 0
    return data[data[position]]
  else
    return data[position]
  end
end

def interpret_instruction(instruction)
  digits = instruction.to_s.rjust(5, '0').chars
  {
    opcode: "#{digits[3]}#{digits[4]}".to_i,
    mode_1: digits[2].to_i,
    mode_2: digits[1].to_i,
    mode_3: digits[0].to_i
  }
end

puts "Part 1: #{compute(input: 1)}"
puts "Part 2: #{compute(input: 5)}"
