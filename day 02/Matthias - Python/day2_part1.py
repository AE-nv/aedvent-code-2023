def is_game_possible(game, max_red, max_green, max_blue):

    for cubes in game.strip().split(';'):
        cubes = cubes.strip()

        for colored_cubes in cubes.split(','):
            colored_cubes = colored_cubes.strip().split(' ')
            number_of_cubes = int(colored_cubes[0])
            color = colored_cubes[1]

            if (color == 'green'):
                if number_of_cubes > max_green:
                    return False
            elif (color == 'red'):
                if number_of_cubes > max_red:
                    return False
            elif (color == 'blue'):
                if number_of_cubes > max_blue:
                    return False
    return True

with open('input.txt') as file:
    lines = [line for line in file]

    sum_of_game_ids = 0
    for line in lines:
        split = line.split(':')
        game_id = int(split[0].split(' ')[1])

        if (is_game_possible(split[1], 12, 13, 14)):
            sum_of_game_ids += game_id

    print(sum_of_game_ids)
            

