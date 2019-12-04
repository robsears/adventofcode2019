mod part_2;
pub use part_2::no_trips;

/// This function tests whether an input has two adjacent digits that are the same.
///
/// # Examples
/// ```
/// use adventofcode_2019::adjacent_digits;
/// let chars: Vec<char> = 111111.to_string().chars().collect();
/// assert_eq!(adjacent_digits(&chars), true);
///
/// let chars: Vec<char> = 123456.to_string().chars().collect();
/// assert_eq!(adjacent_digits(&chars), false);
/// ```
pub fn adjacent_digits<'a>(chars: &'a Vec<char>) -> bool {
    for c in 0..5 {
        if chars[c] == chars[c+1] {
            return true;
        }
    }
    return false;
}

/// This function tests whether, reading right to left, the digits only ever increase or stay the
/// same.
///
/// # Examples
/// ```
/// use adventofcode_2019::never_decrease;
/// let chars: Vec<char> = 123456.to_string().chars().collect();
/// assert_eq!(never_decrease(&chars), true);
///
/// let chars: Vec<char> = 654321.to_string().chars().collect();
/// assert_eq!(never_decrease(&chars), false);
/// ```
pub fn never_decrease<'a>(chars: &'a Vec<char>) -> bool {
    for c in 0..5 {
        if chars[c] > chars[c+1] {
            return false;
        }
    }
    return true;
}
