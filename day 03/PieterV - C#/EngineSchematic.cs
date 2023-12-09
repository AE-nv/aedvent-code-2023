using AoCFramework.Util;

namespace Day2;

public class EngineSchematic
{
    private readonly IList<IList<SchemaSymbol>> _schematic = new List<IList<SchemaSymbol>>();

    public EngineSchematic()
    {
    }

    public EngineSchematic AddSchemaRow(string row)
    {
        var parsedRow = row.Select(c => new SchemaSymbol(c.ToString())).ToList();
        _schematic.Add(parsedRow);
        return this;
    }

    public override string ToString()
    {
        return _schematic.Select(row => row.ConcatBy("  ")).ConcatBy("\n");
    }

    public IList<int> FindNumbersAdjacentToSymbols()
    {
        return _schematic.SelectMany(GetNumbersInRowAdjacentToSymbols).Select(_ => _.Number).ToList();
    }


    public IList<int> FindNumbersAdjacentToSymbol(SchemaSymbol s)
    {
        return _schematic.SelectMany((_, index) => GetNumbersInRowAdjacentToSymbol(_, index, s)).Select(_ => _.Number)
            .ToList();
    }

    private IEnumerable<SchemaNumber> GetNumbersInRowAdjacentToSymbol(IList<SchemaSymbol> row, int rowNr,
        SchemaSymbol schemaSymbol)
    {
        throw new NotImplementedException();
    }

    private IEnumerable<SchemaNumber> GetNumbersInRowAdjacentToSymbols(IList<SchemaSymbol> row, int rowNr)
    {
        return ParseNumbers(row, rowNr, 0, null, new List<SchemaNumber>())
            // .PrintDebug(s => s.ConcatBy(","))
            .Where(IsAdjacentToAnySymbol);
    }

    private bool IsAdjacentToAnySymbol(SchemaNumber schemaNumber)
    {
        return schemaNumber.SpanningPositions.Any(IsAdjacentToAnySymbol);
    }

    private bool IsAdjacentToAnySymbol(Position position)
    {
        var surroundingPositions = new[]
        {
            GetBoundedPosition(_schematic, position.Row - 1, position.Col - 1),
            GetBoundedPosition(_schematic, position.Row - 1, position.Col),
            GetBoundedPosition(_schematic, position.Row - 1, position.Col + 1),
            GetBoundedPosition(_schematic, position.Row, position.Col - 1),
            GetBoundedPosition(_schematic, position.Row, position.Col + 1),
            GetBoundedPosition(_schematic, position.Row + 1, position.Col - 1),
            GetBoundedPosition(_schematic, position.Row + 1, position.Col),
            GetBoundedPosition(_schematic, position.Row + 1, position.Col + 1),
        };
        return surroundingPositions
            // .PrintDebug(_ => _.ConcatBy(","))
            .Select(pos => _schematic[pos.Row][pos.Col])
            .Any(_ => _.SymbolType == SymbolType.Symbol);
    }

    private Position GetBoundedPosition(IList<IList<SchemaSymbol>> schematic, int positionRow, int positionCol)
    {
        var rowNr = Math.Max(Math.Min(positionRow, schematic.Count - 1), 0);
        var colNr = Math.Max(Math.Min(positionCol, schematic[rowNr].Count - 1), 0);
        return new Position(rowNr, colNr);
    }

    private IList<SchemaNumber> ParseNumbers(IList<SchemaSymbol> row, int rowNr, int colNr,
        SchemaSymbol? previousSymbol,
        IList<SchemaNumber> accumulator)
    {
        // if colNr is "out of bounds" return accumulator
        if (colNr + 1 > row.Count)
            return accumulator;
        var currentSymbol = row[colNr];
        var previousSymbolType = previousSymbol?.SymbolType ?? SymbolType.Empty;
        if (currentSymbol.SymbolType == SymbolType.Number && previousSymbolType != SymbolType.Number)
        {
            accumulator.Add(new SchemaNumber());
        }

        if (currentSymbol.SymbolType == SymbolType.Number)
        {
            if (!accumulator.Any())
            {
                accumulator.Add(new SchemaNumber());
            }

            accumulator.Last().AddSymbol(currentSymbol, new Position(rowNr, colNr));
        }

        return ParseNumbers(row, rowNr, colNr + 1, currentSymbol, accumulator);
    }
}

record Position(int Row, int Col)
{
    public override string ToString()
    {
        return $"({Row},{Col})";
    }
}

class SchemaNumber
{
    public SchemaNumber()
    {
        Number = 0;
        SpanningPositions = new List<Position>();
    }

    public IList<Position> SpanningPositions { get; }

    public override string ToString()
    {
        return Number.ToString();
    }

    public int Number { get; private set; }

    public SchemaNumber AddSymbol(SchemaSymbol schemaSymbol, Position position)
    {
        if (schemaSymbol.SymbolType != SymbolType.Number)
        {
            return this;
        }

        Number = (Number.ToString() + schemaSymbol.OriginalSymbol).ToInt();
        SpanningPositions.Add(position);
        return this;
    }
}

public record SchemaSymbol(string OriginalSymbol)
{
    public SymbolType SymbolType { get; } = OriginalSymbol switch
    {
        "." => SymbolType.Empty,
        _ when IsDigit(OriginalSymbol) => SymbolType.Number,
        _ when !IsDigit(OriginalSymbol) => SymbolType.Symbol,
    };

    private static bool IsDigit(string originalSymbol)
    {
        return int.TryParse(originalSymbol, out var any);
    }

    public override string ToString()
    {
        return SymbolType.ToString();
    }
}

public enum SymbolType
{
    Empty,
    Symbol,
    Number,
}