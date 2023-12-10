import sys

sequences = [
    [int(val) for val in line.split()] for line in open(sys.argv[1]).read().splitlines()
]

next_values = []
prev_values = []

for s in sequences:
    diff_sequences = [s]
    while len([val for val in diff_sequences[-1] if val != 0]) > 0:
        last = diff_sequences[-1]
        diffs = [last[i]-last[i-1] for i in range(1,len(last))]
        diff_sequences.append(diffs)

    next_val = sum(diff_sequences[i][-1] for i in range(len(diff_sequences)))
    prev_val = sum((-1)**i*diff_sequences[i][0] for i in range(len(diff_sequences)))
    next_values.append(next_val)
    prev_values.append(prev_val)

print(sum(next_values))
print(sum(prev_values))
