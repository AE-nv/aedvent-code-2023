import sys
from functools import reduce

workflows = open(sys.argv[1]).read().split('\n\n')[0].splitlines()
workflows = {
    f.split('{')[0]: f.split('{')[-1][:-1] for f in workflows
}

# keep 4 ranges => split as necessary while going through
rating_ranges = [[range(1,4001),range(1,4001),range(1,4001),range(1,4001), 'in']]

accepted_rating_ranges = []

while len(rating_ranges) > 0:
    rating_range = rating_ranges.pop(0)
    state = rating_range[-1]
    if state == 'A':
        accepted_rating_ranges.append(rating_range)
        continue
    if state == 'R':
        continue
    # if not accepted
    workflow = workflows[state]
    splits = workflow.split(',')
    for split in splits:
        if '<' in split or '>' in split:
            # perform split and append splitted, keep rest as rating_range
            split_index = 'xmas'.index(split[0])
            smaller_than = True if split[1] == '<' else False
            number = int(split[2:split.index(':')])
            next_state = split[split.index(':')+1:]

            range_to_split = rating_range[split_index]

            if smaller_than:
                remainder = range(number, max(range_to_split)+1)
                after_split = range(min(range_to_split), number)
            else:
                remainder = range(min(range_to_split), number+1)
                after_split = range(number+1, max(range_to_split)+1)
            
            if len(after_split) > 0:
                new = [r for r in rating_range]
                new[split_index] = after_split
                new[-1] = next_state
                rating_ranges.append(new)
            rating_range[split_index] = remainder
        else:
            # just go to next flow
            rating_range[-1] = split
            rating_ranges.append(rating_range)


good_states = sum([reduce(lambda x,y: x*y, [len(r) for r in range]) for range in accepted_rating_ranges])
print(good_states)