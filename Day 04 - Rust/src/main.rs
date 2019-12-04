use adventofcode_2019::*;

/// Run the main program.
fn main() {
    let mut part_1 = 0;
    let mut part_2 = 0;
    for n in 138307..=654504 {
        let chars: Vec<char> = n.to_string().chars().collect();
        if adjacent_digits(&chars) && never_decrease(&chars) {
            part_1 += 1;
            if no_trips(n) {
                part_2 += 1;
            }
        }
    }
    println!("Part 1: {}", part_1);
    println!("Part 2: {}", part_2);
}
