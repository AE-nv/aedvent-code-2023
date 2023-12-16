import sys

# follow the beam, if it splits follow the one until it stops or encounters somewhere+direction its been
# then follow the other beam
# count all unique locations its been

def get_next(map, r, c, dr, dc):
    next = [((r+dr,c+dc),(dr,dc))]
    if map[r][c] == '-' and dr != 0:
        next = [((r,c+dc),(0,dc)) for dc in [-1,1]]
    elif map[r][c] == '|' and dc != 0:
        next = [((r+dr,c),(dr,0)) for dr in [-1,1]]
    elif map[r][c] == '/':
        temp = dr
        dr = -dc
        dc = -temp
        next = [((r+dr,c+dc),(dr,dc))]
    elif map[r][c] == '\\':
        temp = dr
        dr = dc
        dc = temp
        next = [((r+dr,c+dc),(dr,dc))] 

    return [x for x in next if 0<=x[0][0]<len(map) and 0<=x[0][1]<len(map[0])]

def energized(contraption, sr, sc, dr, dc):
    seen = set()
    q = [((sr,sc),(dr,dc))]

    while len(q) > 0:
        cur,dir = q.pop(0)
        seen.add((cur,dir))
        next = get_next(contraption, *cur, *dir)
        next = [n for n in next if n not in seen]
        q.extend(next)

    unique = set([x[0] for x in seen])
    return len(unique)

with open(sys.argv[1]) as f:
    contraption = f.read().splitlines()

    print(energized(contraption,0,0,0,1))

    max_energized = 0
    for r in range(len(contraption)):
        left = energized(contraption,r,0,0,1)
        max_energized = max(left, max_energized)
        right = energized(contraption,r,len(contraption[0])-1,0,-1)
        max_energized = max(right, max_energized)
    for c in range(len(contraption[0])):
        top = energized(contraption,0,c,1,0)
        max_energized = max(top, max_energized)
        bottom = energized(contraption,len(contraption)-1,c,-1,0)
        max_energized = max(bottom, max_energized)
    print(max_energized)
