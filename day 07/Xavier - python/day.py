import sys
from collections import Counter
from functools import cmp_to_key

# parsing
lines = open(sys.argv[1]).read().splitlines()
hand_bid_mapping = {l.split()[0]: int(l.split()[1]) for l in lines}

# part 1
def compare(h1, h2):
    c1 = sorted(Counter(h1).values(), reverse=True)
    c2 = sorted(Counter(h2).values(), reverse=True)
    # compare hand type
    for i in range(len(c1)):
        if c1[i] > c2[i]:
            return 1
        if c1[i] < c2[i]:
            return -1
    # compare cards in hands
    cards = ['A','K','Q','J','T','9','8','7','6','5','4','3','2','1']
    for i in range(5):
        c1 = cards.index(h1[i])
        c2 = cards.index(h2[i])
        if c1 < c2:
            return 1
        if c1 > c2:
            return -1
    return 0

sorted_hands = sorted(hand_bid_mapping.keys(), key=cmp_to_key(compare))
winnings = [(i+1)*hand_bid_mapping[sorted_hands[i]] for i in range(len(sorted_hands))]
print(sum(winnings))

def compare2(h1, h2):
    # compare hand type
    c1 = sorted(Counter(h1.replace('J','')).values(), reverse=True)
    c2 = sorted(Counter(h2.replace('J', '')).values(), reverse=True)
    j1 = Counter(h1)['J']
    j2 = Counter(h2)['J']
    if len(c1) > 0:
        c1[0] += j1
    else:
        c1 = [j1]
    if len(c2) > 0:
        c2[0] += j2
    else:
        c2 = [j2]
    for i in range(len(c1)):
        if c1[i] > c2[i]:
            return 1
        if c1[i] < c2[i]:
            return -1
    # compare cards in hands
    cards = ['A','K','Q','T','9','8','7','6','5','4','3','2','1','J']
    for i in range(5):
        c1 = cards.index(h1[i])
        c2 = cards.index(h2[i])
        if c1 < c2:
            return 1
        if c1 > c2:
            return -1
    return 0

sorted_hands = sorted(hand_bid_mapping.keys(), key=cmp_to_key(compare2))
winnings = [(i+1)*hand_bid_mapping[sorted_hands[i]] for i in range(len(sorted_hands))]
print(sum(winnings))