package g56055.atlg.samegame.controller;

import g56055.atlg.samegame.model.CommandManager;
import g56055.atlg.samegame.model.Game;
import g56055.atlg.samegame.model.Position;
import g56055.atlg.samegame.model.RemoveCommand;
import g56055.atlg.samegame.view.ViewFx;
import javafx.stage.Stage;

import java.util.List;

/**
 * Gathers and controls the different elements of the model and the Fx view.
 *
 * @author g56055
 */
public class ControllerFx {

    private final ViewFx view;
    private final Game game;
    private CommandManager cmdMgr;

    /**
     * Define a Fx Controller
     *
     * @param stage stage of the fx view
     */
    public ControllerFx(Stage stage) {
        this.game = new Game();
        this.view = new ViewFx(this, game);
        view.start(stage);
    }

    /**
     * Ask the model to start the game
     *
     * @param row    number of rows that the game will contain
     * @param col    number of columns that the game will contain
     * @param colors number of different color that the game will contain
     */
    public void startGame(int row, int col, int colors) {
        this.cmdMgr = new CommandManager();
        game.startGame(row, col, colors);
    }

    /**
     * Ask the model the actual score of the game
     *
     * @return actual score
     */
    public int getScore() {
        return game.getScore();
    }

    /**
     * Ask the model to reset the score of the game
     */
    public void resetScore() {
        game.setScore(0);
    }

    /**
     * Ask Command manager to un-execute the previous command
     */
    public void undo() {
        cmdMgr.undoCommand(game);
    }

    /**
     * Ask Command manager to execute the previous canceled command
     */
    public void redo() {
        cmdMgr.redoCommand(game);
    }

    /**
     * Ask model to play one round
     *
     * @param x position x of the tile that will be removed
     * @param y position y of the tile that will be removed
     */
    public void playRound(int x, int y) {
        if (game.canBeRemoved(new Position(x, y))) {
            cmdMgr.addCommand(new RemoveCommand(new Position(x, y), game));
        }
    }

    /**
     * Ask model to check if the game is finished
     */
    public void gameIsOver() {
        game.checkIsOver();
    }

    /**
     * Ask model to give up the current game
     */
    public void giveUpGame() {
        game.giveUpGame();
        this.cmdMgr = new CommandManager();
    }

    /**
     * Ask model to give the number of remaining tiles
     *
     * @return count of remaining tiles
     */
    public int getRemainingTile() {
        return game.getRemainingTile();
    }

    /**
     * Ask model to give all tiles of the same area
     *
     * @param pos position of the tile whose neighbors we want to find
     * @return list of all neighbors positions
     */
    public List<Position> getNeighbors(Position pos) {
        return game.getTilesNeighbors(pos);
    }

    /**
     * Gets game for giving access to model
     *
     * @return the game of controller
     */
    public Game getGame() {
        return game;
    }
}
