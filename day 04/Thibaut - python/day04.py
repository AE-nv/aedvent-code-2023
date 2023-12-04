import pprint

if __name__ == '__main__':
    with open("day04.txt", 'r') as f:
        data = f.readlines()

    cards = {}
    i = 1
    for line in data:
        cards[i] = 1
        i += 1

    scores = []
    for line in data:
        card, numbers = line.rstrip().split(':')
        card_index = int(card.split()[1])
        winning_numbers, actual_numbers = numbers.split('|')
        winning_numbers = winning_numbers.split()
        actual_numbers = actual_numbers.split()

        overlap = list(set(actual_numbers) & set(winning_numbers))

        if len(overlap) > 0:
            scores.append(1 * (2 ** (len(overlap) - 1)))
            for i in range(1, len(overlap)+1):
                cards[card_index+i] += cards[card_index]

    pprint.pprint(cards)
    print(sum(scores))
    print(sum(cards.values()))

