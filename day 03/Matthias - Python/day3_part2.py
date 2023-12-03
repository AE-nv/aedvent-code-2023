class NumberInformation:
    number = 0
    start = 0
    end = 0
    is_possible_part_of_gear = False

    def __init__(self, number, start, end):
        self.number = number
        self.start = start
        self.end = end

    def mark_as_possible_part_of_gear(self):
        self.is_possible_part_of_gear = True

    def reset(self):
        self.is_possible_part_of_gear = False

    def __repr__(self):
        return str(self.number) + " - start: " + str(self.start) + " - end: " + str(self.end)
    
def reset_possible_parts_of_gear(numbers):
    for line_index in numbers.keys():
        for number in numbers[line_index]:
            number.reset()

def calculate_gear_ratio(numbers):
    gear_ratio = 1
    sum = 0
    for line_index in numbers.keys():
        for number in numbers[line_index]:
            if number.is_possible_part_of_gear:
                sum += 1
                gear_ratio *= number.number

    if sum != 2:
        return 0
    
    return gear_ratio

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

def get_gear_ratio(numbers, x, y):
    reset_possible_parts_of_gear(numbers)
    for i in range(-1, 2):
        for j in range(-1, 2):
            part_number = is_part_number_on_coordinates(numbers, x + i, y + j)
            if part_number is not None:
                part_number.mark_as_possible_part_of_gear()

    gear_ratio = calculate_gear_ratio(numbers)
    if gear_ratio is None:
        return None
    
    return gear_ratio

def is_part_number_on_coordinates(numbers, x, y):
    if y not in numbers:
        return
    
    for number in numbers[y]:
        if x >= number.start and x <= number.end:
            return number
        
    return None

def day3(lines):
    gear_ratio_sum = 0
    numbers = parse_numbers(lines)
    for line_index, line in enumerate(lines):
        for character_index, character in enumerate(line):
            if character.isnumeric() == False and character != '.' and character.isspace() == False:
                    gear_ratio = get_gear_ratio(numbers, character_index, line_index)
                    if gear_ratio is not None:
                        gear_ratio_sum += gear_ratio

    print(gear_ratio_sum)    

with open('input.txt') as file:
    lines = [line for line in file]
    day3(lines)

