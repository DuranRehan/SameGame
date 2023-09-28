package g56055.atlg.samegame.view;

import g56055.atlg.samegame.model.Game;
import g56055.atlg.samegame.model.Tile;

import java.util.Scanner;

/**
 * Define the console view of SameGame
 *
 * @author g56055
 */
public class View {

    private final Scanner sc = new Scanner(System.in);
    private final Game game;

    /**
     * Define a console view
     *
     * @param game the game to display
     */
    public View(Game game) {
        this.game = game;
    }

    /**
     * Display a welcome message
     */
    public void displayWelcome() {
        System.out.println("*----------------------*");
        System.out.println("|  Duran Rehan 56055   |");
        System.out.println("|  Project : SameGame  |");
        System.out.println("*----------------------*");
    }

    /**
     * Ask user to enter the desired configuration
     *
     * @return array with the desired configuration
     */
    public String[] askConfiguration() {
        System.out.print("Enter the number of rows & columns and level "
                + "[Like : 10 10 2]: ");
        sc.useDelimiter("\n");
        String pattern = "(\\d{1,2}\\s){2}\\d{1,2}";
        while (!sc.hasNext(pattern)) {
            System.out.println("Enter valid configuration! Retry : ");
            sc.next();
        }
        return sc.next().split(" ");
    }

    /**
     * Ask user to enter the desired position
     *
     * @return the desired position
     */
    public String[] askPosition() {
        sc.useDelimiter("\n");
        String pattern = "(redo|undo)" + "|rm (\\d{1,2}\\s)\\d{1,2}";
        System.out.println("Enter Position (rm [row][col]) : ");
        while (!sc.hasNext(pattern)) {
            System.out.println("Enter valid position or command ! Retry : ");
            sc.next();
        }
        return sc.next().split(" ");
    }

    /**
     * Print the game board
     */
    public void printBoard() {
        for (Tile[] ln : game.getBoard()) {
            for (Tile col : ln) {
                if (col != null) {
                    System.out.printf("%1s", " " + col.getColor().getLetter());
                } else {
                    System.out.printf("%2s", " ");
                }
            }
            System.out.print("\n");
        }
        System.out.println("\n");
    }

    /**
     * Display the actual score of the game
     */
    public void displayScore() {
        System.out.println("Score : " + game.getScore());
    }

    /**
     * Show an error message
     */
    public void showError() {
        System.out.println("Error : this tile cannot be removed ! Retry.");
    }

    /**
     * Display the end message
     */
    public void displayEndMessage() {
        System.out.println("*----------------------*");
        System.out.println("|      Final Score     |");
        System.out.printf("| %11d %9c|\n", game.getScore(), ' ');
        System.out.println("*----------------------*");
    }
}
