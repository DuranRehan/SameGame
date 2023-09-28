package g56055.atlg.samegame.model;

import static g56055.atlg.samegame.model.Color.*;

/**
 * Defines the characteristics of a tile in SameGame
 *
 * @author g56055
 */
public class Tile {

    private final Color color;

    /**
     * Define a tile with a specific color
     *
     * @param colors the color of the created tile
     */
    public Tile(int colors) {
        switch (colors) {
            case 1 -> this.color = RED;
            case 2 -> this.color = BLUE;
            case 3 -> this.color = GREEN;
            case 4 -> this.color = YELLOW;
            case 5 -> this.color = PINK;
            default -> this.color = null;
        }
    }

    /**
     * Gets color of the tile
     *
     * @return color of the tile
     */
    public Color getColor() {
        return color;
    }

}
