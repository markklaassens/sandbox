package puzzles;

import java.util.Scanner;

/**
 * Puzzle - Stock Exchange Losses.
 * <a href="https://www.codingame.com/training/medium/stock-exchange-losses">Link to puzzle on CodinGame.</a>
 *
 * <p>The goal is to find the biggest possible loss for a stock given the values in a chronological order. First integer
 * defines the amount of the stock values to analyze. The following integers are stock values. The program calculates the
 * biggest possible loss that could be made and takes in the chronological order of the values.</p>
 */
class StockExchangeLosses {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int n = in.nextInt(); // the number of stocks to analyse
    int biggestNegativeDifference = 0;
    int[] values = new int[n];

    for (int i = 0; i < n; i++) {
      int v = in.nextInt();
      values[i] = v;
    }
    int maxValue = values[0]; // the first value is the initial maximum

    for (int i = 0; i < n; i++) {
      if (values[i] - maxValue < biggestNegativeDifference) {
        biggestNegativeDifference = values[i] - maxValue;
      }
      if (values[i] > maxValue) {
        maxValue = values[i];
      }
    }
    System.out.println(biggestNegativeDifference);
    in.close();
  }
}
