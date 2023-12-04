namespace AoCFramework.Util;

public static class Extensions
{
    public static string[] SplitOnNewline(this string input)
    {
        return input.Split("\r", StringSplitOptions.RemoveEmptyEntries);
    }

    public static string ConcatBy<T>(this IEnumerable<T> stringToConcat, string separator)
    {
        return string.Join(separator, stringToConcat);
    }

    public static TSame PrintDebug<TSame>(this TSame input, Func<TSame, string>? formatter = null)

    {
        Console.WriteLine(formatter != null ? formatter(input) : input.ToString());
        return input;
    }

    public static int Product(this IEnumerable<int> input)
    {
        return input.Aggregate(1, (acc, i) => acc * i);
    }

    public static HashSet<T> AddAndReturn<T>(this HashSet<T> set, T item)
    {
        set.Add(item);
        return set;
    }

    public static TValue TryOrFallback<TKey, TValue>(this IDictionary<TKey, TValue> dict, TKey key,
        TValue fallback = default)
    {
        return dict.TryGetValue(key, out var value) ? value : fallback;
    }
}