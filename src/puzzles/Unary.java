package puzzles;

import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Puzzle - Unary.
 * <a href="https://www.codingame.com/training/easy/unary">Link to puzzle on CodinGame.</a>
 *
 * <p>Encodes a String using it's binary value into a code, in which '1' is represented as '0 ' and '0' as '00 '.
 * Followed by a number of '0's corresponding to the count of consecutive '1's or '0's. For example C binary is 1000011
 * and encodes as: '0 0 00 0000 0 00'. The binary string is derived from the 7-bit ASCII representation of characters, as
 * all ASCII characters can be defined within 7 bits.</p>
 */
class Unary {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String message = in.nextLine();
    StringBuilder answer = new StringBuilder();
    String binaryString = message.chars().mapToObj(o -> String.format("%7s", Integer.toBinaryString(o))
        .replace(' ', '0')).collect(Collectors.joining());

    for (int j = 0; j < binaryString.length(); j++) {
      if (j > 0 && binaryString.charAt(j) == binaryString.charAt(j - 1)) {
        answer.append("0");
      } else if (binaryString.charAt(j) == '0') {
        answer.append(" 00 0");
      } else if (binaryString.charAt(j) == '1') {
        answer.append(" 0 0");
      }
    }
    System.out.println(answer.toString().trim());
    in.close();
  }
}
