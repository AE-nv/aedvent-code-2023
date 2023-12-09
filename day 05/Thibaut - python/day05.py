import pprint
import copy


def find_locations(seeds, mappings, initial_start, end):
    locations = []
    # print("total seed count: "+str(len(seeds)))
    for i, seed in enumerate(seeds):
        start = copy.deepcopy(initial_start)
        start_value = seed
        while start != end:
            for key in mappings.keys():
                if key.startswith(start):
                    for target_start, source_start, value_range in mappings[key]:
                        if start_value >= source_start and source_start + value_range > start_value:
                            start_value = target_start + start_value - source_start
                            break
                    start = key.split("-")[-1]
                    break
            # print(start + ": " + str(start_value))
        # print("finished "+str(i))
        locations.append(start_value)
    return locations


if __name__ == '__main__':
    with open("day05.txt", 'r') as f:
        data = f.readlines()

    seeds = [int(n) for n in data[0].rstrip().split(":")[1].split()]

    mappings = {}
    for l in data[1:]:
        if l.rstrip().endswith(":"):
            mapping_key = l.rstrip().split()[0]
            mappings[mapping_key] = []
        elif l.rstrip()=="":
            continue
        else:
            mappings[mapping_key].append([int(n) for n in l .rstrip().split()])

    pprint.pprint(mappings)

    locations = find_locations(seeds, mappings, "seed", "location")
    print(min(locations))

    reversed_mapping={}
    for key, range_descriptions in mappings.items():
        reversed_key = "-".join(key.split('-')[::-1])
        reversed_mapping[reversed_key] = []
        for range_description in range_descriptions:
            destination, source, range_size = range_description
            reversed_mapping[reversed_key].append([source, destination, range_size])

    # pprint.pprint(reversed_mapping)

    print(find_locations([46], reversed_mapping, "location", "seed"))

    actual_seeds = [(seeds[i], seeds[i+1]) for i in range(0, len(seeds), 2)]

    found=False
    target = -1
    while not found:
        target+=1
        seed = find_locations([target], reversed_mapping, "location", "seed")[0]
        for seed_start, seed_range in actual_seeds:
            if seed_start <= seed < seed_start+seed_range:
                found=True
        print(target)
    print(target)
    print(seed)


