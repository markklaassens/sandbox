package puzzles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * Puzzle - Death First Search: Episode 1.
 * <a href="https://www.codingame.com/training/medium/death-first-search-episode-1">Link to related puzzle on CodinGame.</a>
 *
 * <p>This is a slight modification of puzzle mentioned above. The goal is to prevent a virus from reaching any of the exit
 * gateways in a network of nodes by destroying links, to prevent the virus from reaching the exit gateway. The virus starts
 * at a specific node, and can move one node at a time and after each move a links get destroyed.</p>
 *
 * <p>The program begins by taking in two integers: the number of links in the network and the number of exit gateways.
 * The following pairs of integers represent the links between nodes in the network, and the subsequent integers represent
 * the exit gateways.</p>
 *
 * <p>The program uses a breadth-first search (BFS) algorithm to find and sever the link closest to the virus that leads to
 * an exit gateway. The BFS starts from the virus's current position and explores neighboring nodes until it finds an exit
 * gateway. Once a link to an exit gateway is found, the link is destroyed, and the program returns to the main loop to
 * continue processing until no more links can be destroyed or the virus has reached an exit.</p>
 */
public class DeathFirstSearchE1 {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int numOfLinks = in.nextInt(); // the number of links
    int numOfExits = in.nextInt(); // the number of exit gateways

    Map<Integer, List<Integer>> graph = new HashMap<>();
    Set<Integer> exits = new HashSet<>();

    for (int i = 0; i < numOfLinks; i++) {
      int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
      int N2 = in.nextInt();
      graph.putIfAbsent(N2, new ArrayList<>());
      graph.putIfAbsent(N1, new ArrayList<>());
      graph.get(N1).add(N2);
      graph.get(N2).add(N1);
    }

    for (int i = 0; i < numOfExits; i++) {
      int EI = in.nextInt(); // the index of a gateway node
      exits.add(EI);
    }

    int virusPosition;
    // game loop
    do {
      virusPosition = in.nextInt();
    } while (destroyLinkClosestToExit(graph, exits, virusPosition) && !exits.contains(virusPosition));
    in.close();
  }

  private static boolean destroyLinkClosestToExit(
      Map<Integer, List<Integer>> graph,
      Set<Integer> exits,
      int virusPosition
  ) {
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    queue.add(virusPosition);

    while (!queue.isEmpty()) {
      int currentNode = queue.poll();

      for (int neighbor : graph.get(currentNode)) {
        if (!visited.contains(neighbor)) {
          queue.add(neighbor);
          visited.add(neighbor);

          // remove the link and return to game loop when we reach the closest exit position
          if (exits.contains(neighbor)) {
            graph.get(currentNode).remove(neighbor);
            graph.get(neighbor).remove(currentNode);
            System.out.println(currentNode + " " + neighbor);
            return true;
          }
        }
      }
    }
    return false;
  }
}
