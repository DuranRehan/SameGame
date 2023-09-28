package g56055.atlg.samegame.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Board class represent the game board and implement all logic behind the game
 *
 * @author g56055
 */
public class Board {

    private final Tile[][] tiles;
    private final int row;
    private final int column;
    private final int numberColor;
    private final List<Position> listPos;

    private int score;
    private int remainingTiles;
    static boolean[][] checked;

    /**
     * Initialize a default board
     *
     * @param row         number of row on the board
     * @param column      number of column on the board
     * @param numberColor number of color on the board
     */
    public Board(int row, int column, int numberColor) {
        this.row = row;
        this.column = column;
        this.numberColor = numberColor;
        score = 0;
        remainingTiles = 0;
        tiles = new Tile[row][column];
        listPos = new ArrayList<>();
    }

    /**
     * Put a tile a specific tile in given position
     *
     * @param tile tile that will be placed
     * @param pos  position where the tile will be put
     */
    void putTile(Tile tile, Position pos) {
        if (!isInsideBoard(pos)) {
            throw new IllegalArgumentException("Out of Board");
        }
        tiles[pos.getX()][pos.getY()] = tile;
    }

    /**
     * Fill board with random color tile
     */
    void fillBoard() {
        int ln = 0;
        for (Tile[] rows : tiles) {
            int col = 0;
            for (Tile cols : rows) {
                putTile(new Tile(randomColor()), new Position(ln, col));
                remainingTiles++;
                col++;
            }
            ln++;
        }
    }

    /**
     * Gets a random number between 1 and number of color of the game, the random number represent a color
     *
     * @return a random number between 1 and number of color of the game
     */
    private int randomColor() {
        return 1 + (int) (Math.random() * numberColor);
    }

    /**
     * Check if the position is inside the board
     *
     * @param pos position to check
     * @return true if the position is inside the board, false otherwise
     */
    boolean isInsideBoard(Position pos) {
        return 0 <= pos.getX() && 0 <= pos.getY() && pos.getX() < row && pos.getY() < column;
    }

    /**
     * Gets all tiles position in the same area with the same color and their count
     *
     * @param pos position of the tiles
     * @return the count of tile with the same color and in the same area
     */
    int getAreaTiles(Position pos) {
        listPos.clear();
        int countTile = 0;
        int x = pos.getX();
        int y = pos.getY();
        if (!isInsideBoard(pos) || tiles[x][y] == null) {
            return countTile;
        }
        Color color = tiles[x][y].getColor();
        if ((isInsideBoard(new Position(x - 1, y)) && tiles[x - 1][y] != null && tiles[x - 1][y].getColor() == color)
                || (isInsideBoard(new Position(x + 1, y)) && tiles[x + 1][y] != null && tiles[x + 1][y].getColor() == color)
                || (isInsideBoard(new Position(x, y - 1)) && tiles[x][y - 1] != null && tiles[x][y - 1].getColor() == color)
                || (isInsideBoard(new Position(x, y + 1)) && tiles[x][y + 1] != null && tiles[x][y + 1].getColor() == color)) {
            countTile = 1;
            listPos.add(pos);
            checked = new boolean[row][column];
            checked[x][y] = true;
            countTile += sameAreaBlock(new Position(x - 1, y), color);
            countTile += sameAreaBlock(new Position(x + 1, y), color);
            countTile += sameAreaBlock(new Position(x, y - 1), color);
            countTile += sameAreaBlock(new Position(x, y + 1), color);
        }
        return countTile;
    }

    private int sameAreaBlock(Position pos, Color color) {
        int x = pos.getX();
        int y = pos.getY();
        if (!isInsideBoard(pos)
                || tiles[x][y] == null
                || tiles[x][y].getColor() != color
                || checked[x][y]) {
            return 0;
        }
        int count = 1;
        listPos.add(pos);
        checked[x][y] = true;
        count += sameAreaBlock(new Position(x - 1, y), color);
        count += sameAreaBlock(new Position(x + 1, y), color);
        count += sameAreaBlock(new Position(x, y - 1), color);
        count += sameAreaBlock(new Position(x, y + 1), color);
        return count;
    }

