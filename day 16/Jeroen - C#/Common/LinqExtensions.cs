using System.Collections.Concurrent;

namespace AdventOfCode;

static class LinqExtensions
{
    public static IEnumerable<T> AsEnumerable<T>(this T item)
    {
        yield return item;
    }
    public static IEnumerable<T> AsEnumerable<T>(this (T a,T b) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
    }

}
