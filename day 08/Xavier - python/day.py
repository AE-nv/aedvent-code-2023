import sys
from math import lcm

input = open(sys.argv[1]).read().splitlines()
path = input[0]
network = {
    line[0:3]: (line[7:10], line[12:15]) for line in input[2:]
}

def calculate_steps(cur, network, path, end_condition):
    steps = 0
    while not end_condition(cur):
        if path[steps%len(path)] == 'L':
            cur = network[cur][0]
        else:
            cur = network[cur][1]
        steps += 1
    return steps

print(calculate_steps('AAA',network,path,lambda x: x == 'ZZZ'))

# part 2
steps = [
    calculate_steps(cur, network, path, lambda x: x.endswith('Z')) for cur in network.keys() if cur.endswith('A')
]

print(lcm(*steps))