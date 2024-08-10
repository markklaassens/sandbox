package puzzles;

import java.util.Scanner;

/**
 * Puzzle - Shadows of the Knight - Episode 1.
 * <a href="https://www.codingame.com/training/medium/shadows-of-the-knight-episode-1">
 * Link to related puzzle on CodinGame.</a>
 *
 * <p>This is a slight modification for the puzzle mentioned above. The goal is to find a specific position on a grid, but
 * the knight only receives the direction and not how far away the specified position. It utilizes the binary search
 * algorithm.</p>
 *
 * <p>This program starts by taking in 5 integers. The first integer defines the width of a grid, and the second defines the
 * height of the grid. The third integer specifies the number of turns the knight has to find a specific position on the
 * grid. The fourth and fifth integers define the starting x and y positions of the knight, respectively. Next, choose a
 * target position on the grid to find, for example, x83, y86 on a 100 by 100 grid. From the starting position, input the
 * direction the knight needs to move. For instance, if the knight is at x20, y20 and needs to move right and down, input
 * 'RD'. The knight will use a binary search algorithm to reach your target position as quickly as possible, based only on
 * the given directions.</p>
 */
public class ShadowsOfTheKnightE1 {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int W = in.nextInt(); // width of the building.
    int H = in.nextInt(); // height of the building.
    int N = in.nextInt(); // maximum number of turns before game over.
    int X0 = in.nextInt();
    int Y0 = in.nextInt();
    int knownMinY = 0;
    int knownMaxY = H;
    int knownMinX = 0;
    int knownMaxX = W;

    // game loop
    while (N > 0) {
      String bombDir = in.next(); // the direction of the bombs from batman's current location (R, RD, D, LD, L, LU, U or RU)

      if (bombDir.contains("U")) {
        knownMaxY = Y0;
        Y0 = (knownMinY + knownMaxY) / 2;
      } else if (bombDir.contains("D")) {
        knownMinY = Y0;
        Y0 = (knownMinY + knownMaxY) / 2;
      }

      if (bombDir.contains("L")) {
        knownMaxX = X0;
        X0 = (knownMinX + knownMaxX) / 2;
      } else if (bombDir.contains("R")) {
        knownMinX = X0;
        X0 = (knownMinX + knownMaxX) / 2;
      }
      System.out.println(X0 + " " + Y0);

      --N;
    }
  }
}
