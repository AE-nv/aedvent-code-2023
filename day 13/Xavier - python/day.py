import sys

patterns = [
    pattern.splitlines() for pattern in open(sys.argv[1]).read().split('\n\n')
]

def transpose(pattern):
    transposed = []
    for col in range(len(pattern[0])):
        row = [pattern[i][col] for i in range(len(pattern))]
        transposed.append(row)
    return transposed

def check_reflection(pattern, column, required_smidges):
    if column < 1 or column > len(pattern[0])-1:
        return False
    smidges = 0
    for row in pattern:
        i = 0
        while 0 <= column-(i+1) and column + i < len(row):
            if row[column-(i+1)] != row[column+i]:
                smidges += 1
                if smidges > required_smidges:
                    return False
            i += 1
    return True if smidges == required_smidges else False


def find_reflection(pattern, required_smidges):
    for reflection_line in range(1,len(pattern[0])):
        if check_reflection(pattern, reflection_line, required_smidges):
            return reflection_line
    return None
    
part_1 = 0
part_2 = 0

for pattern in patterns:
    reflection = find_reflection(pattern, 0) or 100*find_reflection(transpose(pattern), 0)
    part_1 += reflection
    
    smidged_reflection = find_reflection(pattern, 1) or 100*find_reflection(transpose(pattern), 1)
    part_2 += smidged_reflection

print(part_1, part_2)