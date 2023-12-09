var data = File.ReadAllLines("input.txt");
var result = GetResult(data);
Console.WriteLine(result);

const StringSplitOptions SplitOptions = StringSplitOptions.TrimEntries | StringSplitOptions.RemoveEmptyEntries;

record Card(int Id, HashSet<int> WinningNumbers, HashSet<int> Numbers)
{
		public int CountOfMatchingNumbers => Numbers.Count(WinningNumbers.Contains);
};

static int GetResult(IEnumerable<string> data)
{
		var originalCards = data.Select(ParseCard).ToDictionary(x => x.Id, x => x);
		var cards = new Queue<Card>(originalCards.Values);

		var result = 0;
		
		while (cards.Count != 0)
		{
				result++;
				var card = cards.Dequeue();
				var newCardIds = Enumerable.Range(card.Id + 1, card.CountOfMatchingNumbers);
				foreach (var id in newCardIds) cards.Enqueue(originalCards[id]);
		}

		return result;
}

static Card ParseCard(string entry)
{
		var split = entry.Split(':', SplitOptions);
		var cardNumber = int.Parse(split.First().Replace("Card ", string.Empty));
		
		var secondSplit = split.Last().Split('|', SplitOptions);
		var winningNumbers = secondSplit.First().Split(' ', SplitOptions).Select(int.Parse).ToHashSet();
		var numbers = secondSplit.Last().Split(' ', SplitOptions).Select(int.Parse).ToHashSet();

		return new Card(cardNumber, winningNumbers, numbers);
}
