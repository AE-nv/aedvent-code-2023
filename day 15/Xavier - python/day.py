import sys

def hash(sequence):
    hash = 0
    for char in sequence:
        hash += ord(char)
        hash *= 17
        hash %= 256
    return hash

def remove_lens(hashmap, label):
    box = hashmap[hash(label)]
    for i, lens in enumerate(box):
        if lens[0] == label:
            box.pop(i)
            break

def insert_lens(hashmap, label, focal_length):
    record = (label, focal_length)
    box = hashmap[hash(label)]
    for i, lens in enumerate(box):
        if lens[0] == label:
            box[i] = record
            break
    else:
        box.append(record)

def focusing_power(hashmap):
    focusing_power = 0
    for box_nbr, box in enumerate(hashmap):
        for slot, lens in enumerate(box):
            focusing_power += (box_nbr+1) * (slot+1) * lens[1]
    return focusing_power

with open(sys.argv[1]) as f:
    init_sequence = f.read().strip()
    steps = init_sequence.split(',')

    # part 1
    hashed = [hash(step) for step in steps]
    print(sum(hashed))

    # part 2
    boxes = [[] for _ in range(256)]
    for step in steps:
        if '-' in step:
            label = step.split('-')[0]
            remove_lens(boxes, label)         
        else:
            label, focal_length = step.split('=')
            focal_length = int(focal_length)
            insert_lens(boxes, label, focal_length)

    print(focusing_power(boxes))
