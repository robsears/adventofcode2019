/// This function tests whether or not the two adjacent matching digits are not part of a larger
/// group of matching digits
///
/// # Examples
/// ```
/// use adventofcode_2019::no_trips;
/// assert_eq!(no_trips(111122), true);
/// assert_eq!(no_trips(111222), false);
/// ```
pub fn no_trips(number: u32) -> bool {
    for d in 0..10 {
        let num = number.to_string();
        let x = format!("{}{}", d, d).to_string();
        let y = format!("{}{}{}", d, d, d).to_string();

        let dubs:  Vec<&str> = num.matches(&x).collect();
        let trips: Vec<&str> = num.matches(&y).collect();

        if dubs.len() == 1 && trips.len() == 0 {
            return true;
        }
    }
    return false;
}
