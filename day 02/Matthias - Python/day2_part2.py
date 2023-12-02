def get_game_power(game):
    min_red = 0
    min_green = 0
    min_blue = 0

    for cubes in game.strip().split(';'):
        cubes = cubes.strip()

        for colored_cubes in cubes.split(','):
            colored_cubes = colored_cubes.strip().split(' ')
            number_of_cubes = int(colored_cubes[0])
            color = colored_cubes[1]

            if (color == 'green'):
                if number_of_cubes > min_green:
                    min_green = number_of_cubes
            elif (color == 'red'):
                if number_of_cubes > min_red:
                    min_red = number_of_cubes
            elif (color == 'blue'):
                if number_of_cubes > min_blue:
                    min_blue = number_of_cubes
            
    return min_blue * min_red * min_green

with open('input.txt') as file:
    lines = [line for line in file]

    sum_of_power = 0
    for line in lines:
        split = line.split(':')
        game_id = int(split[0].split(' ')[1])
        sum_of_power += get_game_power(split[1])

    print(sum_of_power)
            

