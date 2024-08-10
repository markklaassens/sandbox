package puzzles;

import java.util.Scanner;

/**
 * Puzzle - The Descent.
 * <a href="https://www.codingame.com/training/easy/the-descent">Link to puzzle on CodinGame.</a>
 *
 * <p>This is a slight modification for the puzzle mentioned above. The goal is to find the highest number from an array
 * and print the corresponding index.</p>
 *
 * <p>First, the program takes in the number of passes it has do over mountain ranges during the descent of the plane. Each
 * mountain range consists of 8 mountains, and for each pass, you need to specify the heights of these 8 mountains. The
 * program then returns the index of the highest mountain in each pass.</p>
 */
public class TheDescent {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int passes = in.nextInt();

    // game loop
    while (passes > 0) {
      int[] mountainRange = new int[8];
      int max = 0;
      int maxIndex = 0;

      for (int i = 0; i < 8; i++) {
        mountainRange[i] = in.nextInt();
      }

      for (int j = 0; j < mountainRange.length; j++) {
        if (mountainRange[j] > max) {
          max = mountainRange[j];
          maxIndex = j;
        }
      }
      System.out.println(maxIndex);
      --passes;
    }
    in.close();
  }
}
