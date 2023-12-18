import sys
import math
 
# node = (position, direction, steps in direction)
# neighbours are determined based on all 3
# calculate scores for all neighbours and store in priority q: node -> heat_loss

def get_neighbours(map, node, part):
    position, direction, steps = node
    for dy in range(-1,2):
        for dx in range(-1,2):
            neighbour = (position[0]+dy, position[1]+dx)
            # check out of bounds
            if neighbour[0] < 0 or len(map) <= neighbour[0]:
                continue
            if neighbour[1] < 0 or len(map[0]) <= neighbour[1]:
                continue
            step = (dy,dx)
            # only check allowed directions
            if step == (0,0) or (dy != 0 and dx != 0):
                continue
            # not allowed to backtrace
            if step == (-direction[0],-direction[1]):
                continue  
            # PART 1 NEIGHBOURS
            if part == 1:
                # if in same direction only allow if no more then 3 steps in direction
                if step == direction:
                    if steps < 3:
                        yield (neighbour, step, steps+1)
                    continue         
                # corner
                yield (neighbour, step, 1)
            # PART 2 NEIGHBOURS
            if part == 2:
                # allow no more than 10 steps in same direction
                if step == direction:
                    if steps < 10:
                        yield (neighbour, step, steps+1)
                    continue
                # only allow corners after 4 steps
                if steps >= 4 or direction == (0,0): # corner or start
                    yield (neighbour, step, 1)
                

def dijkstra(map, start, goal, part):
    priority_q = {(start, (0,0), 0): 0}
    heat_loss_map = {}
    while len(priority_q) > 0:
        loss = min(priority_q.values())
        node = [n for n in priority_q.keys() if priority_q[n] == loss][0]
        if node[0][0] % 100 == 0:
            print(node, loss)
        if node[0] == goal:
            # needs to move at least 4 steps in part 2 to be able to stop at ending
            if part == 1 or node[-1] >= 4:
                return loss
        heat_loss_map[node] = loss
        priority_q.pop(node)
        for neighbour in get_neighbours(map, node, part):
            # do not check same spot again
            if neighbour in heat_loss_map.keys():
                continue
            priority_q[neighbour] = min(priority_q.get(neighbour, math.inf), loss + map[neighbour[0][0]][neighbour[0][1]])

with open(sys.argv[1]) as f:
    blocks = [[int(x) for x in line] for line in f.read().splitlines()]

    gr, gc = len(blocks)-1, len(blocks[-1])-1
    # heat_loss = dijkstra(blocks, (0,0), (gr,gc), 1)
    # print(heat_loss)
    heat_loss = dijkstra(blocks, (0,0), (gr,gc), 2)
    print(heat_loss)
    