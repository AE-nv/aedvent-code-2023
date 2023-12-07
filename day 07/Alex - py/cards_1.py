from collections import Counter


def typerank(cards):
    counts = Counter(cards).most_common()
    if counts[0][1] == 5:
        return '9'
    if counts[0][1] == 4:
        return '8'
    if counts[0][1] == 3 and counts[1][1] == 2:
        return '7'
    if counts[0][1] == 3:
        return '6'
    if counts[0][1] == 2 and counts[1][1] == 2:
        return '5'
    if counts[0][1] == 2:
        return '4'
    return '3'


def parse_cards(line):
    parts = line.split(" ")
    rank = typerank(parts[0]) + parts[0].replace("T", "I").replace("K", "X").replace("A", "Z")
    return (parts[0], int(parts[1]), rank)


with open("input.txt", "r") as file:
    lines = file.readlines()
    cards = [parse_cards(line) for line in lines]
    cards.sort(key=lambda x: x[2])
    result = 0
    for i, hand in enumerate(cards):
        result += hand[1] * (i + 1)
    print("Part 1", result)
