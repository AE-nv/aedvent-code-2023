export function part1(input) {
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
    "2",
    "3",
    "4",
    "5",
    "6",
    "7",
    "8",
    "9",
    "T",
    "J",
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
  return Object.values(hand).some((value) => value === 5);
}

function isFourOfAKind(hand) {
  return Object.values(hand).some((value) => value === 4);
}

function isFullHouse(hand) {
  return (
    Object.values(hand).some((value) => value === 3) &&
    Object.values(hand).some((value) => value === 2)
  );
}

function isThreeOfAKind(hand) {
  return Object.values(hand).some((value) => value === 3);
}

function isTwoPair(hand) {
  return Object.values(hand).filter((value) => value === 2).length === 2;
}

function isOnePair(hand) {
  return Object.values(hand).filter((value) => value === 2).length === 1;
}
