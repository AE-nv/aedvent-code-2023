races1 = [(41, 214), (96, 1789), (88, 1127), (94, 1055)]
races2 = [(41968894, 214178911271055)]


def solve_race(time, dist):
    result = []
    for i in range(1, time):
        if (time - i) * i > dist:
            result.append(i)
    return len(result)


part1 = []
for race_params in races1:
    part1.append(solve_race(race_params[0], race_params[1]))
print("Part 1", part1[0] * part1[1] * part1[2])
print("Part 2", solve_race(races2[0][0], races2[0][1]))
