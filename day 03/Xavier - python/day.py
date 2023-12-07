import sys
from collections import defaultdict

map = open(sys.argv[1]).read().splitlines()

def is_part_number(map, x_min, x_max, y_num):
    """Returns whether part number and location of the symbol if so"""
    for x in range(x_min-1, x_max+2):
        for y in range(y_num-1, y_num+2):
            if x in range(x_min, x_max+1) and y == y_num:
                continue
            if x < 0 or x >= len(map[0]):
                continue
            if y < 0 or y >= len(map):
                continue
            if map[y][x] != '.' and not map[y][x].isdigit():
                return True, (y,x)
    return False, (-1,-1)

part_numbers = []
potential_gears = defaultdict(list)

x = y = 0
while y < len(map):
    while x < len(map[y]):
        if map[y][x].isdigit():
            x_min = x
            while x+1 < len(map[y]) and map[y][x+1].isdigit():
                x += 1

            part, symbol_loc = is_part_number(map, x_min, x, y)

            if part:
                part_numbers.append(int(map[y][x_min:x+1]))
            if part and map[symbol_loc[0]][symbol_loc[1]] == '*':
                potential_gears[symbol_loc].append(int(map[y][x_min:x+1]))
        x += 1
    x = 0
    y += 1

# part 1
print(sum(part_numbers))

## part 2
print(sum(x[0]*x[1] for x in potential_gears.values() if len(x)==2))