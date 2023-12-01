# part 1
import re

with open("input.txt", "r") as file:
    lines = file.readlines()
    sum = 0
    for line in lines:
        digits = ""
        for char in line:
            if char.isdigit():
                digits += char
        d = int(digits[0]+digits[len(digits)-1])
        sum += d
    print(sum)
    
# part 2
def replace_words_with_numbers(input_string):
    replacements = {
        "one": "1",
        "two": "2",
        "three": "3",
        "four": "4",
        "five": "5",
        "six": "6",
        "seven": "7",
        "eight": "8",
        "nine": "9"
    }
    result = ''
    for match in re.findall(r"(?=(\d|one|two|three|four|five|six|seven|eight|nine))", input_string):
        for word, number in replacements.items():
            result += match.replace(word, number)

    return result

with open("input.txt", "r") as file:
    lines = file.readlines()
    sum = 0
    for line in lines:
        digits = ""
        for char in replace_words_with_numbers(line):
            if char.isdigit():
                digits += char
        d = int(digits[0]+digits[len(digits)-1])
        sum += d
    print(sum)