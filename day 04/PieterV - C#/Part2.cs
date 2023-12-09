using AoCFramework;
using AoCFramework.Util;

namespace Day4;

public class Part2 : IAoCSolver
{
    public string Solve(string input)
    {
        var scratchCards = input.SplitOnNewline()
            .Select(ScratchCard.Parse)
            .ToList();
        return GetTotalNrOfScratchCardsRecursive(scratchCards, scratchCards, new List<ScratchCard>()).ToString();
    }

    private static int GetTotalNrOfScratchCardsRecursive(IEnumerable<ScratchCard> originalSet,
        IEnumerable<ScratchCard> cardsToHandle,
        IEnumerable<ScratchCard> handledCards)
    {
        var scratchCards = cardsToHandle.ToList();
        if (scratchCards.Count == 0) return handledCards.Count();
        var (head, tail) = scratchCards.Destructure();
        var nrOfWinningNumbers = head.GetMatchingNumbers().Count;
        var originalScratchCardSet = originalSet.ToList();
        var newCards = originalScratchCardSet.Where(_ =>
            _.CardNumber > head.CardNumber &&
            _.CardNumber <= head.CardNumber + nrOfWinningNumbers &&
            nrOfWinningNumbers > 0);
        var newHandledCards = handledCards.Append(head);
        var newCardsToHandle = tail.Concat(newCards);
        return GetTotalNrOfScratchCardsRecursive(originalScratchCardSet, newCardsToHandle, newHandledCards);
    }

    private static int GetTotalNrOfScratchCards(IEnumerable<ScratchCard> initialCards)
    {
        var scratchCards = initialCards.ToList();
        var scratchCardStack = new Stack<ScratchCard>(scratchCards);
        var totalNrOfScratchCards = scratchCards.Count();
        var nrOfHandledCards = 0;
        while (scratchCardStack.Count > 0)
        {
            var card = scratchCardStack.Pop();
            var nrOfWinningNumbers = card.GetMatchingNumbers().Count;
            var newCards = scratchCards.Where(_ =>
                _.CardNumber > card.CardNumber &&
                _.CardNumber <= Math.Min(card.CardNumber + nrOfWinningNumbers, totalNrOfScratchCards)).ToList();
            newCards.ForEach(c => scratchCardStack.Push(c));
            nrOfHandledCards++;
        }

        return nrOfHandledCards;
    }
}