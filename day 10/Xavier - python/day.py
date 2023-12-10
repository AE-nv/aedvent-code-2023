import sys

maze = [[char for char in line] for line in open(sys.argv[1]).read().splitlines()]

start = (0,0)
for y in range(len(maze)):
    for x in range(len(maze[0])):
        if maze[y][x] == "S":
            start = (y,x)

N = (-1,0)
S = (1,0)
W = (0,-1)
E = (0,1)
neighbour_coords = {
    '|': [N,S],
    '-': [E,W],
    'J': [N,W],
    'L': [N,E],
    '7': [S,W],
    'F': [S,E],
    '.': []
}

# find one neighbour of start
loop = [start]
if maze[start[0]+1][start[1]] in ['|','J','L']:
    loop.append((start[0]+1,start[1]))
elif maze[start[0]-1][start[1]] in ['|','7','F']:
    loop.append((start[0]-1,start[1]))
elif maze[start[0]][start[1]-1] in ['-','F','L']:
    loop.append((start[0],start[1]-1))
else:
    loop.append((start[0],start[1]+1))

# go through loop until back at S
def get_next_loop_node(node, loop, maze):
    y,x = node
    pipe = maze[y][x]
    neighbours = [(y+dy,x+dx) for dy,dx in neighbour_coords[pipe]]
    next = [n for n in neighbours if n not in loop]
    if len(next) == 0:
        return None
    return next[0]

cur = loop[-1]
while True:
    cur = get_next_loop_node(cur, loop, maze)
    if cur is None:
        break
    loop.append(cur)

print(len(loop)//2)

# part 2

# add a line in between existing lines (true means pipe, false is something else)
def expand_maze(maze, loop):
    expanded_maze = []
    expanded_loop = []
    for _ in range(len(maze)*2):
        row = [False]*len(maze[0])*2
        expanded_maze.append(row)
    for i in range(len(loop)):
        y,x = loop[i]
        new_y,new_x = 2*y,2*x
        expanded_loop.append((new_y,new_x))
        expanded_maze[new_y][new_x] = True
        y_n,x_n = loop[(i+1)%len(loop)]
        y_c,x_c = y_n-y,x_n-x
        if x_c == 0:
            expanded_maze[new_y+y_c][new_x] = True
            expanded_loop.append((new_y+y_c,new_x))
        else:
            expanded_maze[new_y][new_x+x_c] = True
            expanded_loop.append((new_y,new_x+x_c))
    return expanded_maze, expanded_loop

# floodfill
def floodfill(start,loop,maze):
    q= [start]
    visited = []
    while len(q) > 0:
        y,x = q.pop(0)
        visited.append((y,x))
        neighbours = [(y+dy,x+dx) for dy,dx in [N,S,E,W]]
        for n in neighbours:
            if n in q or n in visited:
                continue
            if n in loop:
                continue
            if 0<=n[0]<len(maze) and 0<=n[1]<len(maze[0]):
                q.append(n)
    return visited


# only enclosed tiles are not connected to outside
def get_inside_coords(maze, loop):
    new_maze, new_loop = expand_maze(maze, loop)

    groups = []
    for y in range(len(new_maze)):
        for x in range(len(new_maze[0])):
            if all([(y,x) not in gr for gr in groups]) and (y,x) not in new_loop:
                gr = floodfill((y,x),new_loop,new_maze)
                groups.append(gr)
    
    inside = []
    for group in groups:
        if len([n for n in group if n[0]==0 or n[1]==0]) == 0:
            inside.extend(group)

    original_inside = [(y//2,x//2) for y,x in inside if y%2==0 and x%2==0]
    return original_inside

print(len(get_inside_coords(maze, loop)))
