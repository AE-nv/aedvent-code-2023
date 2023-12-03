class NumberInformation:
    number = 0
    start = 0
    end = 0
    is_part_number = False

    def __init__(self, number, start, end):
        self.number = number
        self.start = start
        self.end = end

    def mark_as_part_number(self):
        self.is_part_number = True

    def __repr__(self):
        return str(self.number) + " - start: " + str(self.start) + " - end: " + str(self.end)

def parse_numbers(lines):
    numbers_per_line = {}
    line_index = 0

    for line in lines:
        numbers_per_line[line_index] = []
        number_index = 0
        current_number = ""
        start_of_number_index = -1

        for character in line:
            if character.isnumeric():
                if start_of_number_index == -1:
                    start_of_number_index = number_index
                current_number += character
            else:
                if current_number != "":
                    number_information = NumberInformation(int(current_number), start_of_number_index, number_index - 1)
                    numbers_per_line[line_index].append(number_information)
                    current_number = ""
                    start_of_number_index = -1
            number_index += 1

        line_index += 1

    return numbers_per_line

def sum_part_numbers(numbers):
    sum = 0

    for key in numbers.keys():
        for number in numbers[key]:
            if number.is_part_number:
                sum += number.number
    
    return sum

def mark_part_numbers(numbers, x, y):

    for i in range(-1, 2):
        for j in range(-1, 2):
            mark_part_number_on_coordinates(numbers, x + i, y + j)

def mark_part_number_on_coordinates(numbers, x, y):
    if y not in numbers:
        return
    
    for number in numbers[y]:
        if x >= number.start and x <= number.end:
            number.mark_as_part_number()

def day3(lines):
    numbers = parse_numbers(lines)
    for line_index, line in enumerate(lines):
        for character_index, character in enumerate(line):
            if character.isnumeric() == False and character != '.' and character.isspace() == False:
                    mark_part_numbers(numbers, character_index, line_index)

    print(sum_part_numbers(numbers))    

with open('input.txt') as file:
    lines = [line for line in file]
    day3(lines)

