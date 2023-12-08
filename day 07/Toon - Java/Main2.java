import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Main2 {
  public static void main(String[] args) {
    List<Main2.Hand> sorted = Data.get().lines()
        .map(s -> Hand.parse(s))
        .sorted()
        .peek(h -> System.out.println(h))
        .toList();

    long sum = 0;
    for (int i = 0; i < sorted.size(); i++) {
      sum += sorted.get(i).bid * (i + 1);
    }
    System.out.println(sum);
  }

  public record Hand(Type type, List<Card> hand, long bid) implements Comparable<Hand> {
    static Hand parse(String s) {
      String[] split = s.split(" ");
      long bid = Long.valueOf(split[1]);
      List<Card> cards = Stream.of(split[0].split(""))
          .map(x -> Card.parse(x))
          .toList();
      Type type = Type.getType(cards);
      return new Hand(type, cards, bid);
    }

    @Override
    public int compareTo(Hand o) {
      if (this.type.ordinal() < o.type.ordinal()) {
        return 1;
      } else if (this.type.ordinal() > o.type.ordinal()) {
        return -1;
      } else {
        for (int i = 0; i < this.hand.size(); i++) {
          if (this.hand.get(i).ordinal() < o.hand.get(i).ordinal()) {
            return 1;
          } else if (this.hand.get(i).ordinal() > o.hand.get(i).ordinal()) {
            return -1;
          }
        }
      }
      throw new IllegalArgumentException();
    }

  }

  enum Card {
    A("A"),
    K("K"),
    Q("Q"),
    T("T"),
    nine("9"),
    eight("8"),
    seven("7"),
    six("6"),
    five("5"),
    four("4"),
    three("3"),
    two("2"),
    J("J");

    String label;

    Card(String label) {
      this.label = label;
    }

    static Card parse(String s) {
      for (Card c : Card.values()) {
        if (c.label.equals(s)) {
          return c;
        }
      }
      throw new IllegalArgumentException();
    }
  }

  enum Type {
    Five,
    Four,
    Full,
    Three,
    Two,
    One,
    High;

    static Type getType(List<Card> hand) {
      List<Integer> frequency = new ArrayList<>();
      for (Card card : Card.values()) {
        if (card != Card.J) {
          frequency.add(Collections.frequency(hand, card));
        }
      }
      int js = Collections.frequency(hand, Card.J);
      Collections.sort(frequency);
      int highest = frequency.get(frequency.size()-1);
      if (highest + js == 5) {
        return Five;
      } else if (highest + js == 4) {
        return Four;
      } else if ((frequency.contains(3) && frequency.contains(2))
          || (frequency.stream().filter(i -> i == 2).count() == 2 && js == 1)) {
        return Full;
      } else if (highest + js == 3) {
        return Three;
      } else if (frequency.stream().filter(i -> i == 2).count() == 2) {
        return Two;
      } else if (highest + js == 2) {
        return One;
      } else {
        return High;
      }
    }
  }
}
