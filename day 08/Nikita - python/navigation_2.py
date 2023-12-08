import math


def read_in_file(path: str):
    instructions = ""
    all_nodes = {}
    with open(path) as f:
        for line in f.read().split("\n"):
            if line == "":
                continue
            if "=" not in line:
                instructions = line
            else:
                node_name, node_value = line.split("=")
                node_value_left, node_value_right = node_value.split(",")
                all_nodes[node_name.strip()] = (node_value_left.strip().replace("(", ""), node_value_right.strip().replace(")", ""))
    return instructions, all_nodes


if __name__ == '__main__':
    instructions, all_nodes = read_in_file("input.txt")
    print(instructions)
    total_steps_per_node = []
    print(all_nodes.get("AAA"))
    starting_nodes = list(filter(lambda x: x[2] == "A", all_nodes.keys()))
    for starting_node in starting_nodes:
        current_instruction_index = 0
        total_steps = 0
        current_name = starting_node
        while current_name[2] != "Z":
            total_steps += 1
            current_instruction = instructions[current_instruction_index]
            if current_instruction == "L":
                current_name = all_nodes.get(current_name)[0]
            else:
                current_name = all_nodes.get(current_name)[1]
            current_instruction_index += 1
            current_instruction_index %= len(instructions)
        total_steps_per_node.append(total_steps)
    print(total_steps_per_node)
    print(math.lcm(*total_steps_per_node))