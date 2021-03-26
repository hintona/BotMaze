//For Object-Oriented Development class, Fall 2020
package BotMaze;
import java.util.Scanner;


public class BotMaze {

    public static void main(String[] args) {
        System.out.println("Enter the preferred maze size: ");
        Scanner scr = new Scanner(System.in);
        int d = scr.nextInt();
        scr.close();
        if (d >= 4 && d <= 10) {

            MazeBoard mzb = new MazeBoard(d);
            mzb.populate();

            mzb.printMazeBoard();
            mzb.primsAlgorithm(0, 0);
            mzb.printMazeBoard();
            BobBot bob = new BobBot(mzb, d);
            bob.move();
        }
        else System.out.println("You did not enter a valid size. Please pick a number between 4 and 10.");

    }
}
