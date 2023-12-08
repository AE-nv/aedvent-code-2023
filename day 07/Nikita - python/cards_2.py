import copy
from dataclasses import dataclass, field
from typing import List
from collections import Counter

ranks = {
    "A": 1,
    "K": 2,
    "Q": 3,
    "J": 14,
    "T": 5,
    "9": 6,
    "8": 7,
    "7": 8,
    "6": 9,
    "5": 10,
    "4": 11,
    "3": 12,
    "2": 13
}


@dataclass(frozen=True)
class Card:
    rank: str

    def __lt__(self, other):
        return ranks[self.rank] < ranks[other.rank]

    def __gt__(self, other):
        return ranks[self.rank] > ranks[other.rank]

    def __eq__(self, other):
        return ranks[self.rank] == ranks[other.rank]


@dataclass
class Hand:
    original_cards: List[Card]
    bid: int
    winning_number: int = field(init=False)

    def __post_init__(self):
        self.winning_number = self.calculate_winning_number()

    def calculate_winning_number(self) -> int:
        sorted_cards = sorted(self.original_cards)
        temp_counter_cards = Counter(sorted_cards)
        counter_cards = self.process_counter(temp_counter_cards)
        if len(counter_cards) == 1:
            return 6
        elif len(counter_cards) == 5:
            return 0
        elif len(counter_cards) == 4:
            return 1
        elif len(counter_cards) == 3:
            if 3 in counter_cards.values():
                return 3
            else:
                return 2
        else:
            if 4 in counter_cards.values():
                return 5
            else:
                return 4

    def process_counter(self, counter_cards):
        counter_cards = copy.deepcopy(counter_cards)
        if counter_cards.get(Card("J")):
            j_card_count = counter_cards.get(Card("J"))
            if j_card_count == 5:
                return counter_cards
            del counter_cards[Card("J")]
            most_common = counter_cards.most_common(1)[0]
            counter_cards[most_common[0]] += j_card_count
            return counter_cards
        else:
            return counter_cards

    def __lt__(self, other):
        if self.winning_number < other.winning_number:
            return True
        elif self.winning_number > other.winning_number:
            return False
        for i in range(5):
            if other.original_cards[i] < self.original_cards[i]:
                return True
            elif other.original_cards[i] > self.original_cards[i]:
                return False
        return False

    def __gt__(self, other):
        if self.winning_number > other.winning_number:
            return True
        elif self.winning_number < other.winning_number:
            return False
        for i in range(5):
            if other.original_cards[i] > self.original_cards[i]:
                return True
            elif other.original_cards[i] < self.original_cards[i]:
                return False
        return False

    def __eq__(self, other):
        if self.winning_number == other.winning_number:
            for i in range(5):
                if other.original_cards[i] != self.original_cards[i]:
                    return False
            return True
        return False


@dataclass
class GameOfCards:
    all_hands: List[Hand]

    def sort_all_winning_hands(self) -> List[Hand]:
        return sorted(self.all_hands, reverse=True)

    def calculate_total_winnings(self) -> int:
        total_winnings = 0
        sorted_hands = self.sort_all_winning_hands()
        for index, hand in enumerate(sorted_hands):
            total_winnings += (hand.bid * (len(sorted_hands)-index))
        return total_winnings


if __name__ == '__main__':
    all_hands = []
    with open("input.txt") as f:
        for line in f.read().split("\n"):
            if line == "":
                continue
            cards, bid = line.split(" ")
            all_hands.append(Hand([Card(card) for card in cards], int(bid)))
    game = GameOfCards(all_hands)
    print(game.calculate_total_winnings())
