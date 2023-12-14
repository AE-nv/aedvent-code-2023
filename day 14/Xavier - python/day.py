import sys

def north_beam_load(rock_field):
    load = 0
    for i, row in enumerate(rock_field):
        for char in row:
            if char == 'O':
                load += len(rock_field)-i
    return load

def tilt_north(rock_field):
    tilted = [row for row in rock_field]
    for i in range(len(rock_field)):
        for j in range(len(rock_field[0])):
            if tilted[i][j] == '.':
                # check if can be replaced by O
                for k in range(i+1, len(rock_field)):
                    if tilted[k][j] == '#':
                        break
                    elif tilted[k][j] == 'O':
                        # switch
                        tilted[i] = tilted[i][:j] + 'O' + tilted[i][j+1:]
                        tilted[k] = tilted[k][:j] + '.' + tilted[k][j+1:]
                        break
    return tilted

def tilt_south(rock_field):
    south_up = list(reversed(rock_field))
    tilted = tilt_north(south_up)
    return list(reversed(tilted))

def tilt_west(rock_field):
    tilted = [row for row in rock_field]
    for i in range(len(rock_field)):
        for j in range(len(rock_field[0])):
            if tilted[i][j] == '.':
                # check if can be replaced by O
                for k in range(j+1, len(rock_field[0])):
                    if tilted[i][k] == '#':
                        break
                    elif tilted[i][k] == 'O':
                        # switch
                        tilted[i] = tilted[i][:j] + 'O' + tilted[i][j+1:k] + '.' + tilted[i][k+1:]
                        break
    return tilted

def tilt_east(rock_field):
    reverse = [row[::-1] for row in rock_field]
    tilted = tilt_west(reverse)
    return [row[::-1] for row in tilted]

def cycle(rock_field):
    N = tilt_north(rock_field)
    W = tilt_west(N)
    S = tilt_south(W)
    return tuple(tilt_east(S))

if __name__ == "__main__":
    rock_field = open(sys.argv[1]).read().splitlines()

    # part 1
    tilted = tilt_north(rock_field)
    load = north_beam_load(tilted)
    print(load)

    # part 2
    states = [rock_field]
    for i in range(1_000_000_000):
        cycled = cycle(states[-1])

        if cycled in states:
            pre_cycle = states.index(cycled)
            cycle_length = len(states)-pre_cycle
            end_state_index = pre_cycle + (1_000_000_000-pre_cycle) % cycle_length
            print(north_beam_load(states[end_state_index]))
            break
        states.append(cycled)