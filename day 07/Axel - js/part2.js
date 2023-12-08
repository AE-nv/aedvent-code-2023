export function part2(input) {
  const handsAndBids = parseInput(input);
  handsAndBids.sort(compareHands);

  return handsAndBids
    .map((handAndBid, i) => handAndBid.bid * (i + 1))
    .reduce((acc, curr) => (acc += curr), 0);
}

export function parseLine(line) {
  const [hand, bid] = line.split(/\s+/);
  return {
    hand: parseHand(hand),
    rawHand: hand,
    bid: parseInt(bid),
  };
}

function parseInput(input) {
  return input.map((line) => parseLine(line));
}

function parseHand(hand) {
  const result = {
    2: 0,
    3: 0,
    4: 0,
    5: 0,
    6: 0,
    7: 0,
    8: 0,
    9: 0,
    T: 0,
    J: 0,
    Q: 0,
    K: 0,
    A: 0,
  };
  hand.split("").forEach((card) => (result[card] += 1));
  return result;
}

export function compareHands(hand1, hand2) {
  const strength1 = getHandStrength(hand1.hand);
  const strength2 = getHandStrength(hand2.hand);

  if (strength1 !== strength2) {
    return strength1 - strength2;
  }

  for (let index = 0; index < hand1.rawHand.length; index++) {
    const rank1 = getRank(hand1.rawHand[index]);
    const rank2 = getRank(hand2.rawHand[index]);

    if (rank1 !== rank2) {
      return rank1 - rank2;
    }
  }
  return 0;
}

function getRank(card) {
  const cardOptions = [
    "J",
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    "T",
    "Q",
    "K",
    "A",
  ];
  return cardOptions.indexOf(card);
}

export function getHandStrength(hand) {
  if (isFiveOfAKind(hand)) return 7;
  if (isFourOfAKind(hand)) return 6;
  if (isFullHouse(hand)) return 5;
  if (isThreeOfAKind(hand)) return 4;
  if (isTwoPair(hand)) return 3;
  if (isOnePair(hand)) return 2;

  return 1;
}

function isFiveOfAKind(hand) {
  const amountOfJokers = hand.J;
  if (amountOfJokers > 3) {
    return true;
  }
  return Object.entries(hand).some(
    ([key, value]) => value == 5 - amountOfJokers && key !== "J"
  );
}

function isFourOfAKind(hand) {
  const amountOfJokers = hand.J;
  if (amountOfJokers > 2) return true;
  return Object.entries(hand).some(
    ([key, value]) => value === 4 - amountOfJokers && key !== "J"
  );
}

function isFullHouse(hand) {
  const amountOfJokers = hand.J;
  if (amountOfJokers === 1) {
    return Object.values(hand).filter((value) => value === 2).length === 2;
  }

  return (
    Object.values(hand).some((value) => value === 3) &&
    Object.values(hand).some((value) => value === 2)
  );
}

function isThreeOfAKind(hand) {
  const amountOfJokers = hand.J;
  if (amountOfJokers > 1) return true;
  return Object.entries(hand).some(
    ([key, value]) => value === 3 - amountOfJokers && key !== "J"
  );
}

function isTwoPair(hand) {
  return Object.values(hand).filter((value) => value === 2).length === 2;
}

function isOnePair(hand) {
  const amountOfJokers = hand.J;
  if (amountOfJokers > 0) return true;
  return Object.values(hand).filter((value) => value === 2).length === 1;
}
