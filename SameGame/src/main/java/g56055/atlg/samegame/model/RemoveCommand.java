package g56055.atlg.samegame.model;

import java.util.Arrays;

/**
 * Command class to remove a tile to the board
 *
 * @author g56055
 */
public class RemoveCommand implements Command {

    private final Position position;
    private final Game game;
    private Tile[][] savedBoard;
    private int savedScore;
    private int saveRemainingTile;

    /**
     * Define the command of removing a tile of the board
     *
     * @param pos  Position of the tile that will be removed
     * @param game The game or tile must be removed
     */
    public RemoveCommand(Position pos, Game game) {
        this.position = pos;
        this.game = game;
    }

    @Override
    public void execute() {
        saveRemainingTile = game.getRemainingTile();
        savedBoard = deepCopy(game.getBoard());
        savedScore = game.getScore();
        game.playRound(position);
        game.refactorBoard();
        game.notifyObserver("update");
    }

    @Override
    public void unexecute() {
        game.setScore(savedScore);
        game.setRemaining(saveRemainingTile);
        undoBoard();
        game.notifyObserver("update");
    }

    /**
     * Make a deep copy of an 2D tile array
     *
     * @param original the array that will be copied
     * @return the copy of the original tiles array
     */
    private Tile[][] deepCopy(Tile[][] original) {
        if (original == null) {
            return null;
        }
        Tile[][] result = new Tile[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

    /**
     * Replace the actual board with the saved board
     */
    private void undoBoard() {
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                game.insertTile(savedBoard[i][j], new Position(i, j));
            }
        }
    }
}
