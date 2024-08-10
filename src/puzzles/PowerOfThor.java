package puzzles;

import java.util.Scanner;

/**
 * Puzzle - Power of Thor.
 * <a href="https://www.codingame.com/training/easy/power-of-thor-episode-1">Link to related puzzle on CodinGame.</a>
 *
 * <p>This is a slight modification of the puzzle mentioned above. The goal is to let Thor move to the position
 * of the light within the given turns. Both Thor and the light are placed on a grid where (0, 0) is the northwest corner.
 * Thor moves in concrete steps of 1, and is able to move horizontal, vertical and diagonal and the grid.</p>
 *
 * <p>This program starts by taking in 5 integers. First x position of the light, second y position of the light. x=0, y=0
 * is most NW point. The third and fourth integers represent Thor's starting x and y positions. Finally the fifth represents
 * the amount of turns Thor has to get to the light. The code calculates if Thor can reach the light within the
 * given turns.</p>
 */
public class PowerOfThor {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    int lightX = in.nextInt(); // the X position of the light of power
    int lightY = in.nextInt(); // the Y position of the light of power
    int initialTx = in.nextInt(); // Thor's starting X position
    int initialTy = in.nextInt(); // Thor's starting Y position
    int positionX = initialTx;
    int positionY = initialTy;
    int remainingTurns = in.nextInt();

    while (true) {
      if (lightX < positionX && lightY < positionY) {
        System.out.println("NW");
        positionX = positionX - 1;
        positionY = positionY - 1;
      } else if (lightX > positionX && lightY < positionY) {
        System.out.println("NE");
        positionX = positionX + 1;
        positionY = positionY - 1;
      } else if (lightX < positionX && lightY > positionY) {
        System.out.println("SW");
        positionX = positionX - 1;
        positionY = positionY + 1;
      } else if (lightX > positionX && lightY > positionY) {
        System.out.println("SE");
        positionX = positionX + 1;
        positionY = positionY + 1;
      } else if (lightX == positionX && lightY < positionY) {
        System.out.println("N");
        positionY = positionY - 1;
      } else if (lightX == positionX) {
        System.out.println("S");
        positionY = positionY + 1;
      } else if (lightX < positionX) {
        System.out.println("W");
        positionX = positionX - 1;
      } else {
        System.out.println("E");
        positionX = positionX + 1;
      }
      if (positionX == lightX && positionY == lightY) {
        System.out.println("SUCCESS, THE POWER OF THE THOR IS ALMIGHTY!");
        break;
      }
      --remainingTurns;
      if (remainingTurns == 0) {
        System.out.println("TOO FAR AWAY, THOR NEEDS A BEER!");
        break;
      }
    }
  }
}
