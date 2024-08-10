package games;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Path of the Virus. Game is based on the breadth first search algorithm.
 *
 * <p>This game generates a random playing field that consists of nodes and links. You are a virus that infects the system
 * and needs to find the quickest way to an exit and enter the next level of the system. When you have finished the 5th level
 * you've won the game. In the first two levels the system hasn't detected the virus yet and you're free to move as long as
 * you don't run out of turns. From level 3 the system will try to cut you off! After each move the system will destroy a
 * link between your current node and the exit. You can use decoys to misdirect the search algorithm of the system. In the
 * 3rd and 4th level you have 2 decoys, in the 5th last level you have 3 decoys!</p>
 *
 * <p>In the beginning of each turn you get all the nodes and links, exit nodes, number of turns and number of decoys.
 * In addition in the first turn you get all possible start nodes.</p>
 *
 * Potential improvements:
 * <ul>
 * <li>Improve graph validation to ensure enough challenge in each level.</li>
 * <li>Add calculation to determine the minimum amount of decoys needed and add difficulty setting.</li>
 * <li>Add a visual representation in a webapp using Spring Boot with HTMX.</li>
 * <li>Develop a foundation for an algorithm that can beat the game, allowing other developers to experiment with it.</li>
 * </ul>
 *
 * <p>If you want to run this game I suggest to clone the repository and open it in IntelliJ IDE and run the main method.
 * You need to have a JDK installed to run this.</p>
 */
public class PathOfTheVirus {

  static Scanner in = new Scanner(System.in);
  static Random rand = new Random();
  static int[] levels = new int[]{5, 10, 10, 5, 15};
  static Map<Integer, Set<Integer>> graph;
  static Set<Integer> exits;
  static Set<Integer> starts;
  static int virusNode;
  static int decoyNode;

  public static void main(String[] args) {
    System.out.println(
        "\nFIND YOUR WAY FROM A START NODE TO AN EXIT NODE WITHIN THE GIVEN TURNS!"); // add blank line for readability
    for (int levelIndex = 0; levelIndex < levels.length; levelIndex++) {
      System.out.println(
          "\nLEVEL " + (levelIndex + 1)); // add blank line for readability, + 1 because levelIndex starts on 0
      boolean firstTurn = true;
      int amountOfDecoys = levelIndex > 1 ? Math.max(levels[levelIndex] / 5, 2) : 0; //the first 2 levels don't have decoys
      int numOfNodes = levels[levelIndex];
      int numOfExits = levels[levelIndex] / 5;// create 1 exit node per 5 nodes

      boolean validPlayingField;
      do {
        graph = generateValidGraph(numOfNodes);
        exits = generateExits(numOfExits);
        starts = generateStarts();
        validPlayingField =
            (exits.size() * 5) == numOfNodes && !starts.isEmpty(); // 1 exit per 5 nodes and min 1 start node
      } while (!validPlayingField);
      int numberOfTurns = generateNumberOfTurns();

      while (true) {
        int searchNode;
        boolean useDecoy;
        System.out.println("\nALL NODES AND LINKS: " + graph.entrySet()); // add blank line for readability
        System.out.println("ALL EXIT NODES: " + exits);
        System.out.println("NUMBER OF TURNS: " + numberOfTurns);
        System.out.println("AMOUNT OF DECOYS: " + amountOfDecoys);
        if (firstTurn) {
          useDecoy = playFirstTurn(amountOfDecoys);
          if (useDecoy) {
            setDecoy();
            --amountOfDecoys;
            searchNode = decoyNode;
          } else {
            searchNode = virusNode;
          }
          firstTurn = false;
        } else {
          useDecoy = playNormalTurn(amountOfDecoys);
          if (useDecoy) {
            setDecoy();
            --amountOfDecoys;
            searchNode = decoyNode;
          } else {
            searchNode = virusNode;
          }
        }
        if (exits.contains(virusNode)) {
          System.out.println(
              "SUCCESS YOU'VE COMPLETED LEVEL " + (levelIndex + 1)); // + 1 because levelIndex starts on 0
          break;
        }
        if (levelIndex > 1) {
          destroyLinkBetweenSearchNodeAndClosestExit(searchNode);
          if (!checkIfVirusCanReachExit(virusNode)) {
            System.out.println("FAILURE, THE VIRUS CAN'T REACH AN EXIT ANYMORE!");
            if (tryAgain()) {
              --levelIndex;
              break;
            } else {
              in.close();
              System.exit(0);
            }
          }
        }
        if (--numberOfTurns == 0) {
          System.out.println("FAILURE, YOU RAN OUT OF TURNS!");
          if (tryAgain()) {
            --levelIndex;
            break;
          } else {
            in.close();
            System.exit(0);
          }
        }
      }
    }
    System.out.println("YOU HAVE COMPLETED THE GAME, YOU ARE LEGEND!");
    in.close();
    System.exit(0);
  }

