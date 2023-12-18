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


def find_projected_ranges(value_range, mapping):
    start = value_range[0]
    target = value_range[1]
    projected_ranges = []
    while start != target:
        something_was_mapped = False
        for destination, source, mapped_range in mapping:
            if source <= start < source + mapped_range:
                offset = start - source
                if source + mapped_range > target:
                    projected_ranges.append([destination + offset, destination + offset + target - start])
                    start = target
                else:
                    projected_ranges.append([destination + offset, destination + mapped_range])
                    start = source + mapped_range
                something_was_mapped = True
                break
        if not something_was_mapped:
            for _, source, _ in mapping:
                if source > start:
                    projected_ranges.append([start, source])
                    start = source
                    something_was_mapped = True
                    break
        if not something_was_mapped:
            projected_ranges.append([start, target])
            start = target
    # print(projected_ranges)
    return projected_ranges


def find_projected_ranges_source_to_target(values, mappings, source, target):
    value_ranges = []
    for i in range(0, len(values), 2):
        value_ranges.append([values[i], values[i] + values[i + 1]])

    while source != target:
        projections = []
        for key in mappings.keys():
            if key.startswith(source):
                source = key.split("-")[-1]
                break
        for value_range in value_ranges:
            projected_ranges = find_projected_ranges(value_range, mappings[key])
            projections.extend(projected_ranges)
        value_ranges = copy.deepcopy(projections)
    return value_ranges


if __name__ == '__main__':
    with open("day05.txt", 'r') as f:
        data = f.readlines()

    seeds = [int(n) for n in data[0].rstrip().split(":")[1].split()]

    mappings = {}
    for l in data[1:]:
        if l.rstrip().endswith(":"):
            mapping_key = l.rstrip().split()[0]
            mappings[mapping_key] = []
        elif l.rstrip() == "":
            continue
        else:
            mappings[mapping_key].append([int(n) for n in l.rstrip().split()])

    for _, mapping_ranges in mappings.items():
        mapping_ranges.sort(key=lambda mapping_range: mapping_range[1])

    singular_seeds = [int(s) for s in (' 1 '.join([str(i) for i in seeds])+' 1 ').split()]
    part1 = min([projection_start for projection_start, _ in
                     find_projected_ranges_source_to_target(singular_seeds, mappings, "seed", "location")])
    print("PART 1: " + str(part1))

    part2 = min([projection_start for projection_start, _ in
                 find_projected_ranges_source_to_target(seeds, mappings, "seed", "location")])
    print("PART 2: " + str(part2))
