import sys
from math import floor

# parsing
cards = open(sys.argv[1]).read().splitlines()
cards = [card.split(':')[1].strip().split('|') for card in cards]
cards = [{'winning': card[0].strip().split(), 'numbers': card[1].strip().split()} for card in cards]

# part 1
matches = [[1 for number in card['numbers'] if number in card['winning']] for card in cards]
points = [floor(2**(sum(card)-1)) for card in matches]
print(sum(points))

# part 2
card_amounts = [1 for _ in range(len(cards))]
for i in range(len(cards)):
    if len(matches[i]) > 0:
        for j in range(len(matches[i])):
            if i+1+j < len(cards):
                card_amounts[i+1+j] += card_amounts[i]

print(sum(card_amounts))