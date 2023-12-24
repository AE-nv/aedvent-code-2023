namespace AdventOfCode;
#nullable enable

static partial class TupleUtils
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
    public static (T a,T b,T c) ToTuple3<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 3 elements");
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 3 elements");
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 3 elements");
        var c = enumerator.Current;

        if (enumerator.MoveNext()) throw new ArgumentException("More than 3 elements were present in this collection");
        return (a,b,c);
    }
    public static (T a,T b,T c,T d) ToTuple4<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 4 elements");
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 4 elements");
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 4 elements");
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 4 elements");
        var d = enumerator.Current;

        if (enumerator.MoveNext()) throw new ArgumentException("More than 4 elements were present in this collection");
        return (a,b,c,d);
    }
    public static (T a,T b,T c,T d,T e) ToTuple5<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 5 elements");
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 5 elements");
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 5 elements");
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 5 elements");
        var d = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 5 elements");
        var e = enumerator.Current;

        if (enumerator.MoveNext()) throw new ArgumentException("More than 5 elements were present in this collection");
        return (a,b,c,d,e);
    }
    public static (T a,T b,T c,T d,T e,T f) ToTuple6<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 6 elements");
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 6 elements");
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 6 elements");
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 6 elements");
        var d = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 6 elements");
        var e = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 6 elements");
        var f = enumerator.Current;

        if (enumerator.MoveNext()) throw new ArgumentException("More than 6 elements were present in this collection");
        return (a,b,c,d,e,f);
    }
    public static (T a,T b,T c,T d,T e,T f,T g) ToTuple7<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 7 elements");
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 7 elements");
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 7 elements");
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 7 elements");
        var d = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 7 elements");
        var e = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 7 elements");
        var f = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 7 elements");
        var g = enumerator.Current;

        if (enumerator.MoveNext()) throw new ArgumentException("More than 7 elements were present in this collection");
        return (a,b,c,d,e,f,g);
    }
    public static (T a,T b,T c,T d,T e,T f,T g,T h) ToTuple8<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 8 elements");
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 8 elements");
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 8 elements");
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 8 elements");
        var d = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 8 elements");
        var e = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 8 elements");
        var f = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 8 elements");
        var g = enumerator.Current;
        if (!enumerator.MoveNext()) throw new ArgumentException("Not enough elements to create a tuple of 8 elements");
        var h = enumerator.Current;

        if (enumerator.MoveNext()) throw new ArgumentException("More than 8 elements were present in this collection");
        return (a,b,c,d,e,f,g,h);
    }
    public static IEnumerable<(T a,T? b)> Chunked2<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        while (true)
        {
            (T? a,T? b) = (default,default);
            if (!enumerator.MoveNext())
            {
                yield break;
            }
            a = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b);
                yield break;
            }
            b = enumerator.Current;
            yield return (a,b);

        }
    }
    public static IEnumerable<(T a,T? b,T? c)> Chunked3<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        while (true)
        {
            (T? a,T? b,T? c) = (default,default,default);
            if (!enumerator.MoveNext())
            {
                yield break;
            }
            a = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c);
                yield break;
            }
            b = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c);
                yield break;
            }
            c = enumerator.Current;
            yield return (a,b,c);

        }
    }
    public static IEnumerable<(T a,T? b,T? c,T? d)> Chunked4<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        while (true)
        {
            (T? a,T? b,T? c,T? d) = (default,default,default,default);
            if (!enumerator.MoveNext())
            {
                yield break;
            }
            a = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d);
                yield break;
            }
            b = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d);
                yield break;
            }
            c = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d);
                yield break;
            }
            d = enumerator.Current;
            yield return (a,b,c,d);

        }
    }
    public static IEnumerable<(T a,T? b,T? c,T? d,T? e)> Chunked5<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        while (true)
        {
            (T? a,T? b,T? c,T? d,T? e) = (default,default,default,default,default);
            if (!enumerator.MoveNext())
            {
                yield break;
            }
            a = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e);
                yield break;
            }
            b = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e);
                yield break;
            }
            c = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e);
                yield break;
            }
            d = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e);
                yield break;
            }
            e = enumerator.Current;
            yield return (a,b,c,d,e);

        }
    }
    public static IEnumerable<(T a,T? b,T? c,T? d,T? e,T? f)> Chunked6<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        while (true)
        {
            (T? a,T? b,T? c,T? d,T? e,T? f) = (default,default,default,default,default,default);
            if (!enumerator.MoveNext())
            {
                yield break;
            }
            a = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f);
                yield break;
            }
            b = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f);
                yield break;
            }
            c = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f);
                yield break;
            }
            d = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f);
                yield break;
            }
            e = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f);
                yield break;
            }
            f = enumerator.Current;
            yield return (a,b,c,d,e,f);

        }
    }
    public static IEnumerable<(T a,T? b,T? c,T? d,T? e,T? f,T? g)> Chunked7<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        while (true)
        {
            (T? a,T? b,T? c,T? d,T? e,T? f,T? g) = (default,default,default,default,default,default,default);
            if (!enumerator.MoveNext())
            {
                yield break;
            }
            a = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g);
                yield break;
            }
            b = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g);
                yield break;
            }
            c = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g);
                yield break;
            }
            d = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g);
                yield break;
            }
            e = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g);
                yield break;
            }
            f = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g);
                yield break;
            }
            g = enumerator.Current;
            yield return (a,b,c,d,e,f,g);

        }
    }
    public static IEnumerable<(T a,T? b,T? c,T? d,T? e,T? f,T? g,T? h)> Chunked8<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        while (true)
        {
            (T? a,T? b,T? c,T? d,T? e,T? f,T? g,T? h) = (default,default,default,default,default,default,default,default);
            if (!enumerator.MoveNext())
            {
                yield break;
            }
            a = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g,h);
                yield break;
            }
            b = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g,h);
                yield break;
            }
            c = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g,h);
                yield break;
            }
            d = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g,h);
                yield break;
            }
            e = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g,h);
                yield break;
            }
            f = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g,h);
                yield break;
            }
            g = enumerator.Current;
            if (!enumerator.MoveNext())
            {
                yield return (a,b,c,d,e,f,g,h);
                yield break;
            }
            h = enumerator.Current;
            yield return (a,b,c,d,e,f,g,h);

        }
    }


    public static IEnumerable<(T a,T b)> Windowed2<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) yield break;
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var b = enumerator.Current;
        yield return (a,b);
        while (true)
        {
            if (!enumerator.MoveNext()) yield break;
            var c = enumerator.Current;
            (a,b) = (b,c);
            yield return (a,b);
        }
    }
    public static IEnumerable<(T a,T b,T c)> Windowed3<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) yield break;
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var c = enumerator.Current;
        yield return (a,b,c);
        while (true)
        {
            if (!enumerator.MoveNext()) yield break;
            var d = enumerator.Current;
            (a,b,c) = (b,c,d);
            yield return (a,b,c);
        }
    }
    public static IEnumerable<(T a,T b,T c,T d)> Windowed4<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) yield break;
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var d = enumerator.Current;
        yield return (a,b,c,d);
        while (true)
        {
            if (!enumerator.MoveNext()) yield break;
            var e = enumerator.Current;
            (a,b,c,d) = (b,c,d,e);
            yield return (a,b,c,d);
        }
    }
    public static IEnumerable<(T a,T b,T c,T d,T e)> Windowed5<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) yield break;
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var d = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var e = enumerator.Current;
        yield return (a,b,c,d,e);
        while (true)
        {
            if (!enumerator.MoveNext()) yield break;
            var f = enumerator.Current;
            (a,b,c,d,e) = (b,c,d,e,f);
            yield return (a,b,c,d,e);
        }
    }
    public static IEnumerable<(T a,T b,T c,T d,T e,T f)> Windowed6<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) yield break;
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var d = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var e = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var f = enumerator.Current;
        yield return (a,b,c,d,e,f);
        while (true)
        {
            if (!enumerator.MoveNext()) yield break;
            var g = enumerator.Current;
            (a,b,c,d,e,f) = (b,c,d,e,f,g);
            yield return (a,b,c,d,e,f);
        }
    }
    public static IEnumerable<(T a,T b,T c,T d,T e,T f,T g)> Windowed7<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) yield break;
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var d = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var e = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var f = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var g = enumerator.Current;
        yield return (a,b,c,d,e,f,g);
        while (true)
        {
            if (!enumerator.MoveNext()) yield break;
            var h = enumerator.Current;
            (a,b,c,d,e,f,g) = (b,c,d,e,f,g,h);
            yield return (a,b,c,d,e,f,g);
        }
    }
    public static IEnumerable<(T a,T b,T c,T d,T e,T f,T g,T h)> Windowed8<T>(this IEnumerable<T> items)
    {
        var enumerator = items.GetEnumerator();
        if (!enumerator.MoveNext()) yield break;
        var a = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var b = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var c = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var d = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var e = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var f = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var g = enumerator.Current;
        if (!enumerator.MoveNext()) yield break;
        var h = enumerator.Current;
        yield return (a,b,c,d,e,f,g,h);
        while (true)
        {
            if (!enumerator.MoveNext()) yield break;
            var i = enumerator.Current;
            (a,b,c,d,e,f,g,h) = (b,c,d,e,f,g,h,i);
            yield return (a,b,c,d,e,f,g,h);
        }
    }

    public static IEnumerable<T> AsEnumerable<T>(this (T a,T b) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
    }
    public static IEnumerable<T> AsEnumerable<T>(this (T a,T b,T c) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
        yield return tuple.c;
    }
    public static IEnumerable<T> AsEnumerable<T>(this (T a,T b,T c,T d) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
        yield return tuple.c;
        yield return tuple.d;
    }
    public static IEnumerable<T> AsEnumerable<T>(this (T a,T b,T c,T d,T e) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
        yield return tuple.c;
        yield return tuple.d;
        yield return tuple.e;
    }
    public static IEnumerable<T> AsEnumerable<T>(this (T a,T b,T c,T d,T e,T f) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
        yield return tuple.c;
        yield return tuple.d;
        yield return tuple.e;
        yield return tuple.f;
    }
    public static IEnumerable<T> AsEnumerable<T>(this (T a,T b,T c,T d,T e,T f,T g) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
        yield return tuple.c;
        yield return tuple.d;
        yield return tuple.e;
        yield return tuple.f;
        yield return tuple.g;
    }
    public static IEnumerable<T> AsEnumerable<T>(this (T a,T b,T c,T d,T e,T f,T g,T h) tuple)
    {
        yield return tuple.a;
        yield return tuple.b;
        yield return tuple.c;
        yield return tuple.d;
        yield return tuple.e;
        yield return tuple.f;
        yield return tuple.g;
        yield return tuple.h;
    }

    public static T Max<T>(this (T a,T b) tuple) where T : INumber<T>
    {
        var max = tuple.a;
         if (tuple.b > max) max = tuple.b;
        return max;
    }
    public static T Max<T>(this (T a,T b,T c) tuple) where T : INumber<T>
    {
        var max = tuple.a;
         if (tuple.b > max) max = tuple.b;
         if (tuple.c > max) max = tuple.c;
        return max;
    }
    public static T Max<T>(this (T a,T b,T c,T d) tuple) where T : INumber<T>
    {
        var max = tuple.a;
         if (tuple.b > max) max = tuple.b;
         if (tuple.c > max) max = tuple.c;
         if (tuple.d > max) max = tuple.d;
        return max;
    }
    public static T Max<T>(this (T a,T b,T c,T d,T e) tuple) where T : INumber<T>
    {
        var max = tuple.a;
         if (tuple.b > max) max = tuple.b;
         if (tuple.c > max) max = tuple.c;
         if (tuple.d > max) max = tuple.d;
         if (tuple.e > max) max = tuple.e;
        return max;
    }
    public static T Max<T>(this (T a,T b,T c,T d,T e,T f) tuple) where T : INumber<T>
    {
        var max = tuple.a;
         if (tuple.b > max) max = tuple.b;
         if (tuple.c > max) max = tuple.c;
         if (tuple.d > max) max = tuple.d;
         if (tuple.e > max) max = tuple.e;
         if (tuple.f > max) max = tuple.f;
        return max;
    }
    public static T Max<T>(this (T a,T b,T c,T d,T e,T f,T g) tuple) where T : INumber<T>
    {
        var max = tuple.a;
         if (tuple.b > max) max = tuple.b;
         if (tuple.c > max) max = tuple.c;
         if (tuple.d > max) max = tuple.d;
         if (tuple.e > max) max = tuple.e;
         if (tuple.f > max) max = tuple.f;
         if (tuple.g > max) max = tuple.g;
        return max;
    }
    public static T Max<T>(this (T a,T b,T c,T d,T e,T f,T g,T h) tuple) where T : INumber<T>
    {
        var max = tuple.a;
         if (tuple.b > max) max = tuple.b;
         if (tuple.c > max) max = tuple.c;
         if (tuple.d > max) max = tuple.d;
         if (tuple.e > max) max = tuple.e;
         if (tuple.f > max) max = tuple.f;
         if (tuple.g > max) max = tuple.g;
         if (tuple.h > max) max = tuple.h;
        return max;
    }

    public static T Min<T>(this (T a,T b) tuple) where T : INumber<T>
    {
        var min = tuple.a;
         if (tuple.b < min) min = tuple.b;
        return min;
    }
    public static T Min<T>(this (T a,T b,T c) tuple) where T : INumber<T>
    {
        var min = tuple.a;
         if (tuple.b < min) min = tuple.b;
         if (tuple.c < min) min = tuple.c;
        return min;
    }
    public static T Min<T>(this (T a,T b,T c,T d) tuple) where T : INumber<T>
    {
        var min = tuple.a;
         if (tuple.b < min) min = tuple.b;
         if (tuple.c < min) min = tuple.c;
         if (tuple.d < min) min = tuple.d;
        return min;
    }
    public static T Min<T>(this (T a,T b,T c,T d,T e) tuple) where T : INumber<T>
    {
        var min = tuple.a;
         if (tuple.b < min) min = tuple.b;
         if (tuple.c < min) min = tuple.c;
         if (tuple.d < min) min = tuple.d;
         if (tuple.e < min) min = tuple.e;
        return min;
    }
    public static T Min<T>(this (T a,T b,T c,T d,T e,T f) tuple) where T : INumber<T>
    {
        var min = tuple.a;
         if (tuple.b < min) min = tuple.b;
         if (tuple.c < min) min = tuple.c;
         if (tuple.d < min) min = tuple.d;
         if (tuple.e < min) min = tuple.e;
         if (tuple.f < min) min = tuple.f;
        return min;
    }
    public static T Min<T>(this (T a,T b,T c,T d,T e,T f,T g) tuple) where T : INumber<T>
    {
        var min = tuple.a;
         if (tuple.b < min) min = tuple.b;
         if (tuple.c < min) min = tuple.c;
         if (tuple.d < min) min = tuple.d;
         if (tuple.e < min) min = tuple.e;
         if (tuple.f < min) min = tuple.f;
         if (tuple.g < min) min = tuple.g;
        return min;
    }
    public static T Min<T>(this (T a,T b,T c,T d,T e,T f,T g,T h) tuple) where T : INumber<T>
    {
        var min = tuple.a;
         if (tuple.b < min) min = tuple.b;
         if (tuple.c < min) min = tuple.c;
         if (tuple.d < min) min = tuple.d;
         if (tuple.e < min) min = tuple.e;
         if (tuple.f < min) min = tuple.f;
         if (tuple.g < min) min = tuple.g;
         if (tuple.h < min) min = tuple.h;
        return min;
    }


    public static T MaxBy<T>(this (T a,T b) tuple, Func<T, int> f) 
    {
        var max = tuple.a;
        var maxValue = f(max);
        if (f(tuple.b) > maxValue) 
        {
           max = tuple.b;
           maxValue = f(max);
        }
        return max;
    }
    public static T MaxBy<T>(this (T a,T b,T c) tuple, Func<T, int> f) 
    {
        var max = tuple.a;
        var maxValue = f(max);
        if (f(tuple.b) > maxValue) 
        {
           max = tuple.b;
           maxValue = f(max);
        }
        if (f(tuple.c) > maxValue) 
        {
           max = tuple.c;
           maxValue = f(max);
        }
        return max;
    }
    public static T MaxBy<T>(this (T a,T b,T c,T d) tuple, Func<T, int> f) 
    {
        var max = tuple.a;
        var maxValue = f(max);
        if (f(tuple.b) > maxValue) 
        {
           max = tuple.b;
           maxValue = f(max);
        }
        if (f(tuple.c) > maxValue) 
        {
           max = tuple.c;
           maxValue = f(max);
        }
        if (f(tuple.d) > maxValue) 
        {
           max = tuple.d;
           maxValue = f(max);
        }
        return max;
    }
    public static T MaxBy<T>(this (T a,T b,T c,T d,T e) tuple, Func<T, int> f) 
    {
        var max = tuple.a;
        var maxValue = f(max);
        if (f(tuple.b) > maxValue) 
        {
           max = tuple.b;
           maxValue = f(max);
        }
        if (f(tuple.c) > maxValue) 
        {
           max = tuple.c;
           maxValue = f(max);
        }
        if (f(tuple.d) > maxValue) 
        {
           max = tuple.d;
           maxValue = f(max);
        }
        if (f(tuple.e) > maxValue) 
        {
           max = tuple.e;
           maxValue = f(max);
        }
        return max;
    }
    public static T MaxBy<T>(this (T a,T b,T c,T d,T e,T f) tuple, Func<T, int> f) 
    {
        var max = tuple.a;
        var maxValue = f(max);
        if (f(tuple.b) > maxValue) 
        {
           max = tuple.b;
           maxValue = f(max);
        }
        if (f(tuple.c) > maxValue) 
        {
           max = tuple.c;
           maxValue = f(max);
        }
        if (f(tuple.d) > maxValue) 
        {
           max = tuple.d;
           maxValue = f(max);
        }
        if (f(tuple.e) > maxValue) 
        {
           max = tuple.e;
           maxValue = f(max);
        }
        if (f(tuple.f) > maxValue) 
        {
           max = tuple.f;
           maxValue = f(max);
        }
        return max;
    }
    public static T MaxBy<T>(this (T a,T b,T c,T d,T e,T f,T g) tuple, Func<T, int> f) 
    {
        var max = tuple.a;
        var maxValue = f(max);
        if (f(tuple.b) > maxValue) 
        {
           max = tuple.b;
           maxValue = f(max);
        }
        if (f(tuple.c) > maxValue) 
        {
           max = tuple.c;
           maxValue = f(max);
        }
        if (f(tuple.d) > maxValue) 
        {
           max = tuple.d;
           maxValue = f(max);
        }
        if (f(tuple.e) > maxValue) 
        {
           max = tuple.e;
           maxValue = f(max);
        }
        if (f(tuple.f) > maxValue) 
        {
           max = tuple.f;
           maxValue = f(max);
        }
        if (f(tuple.g) > maxValue) 
        {
           max = tuple.g;
           maxValue = f(max);
        }
        return max;
    }
    public static T MaxBy<T>(this (T a,T b,T c,T d,T e,T f,T g,T h) tuple, Func<T, int> f) 
    {
        var max = tuple.a;
        var maxValue = f(max);
        if (f(tuple.b) > maxValue) 
        {
           max = tuple.b;
           maxValue = f(max);
        }
        if (f(tuple.c) > maxValue) 
        {
           max = tuple.c;
           maxValue = f(max);
        }
        if (f(tuple.d) > maxValue) 
        {
           max = tuple.d;
           maxValue = f(max);
        }
        if (f(tuple.e) > maxValue) 
        {
           max = tuple.e;
           maxValue = f(max);
        }
        if (f(tuple.f) > maxValue) 
        {
           max = tuple.f;
           maxValue = f(max);
        }
        if (f(tuple.g) > maxValue) 
        {
           max = tuple.g;
           maxValue = f(max);
        }
        if (f(tuple.h) > maxValue) 
        {
           max = tuple.h;
           maxValue = f(max);
        }
        return max;
    }

    public static T MinBy<T>(this (T a,T b) tuple, Func<T, int> f)
    {
        var min = tuple.a;
        var minValue = f(min);
        if (f(tuple.b) < minValue) 
        {
            min = tuple.b;
            minValue = f(min);
        }
        return min;
    }
    public static T MinBy<T>(this (T a,T b,T c) tuple, Func<T, int> f)
    {
        var min = tuple.a;
        var minValue = f(min);
        if (f(tuple.b) < minValue) 
        {
            min = tuple.b;
            minValue = f(min);
        }
        if (f(tuple.c) < minValue) 
        {
            min = tuple.c;
            minValue = f(min);
        }
        return min;
    }
    public static T MinBy<T>(this (T a,T b,T c,T d) tuple, Func<T, int> f)
    {
        var min = tuple.a;
        var minValue = f(min);
        if (f(tuple.b) < minValue) 
        {
            min = tuple.b;
            minValue = f(min);
        }
        if (f(tuple.c) < minValue) 
        {
            min = tuple.c;
            minValue = f(min);
        }
        if (f(tuple.d) < minValue) 
        {
            min = tuple.d;
            minValue = f(min);
        }
        return min;
    }
    public static T MinBy<T>(this (T a,T b,T c,T d,T e) tuple, Func<T, int> f)
    {
        var min = tuple.a;
        var minValue = f(min);
        if (f(tuple.b) < minValue) 
        {
            min = tuple.b;
            minValue = f(min);
        }
        if (f(tuple.c) < minValue) 
        {
            min = tuple.c;
            minValue = f(min);
        }
        if (f(tuple.d) < minValue) 
        {
            min = tuple.d;
            minValue = f(min);
        }
        if (f(tuple.e) < minValue) 
        {
            min = tuple.e;
            minValue = f(min);
        }
        return min;
    }
    public static T MinBy<T>(this (T a,T b,T c,T d,T e,T f) tuple, Func<T, int> f)
    {
        var min = tuple.a;
        var minValue = f(min);
        if (f(tuple.b) < minValue) 
        {
            min = tuple.b;
            minValue = f(min);
        }
        if (f(tuple.c) < minValue) 
        {
            min = tuple.c;
            minValue = f(min);
        }
        if (f(tuple.d) < minValue) 
        {
            min = tuple.d;
            minValue = f(min);
        }
        if (f(tuple.e) < minValue) 
        {
            min = tuple.e;
            minValue = f(min);
        }
        if (f(tuple.f) < minValue) 
        {
            min = tuple.f;
            minValue = f(min);
        }
        return min;
    }
    public static T MinBy<T>(this (T a,T b,T c,T d,T e,T f,T g) tuple, Func<T, int> f)
    {
        var min = tuple.a;
        var minValue = f(min);
        if (f(tuple.b) < minValue) 
        {
            min = tuple.b;
            minValue = f(min);
        }
        if (f(tuple.c) < minValue) 
        {
            min = tuple.c;
            minValue = f(min);
        }
        if (f(tuple.d) < minValue) 
        {
            min = tuple.d;
            minValue = f(min);
        }
        if (f(tuple.e) < minValue) 
        {
            min = tuple.e;
            minValue = f(min);
        }
        if (f(tuple.f) < minValue) 
        {
            min = tuple.f;
            minValue = f(min);
        }
        if (f(tuple.g) < minValue) 
        {
            min = tuple.g;
            minValue = f(min);
        }
        return min;
    }
    public static T MinBy<T>(this (T a,T b,T c,T d,T e,T f,T g,T h) tuple, Func<T, int> f)
    {
        var min = tuple.a;
        var minValue = f(min);
        if (f(tuple.b) < minValue) 
        {
            min = tuple.b;
            minValue = f(min);
        }
        if (f(tuple.c) < minValue) 
        {
            min = tuple.c;
            minValue = f(min);
        }
        if (f(tuple.d) < minValue) 
        {
            min = tuple.d;
            minValue = f(min);
        }
        if (f(tuple.e) < minValue) 
        {
            min = tuple.e;
            minValue = f(min);
        }
        if (f(tuple.f) < minValue) 
        {
            min = tuple.f;
            minValue = f(min);
        }
        if (f(tuple.g) < minValue) 
        {
            min = tuple.g;
            minValue = f(min);
        }
        if (f(tuple.h) < minValue) 
        {
            min = tuple.h;
            minValue = f(min);
        }
        return min;
    }

}

