import re

def get_digits(line):
    digits = {"one": "1", "two": "2", "three": "3", "four": "4", "five": "5", "six": "6", "seven": "7", "eight": "8", "nine": "9"}
    acc = ""
    extractedDigits = []
    for c in line:
        if c in ["1", "2", "3", "4", "5", "6", "7", "8", "9"]:
            extractedDigits.append(c)
        else:
            acc+=c
            for key in digits.keys():
                if acc.endswith(key):
                    extractedDigits.append(digits[key])

    return extractedDigits

if __name__ == '__main__':
    with open("day01.txt", 'r') as f:
        data = f.readlines()

    result = sum([int(digits[0] + digits[-1]) for digits in [get_digits(line.rstrip()) for line in data]])
    print("result: " + str(result))


