import sys
from functools import cache

@cache
def count_possible_arrangements(springs,groups):
    if len(springs) == 0:
        return 1 if len(groups) == 0 else 0
    if len(groups) == 0:
        return 1 if springs.count('#') == 0 else 0
    if sum(groups) > len(springs):
        return 0
    
    if springs[0] == '.':
        return count_possible_arrangements(springs[1:],groups)
    if springs[0] == '#':
        # check if possible
        group_length = groups[0]
        if springs[:group_length].count('.') == 0 and (len(springs) == group_length or springs[group_length] in ['?','.']):
            return count_possible_arrangements(springs[group_length+1:],groups[1:])
        else:
            return 0

    # question mark => check both options
    return count_possible_arrangements('.'+springs[1:],groups) + count_possible_arrangements('#'+springs[1:],groups)

input = open(sys.argv[1]).read().splitlines()

part_1 = 0
part_2 = 0

for row in input:
    springs, groups = row.split()
    groups = [int(size) for size in groups.split(',')]

    part_1 += count_possible_arrangements(springs, tuple(groups))

    unfolded_springs = springs + ('?'+springs)*4
    unfolded_groups = tuple(groups*5)
    part_2 += count_possible_arrangements(unfolded_springs,unfolded_groups)

print(part_1)
print(part_2)
