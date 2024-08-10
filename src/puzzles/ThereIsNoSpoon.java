package puzzles;

import java.util.Scanner;

/**
 * Puzzle - There Is No Spoon.
 * <a href="https://www.codingame.com/training/medium/there-is-no-spoon-episode-1">Link to related puzzle on CodinGame.</a>
 *
 * <p>This is slight modification for the puzzle mentioned above. The goal is to find all neighbors to the right and
 * below a node on a grid.</p>
 *
 * <p>Takes in 2 integers which define the width and height of a grid. Followed by lines of characters in which a '0'
 * represents a node. The amount of lines need to correspond with the height and the amount of characters per line need to
 * correspond to the width. The program searches for the 0's and closest 0 to the right of it and the closest 0 below it and
 * gives the coordinates of the node it self and the closest neighbours. If no neighbour is found it returns -1 -1.
 * On the grid 0 0 is the top left position.</p>
 */
public class ThereIsNoSpoon {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int width = in.nextInt(); // the number of cells on the X axis
    int height = in.nextInt(); // the number of cells on the Y axis
    in.nextLine(); // skip to next line to start reading the first row.
    char[][] grid = new char[height][width];

    for (int i = 0; i < height; i++) {
      String row = in.nextLine();
      char[] charRow = row.toCharArray();
      System.arraycopy(charRow, 0, grid[i], 0, width);
      System.err.println(grid[i]);
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (grid[i][j] == '0') {
          String node = j + " " + i + " ";
          String rightNode = -1 + " " + -1 + " ";
          String bottomNode = -1 + " " + -1;

          if (j < width - 1) {
            for (int h = j + 1; h < width; h++) {
              if (grid[i][h] == '0') {
                rightNode = h + " " + i + " ";
                break;
              }
            }
          }

          if (i < height - 1) {
            for (int v = i + 1; v < height; v++) {
              if (grid[v][j] == '0') {
                bottomNode = j + " " + v;
                break;
              }
            }
          }
          System.out.println(node + rightNode + bottomNode);
        }
      }
    }
    in.close();
  }
}
