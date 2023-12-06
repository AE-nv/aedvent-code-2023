import sys

input = open(sys.argv[1]).readlines()

# parsing
games = []
for line in input:
    game, sets = line.split(':')
    game_id = int(game.split()[-1])
    cube_sets = [[{'color': color.strip().split()[1], 'number': int(color.strip().split()[0])} for color in cube_set.split(',')] for cube_set in sets.split(';')]

    games.append({'id': game_id, 'cube_sets': cube_sets})

# part 1
max_reds = 12
max_greens = 13
max_blues = 14

game_id_sum = 0

def count_color(game, color):
    return max([c['number'] for cube_set in game['cube_sets'] for c in cube_set if c['color'] == color])    

for game in games:
    reds = count_color(game, 'red')
    blues = count_color(game, 'blue')
    greens = count_color(game, 'green')
    if reds <= max_reds and greens <= max_greens and blues <= max_blues:
        game_id_sum += game['id']

print(game_id_sum)

# part 2
power = 0

for game in games:
    reds = count_color(game, 'red')
    blues = count_color(game, 'blue')
    greens = count_color(game, 'green')
    power += reds * blues * greens

print(power)