    /**
     * Check if a tile in given position can be removed of the board
     *
     * @param pos position of the tile to check
     * @return true if the tile can be removed, false otherwise
     */
    boolean canBeRemoved(Position pos) {
        if (!isInsideBoard(pos) || tiles[pos.getX()][pos.getY()] == null) {
            return false;
        }
        return getAreaTiles(pos) >= 2;
    }

    /**
     * Remove the tile in given position
     *
     * @param pos position of the tile
     */
    void removeTile(Position pos) {
        tiles[pos.getX()][pos.getY()] = null;
    }

    /**
     * Remove all tiles that have the same color as the tile in the given position
     *
     * @param pos position of the tile
     */
    void removesTilesArea(Position pos) {
        if (!isInsideBoard(pos)) {
            throw new IllegalArgumentException("Out of board");
        }
        int deletedTile = getAreaTiles(pos);
        calculateScore(deletedTile);
        remainingTiles -= deletedTile;
        for (Position p : listPos) {
            removeTile(p);
        }

    }

    /**
     * Check if the board is empty
     *
     * @return true if the board is empty, false otherwise
     */
    Boolean checkIsEmpty() {
        for (Tile[] lg : tiles) {
            for (Tile col : lg) {
                if (col != null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * checks if there are at least 2 tiles of the same color left on the board
     *
     * @return true if there are at least 2 tiles with the same color, false otherwise
     */
    boolean isAnotherPlay() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (tiles[i][j] != null && getAreaTiles(new Position(i, j)) > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Gets the score of the game
     *
     * @return score of the game
     */
    int getScore() {
        return score;
    }

    /**
     * Sets the score of the game
     *
     * @param score score that will be set
     */
    void setScore(int score) {
        this.score = score;
    }

    /**
     * Calculates the scored score
     *
     * @param n number of tiles removed
     */
    private void calculateScore(int n) {
        score += n * (n - 1);
    }

    /**
     * Gets the copy of the board0
     *
     * @return copy of the board
     */
    Tile[][] getTiles() {
        return Arrays.copyOf(tiles, tiles.length);
    }

    /**
     * Get all the positions of the tiles that have the same color as the tile in the given position
     *
     * @param pos position of the tile
     * @return all position of the neighbors tile with the same color
     */
    List<Position> getTilesNeighbors(Position pos) {
        getAreaTiles(pos);
        return List.copyOf(listPos);
    }

    /**
     * Gets the count of remaining tiles in the current game
     *
     * @return count of remaining tiles
     */
    int getRemainingTile() {
        return remainingTiles;
    }

    /**
     * Sets the remaining count of tiles
     *
     * @param remaining number of remaining tiles that will be set
     */
    void setRemaining(int remaining) {
        remainingTiles = remaining;
    }

    /**
     * Rearrange the board
     */
    void refactorBoard() {
        refactorRow();
        refactorColumn();
    }

    /**
     * Rearrange the row of the board
     */
    private void refactorRow() {
        for (int col = 0; col < column; col++) {
            int emptyRow = row - 1;
            int nextTile = emptyRow;
            while (nextTile >= 0 && emptyRow >= 0) {
                while (emptyRow >= 0 && tiles[emptyRow][col] != null) {
                    emptyRow--;
                }
                if (emptyRow >= 0) {
                    nextTile = emptyRow - 1;
                    while (nextTile >= 0
                            && tiles[nextTile][col] == null) {
                        nextTile--;
                    }
                    if (nextTile >= 0) {
                        tiles[emptyRow][col]
                                = tiles[nextTile][col];
                        tiles[nextTile][col] = null;
                    }
                }
            }
        }
    }

    /**
     * Rearrange the column of the board
     */
    private void refactorColumn() {
        int emptyCol = 0;
        int nextTile = emptyCol;
        while (emptyCol < column && nextTile < column) {
            while (emptyCol < column && tiles[row - 1][emptyCol] != null) {
                emptyCol++;
            }
            if (emptyCol < column) {
                nextTile = emptyCol + 1;
                while (nextTile < column && tiles[row - 1][nextTile] == null) {
                    nextTile++;
                }
                if (nextTile < column) {
                    for (int ln = 0; ln < row; ln++) {
                        tiles[ln][emptyCol] = tiles[ln][nextTile];
                        tiles[ln][nextTile] = null;
                    }
                }
            }
        }
    }
}
