namespace AdventOfCode;

static class TupleUtils
{
    
    public static (T a,T b) ToTuple2<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 2 elements");
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 2 elements");
        var b = enumerator.Current;

        if (enumerator.MoveNext()) throw new ArgumentException("More than 2 elements were present in this collection");
        return (a,b);
    }
 }

