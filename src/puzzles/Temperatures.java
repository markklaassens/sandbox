package puzzles;

import java.util.Scanner;

/**
 * Puzzle - Temperatures.
 * <a href="https://www.codingame.com/training/easy/temperatures">Link to puzzle on CodinGame.</a>
 *
 * <p>The goal is to find the temperature closest to 0 in a given array. First integer defines the amount of temperatures
 * to analyze. The following integers are temperatures. The program returns the temperature closest to 0.</p>
 */
public class Temperatures {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt(); // the number of temperatures to analyse
    int knownClosestToZero = Integer.MAX_VALUE;

    if (n > 0) {
      for (int i = 0; i < n; i++) {
        int temperature = in.nextInt();
        if (Math.abs(temperature) < Math.abs(knownClosestToZero)) {
          knownClosestToZero = temperature;
        } else if (Math.abs(temperature) == Math.abs(knownClosestToZero) && temperature > knownClosestToZero) {
          knownClosestToZero = temperature;
        }
      }
      System.out.println(knownClosestToZero);
    } else {
      System.out.println(0);
    }
    in.close();
  }
}
