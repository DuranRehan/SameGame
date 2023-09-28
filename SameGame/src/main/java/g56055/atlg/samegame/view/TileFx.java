package g56055.atlg.samegame.view;

import g56055.atlg.samegame.model.Position;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * TileFx is the representation graphic of a tile on the board
 *
 * @author g56055
 */
public class TileFx extends Rectangle {
    private Position position;

    /**
     * Define a TileFx with a defined color
     *
     * @param color color of the tileFx
     */
    public TileFx(Color color) {
        super(0, 0, color);
        double SQUARE_SIZE = 25;
        this.setWidth(SQUARE_SIZE);
        this.setHeight(SQUARE_SIZE);
    }

    /**
     * Set the position of the tileFx
     *
     * @param position position where the tileFx will be set
     */
    void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gets the position of the TileFx
     *
     * @return the position of the tileFx
     */
    Position getPosition() {
        return position;
    }
}
