import sys

input = open(sys.argv[1]).readlines()
part = int(sys.argv[2])

calibration_value_sum = 0

if part == 1:
    for line in input:
        digits = [int(char) for char in line if char.isnumeric()]
        calibration_value_sum += 10*digits[0]+digits[-1]        

if part == 2:
    digit_map = {
        'one': 1,
        'two': 2,
        'three': 3,
        'four': 4,
        'five': 5,
        'six': 6,
        'seven': 7,
        'eight': 8,
        'nine': 9
    }

    for line in input:
        digits = []
        for i in range(len(line)):
            if line[i].isnumeric():
                digits.append(int(line[i]))
            else:
                for digit in digit_map:
                    if line[i:i+len(digit)] == digit:
                        digits.append(digit_map[digit])
                        i += len(digit)
        calibration_value_sum += digits[0]*10+digits[-1]

print(calibration_value_sum)