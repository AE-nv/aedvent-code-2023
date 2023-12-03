import re


def has_symbolic_neighbours(start, end, y_data, data):
    locations = []
    for x in range(start-1, end+1):
        for y in range(y_data-1, y_data+2):
            if y >=0 and y < len(data) and x>=0 and x<len(data[0].rstrip()):
                locations.append((x,y))
                if not data[y][x].isnumeric() and not data[y][x]=='.':
                    return True
    return False

if __name__ == '__main__':
    with open("day03.txt", 'r') as f:
        data = f.readlines()

    part_numbers = []
    all_numbers = {}
    for y, line in enumerate(data):
        all_numbers[y]=[]
        numbers = re.finditer("\d+", line.rstrip())
        for number in numbers:
            start, end, value = number.start(), number.end(), number.group()
            all_numbers[y].append((start, end, int(value)))
            if has_symbolic_neighbours(start, end, y, data):
                part_numbers.append(int(value))

    print("PART 1: "+str(sum(part_numbers)))

    gear_ratios = []
    for y, line in enumerate(data):
        asters = re.finditer("\\*", line.rstrip())
        for aster in asters:
            x = aster.start()
            numbers_for_aster = []
            for y_rel in range(y-1, y+2):
                if y_rel in all_numbers.keys():
                    for (start_number, end_number, number_value) in all_numbers[y_rel]:
                        if (start_number>=x-1 and start_number<=x+1) \
                                or (end_number-1>=x-1 and end_number-1<=x+1):
                            numbers_for_aster.append(number_value)
            if len(numbers_for_aster)==2:
                gear_ratios.append(numbers_for_aster[0]*numbers_for_aster[1])

    print("PART 2: "+str(sum(gear_ratios)))