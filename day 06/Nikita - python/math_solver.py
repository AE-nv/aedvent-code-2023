import math


def solve(a, b, c):
    d = b**2 - 4*a*c
    if d < 0:
        return None, None
    elif d == 0:
        return -b/(2*a), -b/(2*a)
    else:
        return ((-b + math.sqrt(d))/(2*a)), ((-b - math.sqrt(d))/(2*a))


def read_in_cases(path: str):
    time_cases = []
    distance_cases = []
    with open(path) as f:
        for line in f.read().split("\n"):
            if line == "":
                continue
            objectif, line = line.split(":")
            if objectif == "Time":
                time_cases = [int(i) for i in line.split(" ") if i != ""]
            else:
                distance_cases = [int(i) for i in line.split(" ") if i != ""]
    return list(zip(time_cases, distance_cases))

def read_in_large_case(path: str):
    time_case = "0"
    distance_case = "0"
    with open(path) as f:
        for line in f.read().split("\n"):
            if line == "":
                continue
            objectif, line = line.split(":")
            if objectif == "Time":
                time_case = int(line.replace(" ", ""))
            else:
                distance_case = int(line.replace(" ", ""))
    return list((int(time_case), int(distance_case)))


if __name__ == '__main__':
    time_distance_cases = read_in_cases("input.txt")
    amount_of_winning_cases = []
    for time, distance in time_distance_cases:
        out1, out2 = solve(-1, time, -distance)
        print(out1, out2)
        if out1%1 == 0:
            out1_rounded_up = int(out1) + 1
        else:
            out1_rounded_up = math.ceil(out1)
        if out2%1 == 0:
            out2_rounded_down = int(out2) - 1
        else:
            out2_rounded_down = int(out2)
        print("between {} and {}".format(out1_rounded_up, out2_rounded_down))
        amount_of_winning_cases.append((out2_rounded_down - out1_rounded_up) + 1)
    total_multiplication = 1
    for i in amount_of_winning_cases:
        total_multiplication *= i
    print(total_multiplication)
    print("PART 2:")
    time_distance_cases = read_in_large_case("input.txt")
    out1, out2 = solve(-1, time_distance_cases[0], -time_distance_cases[1])
    print(out1, out2)
    if out1 % 1 == 0:
        out1_rounded_up = int(out1) + 1
    else:
        out1_rounded_up = math.ceil(out1)
    if out2 % 1 == 0:
        out2_rounded_down = int(out2) - 1
    else:
        out2_rounded_down = int(out2)
    print("between {} and {}".format(out1_rounded_up, out2_rounded_down))
    print("amount of winning cases: {}".format((out2_rounded_down - out1_rounded_up) + 1))


