import re


def sum_cubes(line, color):
    cubes = re.findall(r'(\d+) ' + color, line)
    return sum([int(c) for c in cubes])


def run_game1(line):
    splitprefix = line.split(":")
    splitgames = splitprefix[1].split(";")
    for game in splitgames:
        blue = sum_cubes(game, 'blue')
        red = sum_cubes(game, 'red')
        green = sum_cubes(game, 'green')
        if red > 12 or green > 13 or blue > 14:
            return 0
    game_number = int(re.search(r'Game (\d+)', line).group(1))
    return game_number


def run_game2(line):
    splitprefix = line.split(":")
    splitgames = splitprefix[1].split(";")
    maxRed, maxGreen, maxBlue = 0, 0, 0
    for game in splitgames:
        blue = sum_cubes(game, 'blue')
        red = sum_cubes(game, 'red')
        green = sum_cubes(game, 'green')
        maxRed = max(maxRed, red)
        maxBlue = max(maxBlue, blue)
        maxGreen = max(maxGreen, green)
    return maxRed * maxBlue * maxGreen


with open("input.txt", "r") as file:
    lines = file.readlines()
    part1, part2 = 0, 0
    for line in lines:
        part1 += run_game1(line)
        part2 += run_game2(line)
    print(part1)
    print(part2)
