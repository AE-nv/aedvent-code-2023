import re
from collections import defaultdict


def points_p1(winning, game):
    points = 0
    for num in game:
        if num in winning:
            points = max(1, points * 2)
    return points


def matches_p2(winning, game):
    matches = 0
    for num in game:
        if num in winning:
            matches += 1
    return matches


with open("input.txt", "r") as file:
    lines = file.readlines()
    part1 = []
    part2 = 0
    cards = defaultdict(int)
    for line in lines:
        prefix_part, game_part = line.split(":")
        winning_numbers, scratched_numbers = game_part.split("|")
        card = int(re.findall(r'(\d+)', prefix_part)[0])
        winning = [int(c) for c in re.findall(r'(\d+)', winning_numbers)]
        playing = [int(c) for c in re.findall(r'(\d+)', scratched_numbers)]
        part1.append(points_p1(winning, playing))
        cards[card] += 1
        matches = matches_p2(winning, playing)
        for i in range(card + 1, card + matches + 1):
            cards[i] += cards[card]
        part2 += cards[card]
    print(sum(part1))
    print(part2)
