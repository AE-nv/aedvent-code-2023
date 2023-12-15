def coord(x, y) -> str:
    return f"{x},{y}"

NORTH, EAST, SOUTH, WEST = (0, -1), (1, 0), (0, 1), (-1, 0)
MAX_X, MAX_Y = 0, 0
def move_rock(map, x, y, dx, dy):
    if map.get(coord(x + dx, y + dy), '') == '.':
        map[coord(x + dx, y + dy)] = 'O'
        map[coord(x, y)] = '.'
        return move_rock(map, x + dx, y + dy, dx, dy)
    return

def readMap(lines):
    map = dict()
    for y, line in enumerate(lines):
        for x, c in enumerate(line):
            map[coord(x, y)] = c
    return map


def tilt_north(map):
    for y in range(1, MAX_Y):
        for x in range(0, MAX_X):
            if map[coord(x, y)] == 'O':
                move_rock(map, x, y, *NORTH)
    return map

def tilt_south(map):
    for y in range(MAX_Y - 1, -1, -1):
        for x in range(0, MAX_X):
            if map[coord(x, y)] == 'O':
                move_rock(map, x, y, *SOUTH)
    return map

def tilt_east(map):
    for x in range(MAX_X - 1, -1, -1):
        for y in range(0, MAX_Y):
            if map[coord(x, y)] == 'O':
                move_rock(map, x, y, *EAST)
    return map

def tilt_west(map):
    for x in range(0, MAX_X):
        for y in range(0, MAX_Y):
            if map[coord(x, y)] == 'O':
                move_rock(map, x, y, *WEST)
    return map

def tilt_all_dirs(map):
    map = tilt_north(map)
    map = tilt_west(map)
    map = tilt_south(map)
    map = tilt_east(map)
    return map

with open("input.txt", "r") as file:
    lines = [line.strip() for line in file.readlines()]
    MAX_X, MAX_Y = len(lines[0]), len(lines)
    map = readMap(lines)
    map = tilt_north(map)
    part1, part2 = 0, 0
    for y in range(0, MAX_Y):
        part1 += (MAX_Y - y) * (sum([1 if map.get(coord(x, y), '') == 'O' else 0 for x in range(0, MAX_X)]))
    print("Part 1:", part1)
    map = readMap(lines)
    previous = []
    cycle_start, cycle_size = 0, 0
    for i in range(0, 1000000000):
        tilt_all_dirs(map)
        cycle = ''.join(map.values())
        if cycle in previous:
            cycle_start = previous.index(cycle)
            cycle_size = i - cycle_start
            break
        previous.append(cycle)
    for i in range(0, ((1000000000 - cycle_start) % cycle_size) - 1):
        tilt_all_dirs(map)
    for y in range(0, MAX_Y):
        part2 += (MAX_Y - y) * (sum([1 if map.get(coord(x, y), '') == 'O' else 0 for x in range(0, MAX_Y)]))
    print("Part 2:", part2)
