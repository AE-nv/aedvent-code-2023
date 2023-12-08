import sys

# parsing
input = open(sys.argv[1]).read().split('\n\n')
seeds = [int(seed) for seed in input[0].split(': ')[1].split()]
maps = [
    [[int(x) for x in line.split()] for line in m.splitlines()[1:]] for m in input[1:]
]

# part 1
def location_number(seed, maps):
    for m in maps:
        for mapping in m:
            distance = seed - mapping[1]
            if 0 <= distance < mapping[2]:
                seed = mapping[0] + distance
                break
    return seed

location_numbers = [location_number(seed, maps) for seed in seeds]

print(min(location_numbers))

# part 2
seeds = [range(seeds[i*2], seeds[i*2]+seeds[i*2+1]) for i in range(len(seeds)//2)]
maps = [[(range(mapping[1],mapping[1]+mapping[2]), mapping[0]) for mapping in map_group] for map_group in maps]

for map_group in maps:
    new_seeds = []
    
    for map_range, target in map_group:
        next_try = []
        for seed in seeds:
            # map intersection, append other pieces without mapping
            intersection = range(max(seed[0],map_range[0]),min(seed[-1],map_range[-1])+1) or None
            if intersection is None:
                next_try.append(seed)
            else:
                left = range(seed[0],max(seed[0],map_range[0])) or None
                if left:
                    next_try.append(left)
                right = range(min(seed[-1],map_range[-1])+1,seed[-1]+1) or None
                if right:
                    next_try.append(right)
                shift = target - map_range[0]
                mapped = range(intersection[0] + target - map_range[0], intersection[-1] + target - map_range[0]+1)
                new_seeds.append(mapped)
        seeds = next_try
    new_seeds.extend(next_try)
    seeds = new_seeds
            

print(min([min(r) for r in seeds]))