
if __name__ == '__main__':
    with open("day02.txt", 'r') as f:
        data = f.readlines()

    games = {}
    for line in data:
        game, cube_sets = line.rstrip().split(":")
        game_index = game.split()[1]
        games[game_index] = []
        for cube_set in cube_sets.split(";"):
            game = {}
            game["red"] = 0
            game["blue"] = 0
            game["green"] = 0
            games[game_index].append(game)
            cubes = cube_set.split(",")
            for cube in cubes:
                number = cube.split()[0]
                if "red" in cube:
                    games[game_index][-1]["red"] = int(number)
                elif "blue" in cube:
                    games[game_index][-1]["blue"] = int(number)
                elif "green" in cube:
                    games[game_index][-1]["green"] = int(number)

    possible_games = []
    for game_index, game in games.items():
        possible = True
        for iteration in game:
            if iteration["red"] > 12 or iteration["green"] > 13 or iteration["blue"] > 14:
                possible = False
                break
        if possible:
            possible_games.append(int(game_index))

    powers = []
    for game_index, game in games.items():
        red = max([iteration["red"] for iteration in game])
        blue = max([iteration["blue"] for iteration in game])
        green = max([iteration["green"] for iteration in game])
        powers.append(red * blue * green)

    print("PART 1:" + str(sum(possible_games)))
    print("PART 2:" + str(sum(powers)))
