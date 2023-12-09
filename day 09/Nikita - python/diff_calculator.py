from collections import Counter

import numpy as np

def read_file(file_name):
    with open(file_name) as f:
        for line in f.read().split('\n'):
            yield [int(a) for a in line.strip().split(' ')]


if __name__ == '__main__':
    # part 1
    total_sum = 0
    for line in read_file('input.txt'):
        all_last_numbers = []
        while Counter(line).get(0) != len(line):
            all_last_numbers.append(line[-1])
            line = np.ediff1d(line)
        total_sum += sum(all_last_numbers)
    print(total_sum)

    # part 2
    total_sum = 0
    for line in read_file('input.txt'):
        all_first_numbers = []
        while Counter(line).get(0) != len(line):
            all_first_numbers.append(line[0])
            line = np.ediff1d(line)
        number1 = all_first_numbers.pop(-1)
        while len(all_first_numbers) > 0:
            number2 = all_first_numbers.pop(-1)
            number1 = number2 - number1
        total_sum += number1
    print(total_sum)
