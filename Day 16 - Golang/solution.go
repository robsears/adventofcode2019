package main

import (
    "fmt"
    "strconv"
    "strings"
)

func main() {
  input := "03036732577212944063491565474664"
  // part1(input)
  part2(input)
}

func part1(input string) {
  fmt.Println(iterateInput(input))
}

func part2(input string) {
  x := 0
  offset      := string(input[0:7])
  real_signal := iterateInput(strings.Repeat(input, 1000000))
  if s, err := strconv.Atoi(offset); err == nil {
    x = s
  }
  fmt.Println(string(real_signal[x:x+8]))
}

func iterateInput(input string) string {
  length := len(input)
  for i := 0; i < 100; i++ {
    next_iteration := make([]byte, length)
    for i := range input {
      mask := renderMask(i, length)
      next_iteration[i] = applyMask(mask, input)
    }
    input = string(next_iteration)
    fmt.Println(i)
  }
  return string(input[0:8])
}

func applyMask(mask []int, input string) byte {
  val := 0;
  for i := range input {
    if s, err := strconv.Atoi(string(input[i])); err == nil {
      val += s * mask[i]
    }
  }
  s := strconv.Itoa(val)
  return s[len(s)-1]
}

func renderMask(pos int, length int) []int {
  base_pattern := []int {0, 1, 0, -1}
  temp_pattern := make([]int, length+1)
  next_pattern := make([]int, length)
  for i := 0; i <= length; i++ {
    for _, e := range base_pattern {
      for k:=0; k <= pos; k++ {
        temp_pattern[i] = e
        if (i == length) {
          for l := 1; l < len(temp_pattern); l++ {
            next_pattern[l-1] = temp_pattern[l]
          }
          return next_pattern
        } else {
          i++
        }
      }
    }
    i--
  }
  return next_pattern
}
