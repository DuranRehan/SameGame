package g56055.atlg.samegame.model;

import g56055.atlg.samegame.view.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Game class represent the facade of the Model and Gather the necessary elements for the game
 *
 * @author g56055
 */
public class Game implements Observable {

    private Board board;
    private List<Observer> observers;

    /**
     * Initialize a default Model
     */
    public Game() {
        this.observers = new ArrayList<>();
    }

    /**
     * Ask to board to create a game with define size and number of color
     * the board will be also filled with tile
     *
     * @param row         number of row the board will be contained
     * @param col         number of column the board will be contained
     * @param numberColor number of color the board will be contained
     */
    public void startGame(int row, int col, int numberColor) {
        board = new Board(row, col, numberColor);
        board.fillBoard();
    }

    /**
     * Check if the game is over
     *
     * @return true if the game is over, false otherwise
     */
    public boolean checkIsOver() {
        if (board.checkIsEmpty() || !board.isAnotherPlay()) {
            notifyObserver("win");
            return true;
        }
        return false;
    }

    /**
     * Play one round of SameGame, the tile and the neighbors with the same color will be removed of the board
     *
     * @param pos position of the tile that will be removed
     */
    public void playRound(Position pos) {
        board.removesTilesArea(pos);
    }

    /**
     * Check if the tile with a certain position can be removed of the board
     *
     * @param pos position of the tile to check
     * @return true if the tile in the given position can be removed of the board
     */
    public boolean canBeRemoved(Position pos) {
        if (!board.canBeRemoved(pos)) {
            notifyObserver("removeError");
            return false;
        }
        return true;
    }

    /**
     * Gets the board of the game
     *
     * @return board of the game
     */
    public Tile[][] getBoard() {
        return board.getTiles();
    }

    /**
     * Insert a specific tile in a given position in the board
     *
     * @param tile tile that will be placed
     * @param pos  position where the tile will be placed
     */
    public void insertTile(Tile tile, Position pos) {
        board.putTile(tile, pos);
    }

    /**
     * Gets the score of the game
     *
     * @return score of the game
     */
    public int getScore() {
        return board.getScore();
    }

    /**
     * Sets the score of the game
     *
     * @param score score that will be set
     */
    public void setScore(int score) {
        board.setScore(score);
    }

    /**
     * Rearrange the board of the game
     */
    public void refactorBoard() {
        board.refactorBoard();
    }

    /**
     * Give up the game, set the board attribute to null
     */
    public void giveUpGame() {
        this.board = null;
        notifyObserver("giveUp");
    }

    /**
     * Gets the count of remaining tiles in the current game
     *
     * @return count of remaining tiles
     */
    public int getRemainingTile() {
        return board.getRemainingTile();
    }

    /**
     * Get all the positions of the tiles that have the same color as the tile in the given position
     *
     * @param pos position of the tile
     * @return all position of the neighbors tile with the same color
     */
    public List<Position> getTilesNeighbors(Position pos) {
        return board.getTilesNeighbors(pos);
    }

    /**
     * Sets the remaining count of tiles
     *
     * @param remaining number of remaining tiles that will be set
     */
    void setRemaining(int remaining) {
        board.setRemaining(remaining);
    }

    @Override
    public void subscribe(Observer v) {
        observers.add(v);
    }

    @Override
    public void unsubscribe(Observer v) {
        observers.remove(v);
    }

    @Override
    public void notifyObserver(String toUpdate) {
        for (Observer observer : observers) {
            observer.update(toUpdate);
        }
    }

}
