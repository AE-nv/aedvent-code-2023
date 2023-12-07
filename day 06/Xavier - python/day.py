import sys
from functools import reduce

input = open(sys.argv[1]).readlines()

def wins(t, rec):
    ds = [ms*(t-ms) for ms in range(t)]
    return sum(1 for d in ds if d > rec)

# part 1
ts, recs = [[int(x) for x in l.split(':')[1].split()] for l in input]
print(reduce(lambda x,y: x*y, [wins(ts[i], recs[i]) for i in range(len(ts))]))

# part 2
t, rec = [int(line.split(':')[1].replace(' ','')) for line in input]

print(wins(t, rec))