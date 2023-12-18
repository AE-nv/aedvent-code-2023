import numpy


def all_zero(list):
    return all(x == 0 for x in list)


def guess_next(list):
    diff = numpy.diff(list)
    return list[len(list) - 1] + (0 if all_zero(diff) else guess_next(diff))


def guess_prev(list):
    diff = numpy.diff(list)
    return list[0] - (0 if all_zero(diff) else guess_prev(diff))


with open("input.txt", "r") as file:
    lines = file.readlines()
    part1, part2 = 0, 0
    for line in lines:
        part1 += guess_next([int(x) for x in line.split(" ")])
        part2 += guess_prev([int(x) for x in line.split(" ")])
    print(part1, part2)
