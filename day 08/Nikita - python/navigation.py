from dataclasses import dataclass


@dataclass
class Node:
    name: str
    left_node = None
    right_node = None

    def __left__(self):
        return self.left_node

    def __right__(self):
        return self.right_node

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

def find_node(nodes: dict, name: str):
    ...


if __name__ == '__main__':
    instructions, all_nodes = read_in_file("input.txt")
    print(instructions)
    print(all_nodes.get("AAA"))
    current_name = "AAA"
    total_steps = 0
    current_instruction_index = 0
    while current_name != "ZZZ":
        total_steps += 1
        current_instruction = instructions[current_instruction_index]
        if current_instruction == "L":
            current_name = all_nodes.get(current_name)[0]
        else:
            current_name = all_nodes.get(current_name)[1]
        current_instruction_index += 1
        current_instruction_index %= len(instructions)
    print(total_steps)