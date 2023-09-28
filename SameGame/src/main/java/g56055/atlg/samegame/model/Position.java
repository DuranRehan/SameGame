package g56055.atlg.samegame.model;

/**
 * Determine a precise position on the game board
 *
 * @author g56055
 */
public class Position {

    private int x;
    private int y;

    /**
     * Define a position on the game board
     *
     * @param x the row of the game board
     * @param y the column of the game board
     */

    public Position(int x, int y) {

        this.x = x;
        this.y = y;
    }

    /**
     * Gets the y positions on the board
     *
     * @return y positions
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the x positions on the board
     *
     * @return x positions
     */
    public int getX() {
        return x;
    }

}
