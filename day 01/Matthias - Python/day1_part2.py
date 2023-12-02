numbers_as_text = ["one", "two", "three", "four", "five", "six", "seven", "eight", "nine"]

with open('day1_part2_input.txt') as file:
    lines = [line for line in file]

    total = 0
    for line in lines:

        first_number = None
        last_number = None
        current_word_character = ""

        for character in line:
            if character.isnumeric():
                current_word_character = ""
                if first_number == None:
                    first_number = character
                    last_number = character
                else:
                    last_number = character
            else:
                current_word_character += character

                for index,  n in enumerate(numbers_as_text):
                    if current_word_character.endswith(n) :
                        number_as_text = str(index + 1)
                        current_word_character = current_word_character[-1]

                        if first_number == None:
                            first_number = number_as_text
                            last_number = number_as_text
                        else:
                            last_number = number_as_text

                
                    
        number_for_line = int(first_number + last_number)
        print(number_for_line)
        total += number_for_line

    print(total)