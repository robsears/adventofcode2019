def compute(noun, verb)
  input = File.readlines('input').first.split(',').map(&:to_i)
  input[1] = noun
  input[2] = verb
  pos = 0
  while input[pos] != 99
    case input[pos]
    when 1
      input[input[pos+3]] = input[input[pos+1]] + input[input[pos+2]]
    when 2
      input[input[pos+3]] = input[input[pos+1]] * input[input[pos+2]]
    end
    pos += 4
  end
  input[0]
end

def bruteforce
  (0..99).each do |noun|
    (0..99).each do |verb|
      return "#{"%02d" % noun}#{"%02d" % verb}" if compute(noun, verb) == 19690720
    end
  end
end

puts "Part 1: #{compute(12, 2)}"
puts "Part 2: #{bruteforce}"
