def fuel(inpt)
  (inpt / 3) - 2
end

def recursefuel(inpt)
  i = inpt
  total_fuel = 0
  while fuel(i) > 0
    i = fuel(i)
    total_fuel += i
  end
  total_fuel
end

input = File.readlines('input').map(&:chomp).map(&:to_i)

# Part 1
sol_1 = input.map{|i| fuel(i)}.inject(0, :+)

# Part 2
sol_2 = input.map{|i| recursefuel(i)}.inject(0, :+)

puts "Part 1: #{sol_1}"
puts "Part 2: #{sol_2}"
