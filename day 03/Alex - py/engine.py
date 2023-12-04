import re


def has_any_symbol(line, start_pos, end_pos):
    for i in range(start_pos, end_pos):
        if 0 <= i < len(line) and not (line[i] == '.' or line[i].isdigit() or line[i].isspace()):
            return True
    return False


def add_part_to_gears(gears, part, line_index, line, start_pos, end_pos):
    for i in range(start_pos, end_pos):
        if 0 <= i < len(line) and line[i] == '*':
            key = str(line_index) + '_' + str(i)
            if key not in gears:
                gears[key] = []
            gears[key].append(part)


def part2(gears):
    result = []
    for key in gears.keys():
        if len(gears[key]) == 2:
            result.append(gears[key][0] * gears[key][1])
    print('part2: ', sum(result))


with open("input.txt", "r") as file:
    lines = file.readlines()
    part1 = []
    gears = {}
    for index, line in enumerate(lines):
        pattern = r'\d+'
        matches = re.finditer(r'\d+', line)

        for match in matches:
            part = int(match.group(0))
            start, end = match.start() - 1, match.end() + 1
            if has_any_symbol(line, start, end) or (index > 0 and has_any_symbol(lines[index - 1], start, end)) or (index + 1 < len(
                    lines) and has_any_symbol(lines[index + 1], start, end)):
                part1.append(part)
                add_part_to_gears(gears, part, index, line, start, end)
                if index > 0:
                    add_part_to_gears(gears, part, index - 1, lines[index - 1], start, end)
                if index + 1 < len(lines):
                    add_part_to_gears(gears, part, index + 1, lines[index + 1], start, end)
    print('part1: ', sum(part1))
    part2(gears)
