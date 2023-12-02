with open('day1_part1_input.txt') as file:
    lines = [line for line in file]

    total = 0
    for line in lines:

        first_number = None
        last_number = None
        for character in line:

            if character.isnumeric():
                if first_number == None:
                    first_number = character
                    last_number = character
                else:
                    last_number = character
                    
        number_for_line = int(first_number + last_number)
        total += number_for_line

    print(total)