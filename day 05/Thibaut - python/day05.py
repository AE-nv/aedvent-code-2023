import pprint


def find_locations(seeds, mappings):
    locations = []
    print("total seed count: "+str(len(seeds)))
    for i, seed in enumerate(seeds):
        start = "seed"
        start_value = seed
        end = "location"
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
        print("finished "+str(i))
        locations.append(start_value)
    return locations


if __name__ == '__main__':
    with open("day05_example.txt", 'r') as f:
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

    locations = find_locations(seeds, mappings)
    print(min(locations))

    actual_seeds=[]
    for i in range(0, len(seeds), 2):
        for v in range(seeds[i], seeds[i]+seeds[i+1]):
            actual_seeds.append(v)
    print(actual_seeds)
    print(len(actual_seeds))

    locations = find_locations(actual_seeds, mappings)
    print(min(locations))


