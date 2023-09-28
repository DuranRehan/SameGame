package g56055.atlg.samegame.view;

import g56055.atlg.samegame.controller.ControllerFx;
import g56055.atlg.samegame.model.Game;
import g56055.atlg.samegame.model.Position;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Objects;

/**
 * BoardFx class represent the board of the game that will be displayed
 *
 * @author g56055
 */
public class BoardFx extends GridPane {
    
    private TileFx[][] boardFx;
    private Game game;

    /**
     * Initialized a default Board
     */
    public BoardFx() {
        this.getStyleClass().add("board");
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("background.png")));
        this.setBackground(new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(500, 500, false, false, false, false))));
    }

    /**
     * Display the board of the game with a fx view
     */
    void displayBoard() {
        this.getChildren().clear();
        boardFx = new TileFx[game.getBoard().length][game.getBoard()[0].length];
        for (int row = 0; row < game.getBoard().length; row++) {
            for (int column = 0; column < game.getBoard()[row].length; column++) {
                if (game.getBoard()[row][column] != null) {
                    char color = game.getBoard()[row][column].getColor().getLetter();
                    TileFx tile;
                    switch (color) {
                        case 'R' ->
                            tile = new TileFx(Color.RED);
                        case 'G' ->
                            tile = new TileFx(Color.GREEN);
                        case 'B' ->
                            tile = new TileFx(Color.BLUE);
                        case 'Y' ->
                            tile = new TileFx(Color.YELLOW);
                        case 'P' ->
                            tile = new TileFx(Color.PINK);
                        default ->
                            tile = new TileFx(Color.TRANSPARENT);
                    }
                    boardFx[row][column] = tile;
                    boardFx[row][column].setPosition(new Position(row, column));
                    this.add(tile, column, row);
                    tile.getStyleClass().add("tileFx");
                }
            }
        }
    }

    /**
     * Gets the game of the controller
     *
     * @param controller controller who is used
     */
    void getGame(ControllerFx controller) {
        this.game = controller.getGame();
    }

    /**
     * Gets a copy of the boardFx array
     *
     * @return copy of the boardFx
     */
    TileFx[][] getBoardFx() {
        if (this.boardFx == null) {
            return null;
        }
        return Arrays.copyOf(boardFx, boardFx.length);
    }

    /**
     * Reset the board of the game
     */
    void resetBoard() {
        this.getChildren().clear();
    }
}
