import re


def parse_map(lines):
    map = {}
    for line in lines:
        parts = re.findall(r'[A-Z]{3}', line)
        map[parts[0]] = (parts[1],parts[2])
    return map

def find_path(map, dirs):
    dist = 0
    pos = 'AAA'
    while True:
        nextDir = 0 if dirs[dist % len(dirs)] == "L" else 1
        pos = map[pos][nextDir]
        dist+= 1
        if pos == "ZZZ":
            return dist

with open("input.txt", "r") as file:
    lines = file.readlines()
    dirs = lines[0].strip()
    map = parse_map(lines[2:])

    print("Part 1", find_path(map, dirs))
