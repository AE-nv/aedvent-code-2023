import sys


input = open(sys.argv[1]).read().splitlines()

def expanding_rows_and_columns(universe):
    rows = [i for i in range(len(universe)) if universe[i].count('.') == len(universe[i])]
    cols = [i for i in range(len(universe[0])) if [line[i] for line in universe].count('.') == len(universe[0])]
    return rows, cols

def find_galaxies(universe):
    galaxies = []
    for y in range(len(universe)):
        for x in range(len(universe[0])):
            if universe[y][x] == '#':
                galaxies.append((y,x))
    return galaxies

def get_path_length(g1,g2,rows,cols):
    length = abs(g1[0]-g2[0]) + abs(g1[1]-g2[1])
    length += (EXPANSION_FACTOR-1) * len([row for row in rows if row in range(min(g1[0],g2[0]),max(g1[0],g2[0]))])
    length += (EXPANSION_FACTOR-1) * len([col for col in cols if col in range(min(g1[1],g2[1]),max(g1[1],g2[1]))])
    return length

rows, cols = expanding_rows_and_columns(input)

galaxies = find_galaxies(input)

EXPANSION_FACTOR = 2
shortest_paths = [get_path_length(galaxies[i],galaxies[j],rows,cols) for i in range(len(galaxies)) for j in range(i+1, len(galaxies))]
print(sum(shortest_paths))

EXPANSION_FACTOR = 1_000_000
shortest_paths = [get_path_length(galaxies[i],galaxies[j],rows,cols) for i in range(len(galaxies)) for j in range(i+1, len(galaxies))]
print(sum(shortest_paths))
