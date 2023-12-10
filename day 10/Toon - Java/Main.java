import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
  public static void main(String[] args) {
    List<String> input = Data.get().lines().toList();
    List<List<ConnectedPipe>> network = buildUnconnectedNetwork(input);
    buildConnections(network);
    
    List<Co> visited = getPipeNetwork(network);
    System.out.println(String.format("Part 1: %s", visited.size()/2));

    List<String> result = getInsideAndOutside(network, visited);
    System.out.println(String.format("Part 2: %s", result.stream().mapToInt(s -> s.replaceAll("[^1]", "").length()).sum()));
  }

  private static List<String> getInsideAndOutside(List<List<ConnectedPipe>> network, List<Co> visited) {
    InOrOut io = new InOrOut();
    List<String> result = new ArrayList<>();;
    for (int y = 0; y < network.size(); y++) {
      StringBuilder sb = new StringBuilder();
      for (int x = 0; x < network.get(0).size(); x++) {
        if (visited.contains(new Co(x,y))){
          Pipe p = network.get(y).get(x).p;
          io.handleNewPipe(p);
          sb.append(p.symbol);
        } else{
          sb.append(io.get());
        }
      }
      String r = sb.toString();
      System.out.println(r);
      result.add(r);
    }
    return result;
  }

  private static List<Co> getPipeNetwork(List<List<ConnectedPipe>> network) {
    ConnectedPipe start = network.stream().flatMap(s -> s.stream()).filter(s -> s.p == Pipe.S).findFirst().get();
    List<Co> visited = new ArrayList<>();
    List<ConnectedPipe> todo = new ArrayList<>();
    todo.add(start);
    while (todo.size() > 0) {
      ConnectedPipe current = todo.get(0);
      visited.add(current.coordinate);
      current.connected.forEach(p -> {
        if (visited.contains(p.coordinate)) {
          return;
        }
        todo.add(p);
      });
      todo.remove(0);
    }
    return visited;
  }

  public static class InOrOut {
  
    public InOrOut() {}

    private boolean in = false;
    private Pipe previous = null;

    public void handleNewPipe(Pipe p) {
      if(p == Pipe.H || p == Pipe.G) {
        return;
      }

      if (p == Pipe.V) {
        in = !in;
        return;
      } 
      
      if(previous != null) {
        if(previous.crosses == p) {
          in = !in;
          previous = null;
        } else {
          previous = null;
        }
      } else {
        previous = p;
      }
    }

    public String get() {
      return in ? "1" : ".";
    }

  }

  private static void buildConnections(List<List<ConnectedPipe>> unconnectedNetwork) {
    for (int y = 0; y < unconnectedNetwork.size(); y++) {
      List<ConnectedPipe> row = unconnectedNetwork.get(y);
      for (int x = 0; x < row.size(); x++) {
        ConnectedPipe current = row.get(x);
        connect(current, unconnectedNetwork);
      }
    }
  }

  private static void connect(ConnectedPipe current, List<List<ConnectedPipe>> unconnectedNetwork) {
    List<ConnectedPipe> list = current.getPotentialConnectingCoordinates().stream()
        .map(coordinate -> coordinate.getPipeFrom(unconnectedNetwork))
        .filter(conP -> conP.isPresent())
        .map(conP -> conP.get())
        .filter(conP -> conP.getPotentialConnectingCoordinates().contains(current.coordinate))
        .toList();
    current.connected.addAll(list);
  }

  public static List<List<ConnectedPipe>> buildUnconnectedNetwork(List<String> input) {
    List<List<ConnectedPipe>> network = new ArrayList<>();
    for (int y = 0; y < input.size(); y++) {
      List<ConnectedPipe> r = new ArrayList<>();
      String row = input.get(y);
      for (int x = 0; x < row.length(); x++) {
        Pipe p = Pipe.parse(row.substring(x, x + 1));
        r.add(new ConnectedPipe(p, new Co(x, y), new ArrayList<>()));
      }
      network.add(r);
    }
    return network;
  }

  record ConnectedPipe(Pipe p, Co coordinate, List<ConnectedPipe> connected) {
    public List<Co> getPotentialConnectingCoordinates() {
      return p.connectsTo.stream().map(delta -> coordinate.add(delta)).toList();
    }
  }

  record Co(int x, int y) {
    public Co add(Delta d) {
      return new Co(x + d.dx, y + d.dy);
    }

    public Optional<ConnectedPipe> getPipeFrom(List<List<ConnectedPipe>> network) {
      if (y < 0 || y >= network.size() || x < 0 || x >= network.get(0).size()) {
        return Optional.empty();
      }
      return Optional.of(network.get(y).get(x));
    }
  }

  record Delta(int dx, int dy) {
  }

  enum Pipe {
    V("|", List.of(new Delta(0, -1), new Delta(0, 1))),
    H("-", List.of(new Delta(-1, 0), new Delta(1, 0))),
    NE("L", List.of(new Delta(1, 0), new Delta(0, -1))),
    NW("J", List.of(new Delta(-1, 0), new Delta(0, -1))),
    SW("7", List.of(new Delta(-1, 0), new Delta(0, 1))),
    SE("F", List.of(new Delta(1, 0), new Delta(0, 1))),
    G(".", List.of()),
    S("S", List.of(new Delta(1, 0), new Delta(-1, 0), new Delta(0, -1), new Delta(0, 1)));

    Pipe(String s, List<Delta> connectsTo) {
      this.symbol = s;
      this.connectsTo = connectsTo;
    }

    private String symbol;
    private List<Delta> connectsTo;
    private Pipe crosses;

    static {
      V.crosses = null;
      H.crosses = null;
      NE.crosses = SW;
      NW.crosses = SE;
      SW.crosses = NE;
      SE.crosses = NW;
      G.crosses = null;
      S.crosses = NE.crosses; // TODO adapt to your situation
    }

    public static Pipe parse(String i) {
      for (Pipe p : Pipe.values()) {
        if (p.symbol.equals(i)) {
          return p;
        }
      }
      throw new IllegalArgumentException();
    }
  }
}
