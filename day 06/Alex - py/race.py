races1 = [(41, 214), (96, 1789), (88, 1127), (94, 1055)]
races2 = [(41968894, 214178911271055)]


def solve_race(time, dist):
    result = []
    for i in range(1, time):
        if (time - i) * i > dist:
            result.append(i)
    return len(result)


print("Part 1", solve_race(*races1[0]) * solve_race(*races1[1]) * solve_race(*races1[2]))
print("Part 2", solve_race(*races2[0]))
