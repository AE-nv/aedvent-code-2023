import sys

# (y,x), U is negative, L is negative

def next_location(location, direction, steps):
    match direction:
        case 'U':
            location = (location[0]-steps,location[1])
        case 'D':
            location = (location[0]+steps,location[1])
        case 'L':
            location = (location[0],location[1]-steps)
        case 'R':
            location = (location[0],location[1]+steps)
    
    return location

def calculate_area(steps):
    circumference = 0
    corners = [(0,0)]

    for direction, distance in steps:
        corner = next_location(corners[-1], direction, distance)
        corners.append(corner)
        circumference += distance
    
    area = 0
    for i in range(len(corners)-1):
        c1 = corners[i]
        c2 = corners[i+1]
        area += c1[1]*c2[0] - c1[0]*c2[1]
    area //= 2

    return area + circumference//2 + 1

with open(sys.argv[1]) as f:
    steps_part_1 = []
    steps_part_2 = []

    for command in f.read().splitlines():
        direction, steps, color = command.split()
        steps = int(steps)

        steps_part_1.append((direction, steps))

        steps = int(color[2:-2], base=16)
        direction = 'RDLU'[int(color[-2:-1])]

        steps_part_2.append((direction, steps))

    print(calculate_area(steps_part_1))
    print(calculate_area(steps_part_2))