  private static Map<Integer, Set<Integer>> generateValidGraph(int numOfNodes) {
    Map<Integer, Set<Integer>> graph = new HashMap<>();
    int maxNumOfLinksPerNode = numOfNodes - 1;
    for (int i = 1; i <= numOfNodes; i++) {
      graph.putIfAbsent(i, new HashSet<>());
      while (graph.get(i).size() < rand.nextInt(maxNumOfLinksPerNode) + 1) {
        int newNeighborNode = rand.nextInt(numOfNodes) + 1;
        if (i == newNeighborNode) {
          continue;
        } else if (graph.get(i).contains(newNeighborNode)) {
          continue;
        }
        graph.get(i).add(newNeighborNode);
        graph.putIfAbsent(newNeighborNode, new HashSet<>());
        graph.get(newNeighborNode).add(i);
      }
    }
    return graph;
  }

  private static Set<Integer> generateExits(int numOfExits) {
    Set<Integer> exits = new HashSet<>();
    List<Integer> nodeList = new ArrayList<>(graph.keySet());
    Collections.shuffle(nodeList);
    Queue<Integer> queue = new LinkedList<>(nodeList);
    while (exits.size() < numOfExits && !queue.isEmpty()) {
      Integer currentNode = queue.poll();
      for (int neighbor : graph.get(currentNode)) {
        if (graph.get(neighbor).size() > 1) {
          exits.add(currentNode);
          break;
        }
      }
    }
    return exits;
  }

  private static Set<Integer> generateStarts() {
    Set<Integer> starts = new HashSet<>(graph.keySet());
    starts.removeAll(exits);
    Set<Integer> invalidNodes = new HashSet<>();
    for (Integer exitNode : exits) {
      invalidNodes.clear();
      for (Integer node : starts) {
        if (graph.get(node).contains(exitNode)) {
          invalidNodes.add(node);
        }
      }
      starts.removeAll(invalidNodes);
    }
    return starts;
  }

  private static int generateNumberOfTurns() {
    List<Integer> shortestPath = new ArrayList<>();
    Queue<List<Integer>> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    for (Integer node : starts) {
      List<Integer> initialPath = new ArrayList<>();
      initialPath.add(node);
      queue.add(initialPath);
      visited.add(node);

      while (!queue.isEmpty()) {
        List<Integer> path = queue.poll();
        int currentNode = path.getLast();
        if (exits.contains(currentNode)) {
          shortestPath = path;
        }

        for (Integer neighbor : graph.get(currentNode)) {
          if (!visited.contains(neighbor)) {
            visited.add(neighbor);
            List<Integer> extendedPath = new ArrayList<>(path);
            extendedPath.add(neighbor);
            queue.add(extendedPath);
          }
        }
      }
    }
    return shortestPath.size();
  }

  private static boolean playFirstTurn(int amountOfDecoys) {
    System.out.println("ALL START NODES: " + starts);
    System.out.println("ENTER VIRUS START NODE: ");
    while (true) {
      virusNode = in.nextInt();
      if (!starts.contains(virusNode)) {
        System.out.println("INVALID NODE, TRY AGAIN!");
        System.out.println("ALL START NODES:  " + starts);
        continue;
      }
      break;
    }
    if (amountOfDecoys > 0) {
      System.out.println("USE DECOY? THIS TURN (Y/N): ");
      while (true) {
        String answer = in.next();
        if (answer.equalsIgnoreCase("Y")) {
          return true;
        } else if (answer.equalsIgnoreCase("N")) {
          return false;
        } else {
          System.out.println("INVALID ANSWER, ENTER 'Y' TO USE DECOY, OR 'N' TO NOT USE DECOY THIS TURN.");
        }
      }
    }
    return false;
  }

