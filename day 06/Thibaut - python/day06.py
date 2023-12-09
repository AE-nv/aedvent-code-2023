from sympy import symbols, Eq, solve
import math

if __name__ == '__main__':
    with open("day06.txt", 'r') as f:
        data = f.readlines()

    times = [int(n) for n in data[0].rstrip().split(":")[1].split()]
    distances = [int(n) for n in data[1].rstrip().split(":")[1].split()]

    times.append(int("".join([str(t) for t in times])))
    distances.append(int("".join([str(d) for d in distances])))

    margins = []
    for i, time in enumerate(times):
        distance = distances[i]
        t_pressed = symbols('x')
        eq = Eq(-t_pressed**2+time*t_pressed-distance, 0)

        sol = solve(eq)
        sol = [float(v) for v in sol]
        margin = math.floor(sol[1]-0.0000001)-math.ceil(sol[0]+0.0000001)+1
        margins.append(margin)
    print(math.prod(margins[:-1]))
    print(margins[-1])