  private static void setDecoy() {
    while (true) {
      System.out.println("ENTER DECOY NODE: ");
      decoyNode = in.nextInt();
      if (!graph.containsKey(decoyNode)) {
        System.out.println("INVALID NODE, TRY AGAIN!");
        System.out.println("ALL NODES:  " + graph.keySet());
        continue;
      }
      return;
    }
  }

  private static boolean playNormalTurn(int amountOfDecoys) {
    boolean useDecoy = false;
    System.out.println("CURRENT NODE: " + virusNode);
    System.out.println("ENTER NEW VIRUS NODE: ");
    int newNode;
    while (true) {
      newNode = in.nextInt();
      if (!graph.get(virusNode).contains(newNode)) {
        System.out.println("INVALID MOVE, TRY AGAIN!");
      } else {
        break;
      }
    }
    if (amountOfDecoys > 0) {
      System.out.println("USE DECOY THIS TURN? (Y/N): ");
      while (true) {
        String answer = in.next();
        if (answer.equalsIgnoreCase("Y")) {
          useDecoy = true;
          break;
        } else if (answer.equalsIgnoreCase("N")) {
          break;
        } else {
          System.out.println("INVALID ANSWER, ENTER 'Y' TO USE DECOY, OR 'N' TO NOT USE DECOY THIS TURN.");
        }
      }
    }
    virusNode = newNode;
    return useDecoy;
  }

  private static void destroyLinkBetweenSearchNodeAndClosestExit(int newVirusNode) {
    int[] linkToDestroy = new int[2];
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>(exits);

    while (!queue.isEmpty()) {
      int currentNode = queue.poll();
      List<Integer> shuffledNeighbors = new ArrayList<>(graph.get(currentNode));
      Collections.shuffle(shuffledNeighbors);
      for (int neighborNode : shuffledNeighbors) {
        if (!visited.contains(neighborNode)) {
          queue.add(neighborNode);
          visited.add(neighborNode);

          if (neighborNode == newVirusNode) {
            graph.get(currentNode).remove(neighborNode);
            graph.get(neighborNode).remove(currentNode);
            linkToDestroy[0] = currentNode;
            linkToDestroy[1] = neighborNode;
            if (linkToDestroy[0] <= linkToDestroy[1]) {
              System.out.println(
                  "DESTROYED LINK: " + linkToDestroy[0] + "-" + linkToDestroy[1]); // add blank line for readability
            } else {
              System.out.println(
                  "DESTROYED LINK: " + linkToDestroy[1] + "-" + linkToDestroy[0]); // add blank line for readability
            }
            return;
          }
        }
      }
    }
    System.out.println("NO LINKS TO DESTROY FOUND!");
  }

  private static boolean checkIfVirusCanReachExit(int currentVirusNode) {
    if (currentVirusNode == -1) {
      return true;
    }
    boolean canReachExit = false;
    Queue<Integer> queue = new LinkedList<>();
    Set<Integer> visited = new HashSet<>();
    queue.add(currentVirusNode);
    while (!queue.isEmpty()) {
      int currentNode = queue.poll();
      for (int neighborNode : graph.get(currentNode)) {
        if (!visited.contains(neighborNode)) {
          queue.add(neighborNode);
          visited.add(neighborNode);
          if (exits.contains(neighborNode)) {
            canReachExit = true;
            return canReachExit;
          }
        }
      }
    }
    return canReachExit;
  }

  private static boolean tryAgain() {
    System.out.println("TRY AGAIN? (Y/N). 'Y' TO TRY AGAIN, 'N' TO EXIT.");
    while (true) {
      String answer = in.next();
      if (answer.equalsIgnoreCase("Y")) {
        return true;
      } else if (answer.equalsIgnoreCase("N")) {
        return false;
      } else {
        System.out.println("INVALID ANSWER, ENTER 'Y' TO TRY AGAIN OR 'N' TO EXIT.");
      }
    }
  }
}
