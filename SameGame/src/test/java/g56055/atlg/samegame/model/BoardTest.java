package g56055.atlg.samegame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit test for Board class
 *
 * @author g56055
 */
public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(10, 10, 5);
    }

    @Test
    public void testPutTile() {
        setUp();
        Tile tile = new Tile(2);
        Position position = new Position(5, 5);
        board.putTile(tile, position);
        assertSame(board.getTiles()[position.getX()][position.getY()], tile);
    }

    @Test
    public void testPutTileError() {
        setUp();
        Tile tile = new Tile(2);
        Position position = new Position(15, 15);
        assertThrows(IllegalArgumentException.class, () -> board.putTile(tile, position));
    }

    @Test
    public void testCheckIsEmpty() {
        setUp();
        assertTrue(board.checkIsEmpty());
    }

    @Test
    public void testCheckIs_not_Empty() {
        setUp();
        board.fillBoard();
        assertFalse(board.checkIsEmpty());
    }

    @Test
    public void testIsInsideBoard() {
        setUp();
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(9, 9);
        Position pos3 = new Position(0, 9);
        Position pos4 = new Position(9, 0);
        Position pos5 = new Position(5, 5);
        assertTrue(
                board.isInsideBoard(pos1)
                        && board.isInsideBoard(pos2)
                        && board.isInsideBoard(pos3)
                        && board.isInsideBoard(pos4)
                        && board.isInsideBoard(pos5));
    }

    @Test
    public void testIs_not_InsideBoard() {
        setUp();
        Position pos1 = new Position(-1, -1);
        Position pos2 = new Position(10, 10);
        Position pos3 = new Position(-1, 9);
        Position pos4 = new Position(-1, 0);
        Position pos5 = new Position(12, 15);
        assertFalse(
                board.isInsideBoard(pos1)
                        && board.isInsideBoard(pos2)
                        && board.isInsideBoard(pos3)
                        && board.isInsideBoard(pos4)
                        && board.isInsideBoard(pos5));
    }

    @Test
    public void testGetAreaTiles() {
        setUp();
        createArtificialArea(board);
        assertTrue(board.getAreaTiles(new Position(0, 1)) == 4
                && board.getAreaTiles(new Position(0, 0)) == 2
                && board.getAreaTiles(new Position(9, 0)) == 7
        );
    }

    @Test
    public void testCanBeRemoved_ok() {
        setUp();
        createArtificialArea(board);
        assertTrue(board.canBeRemoved(new Position(0, 1))
                && board.canBeRemoved(new Position(0, 0))
                && board.canBeRemoved(new Position(9, 0))
        );
    }

    @Test
    public void testCanBeRemoved_not_ok() {
        setUp();
        //Area1 : One single block
        board.putTile(new Tile(1), new Position(0, 0));
        assertFalse(board.canBeRemoved(new Position(0, 0)));
    }

    @Test
    public void testRemoveTile() {
        setUp();
        createArtificialArea(board);
        board.removeTile(new Position(0, 3));
        assertNull(board.getTiles()[0][3]);
    }

    @Test
    public void testRemovesTilesArea() {
        setUp();
        createArtificialArea(board);
        board.removesTilesArea(new Position(0, 0));
        board.removesTilesArea(new Position(0, 1));
        board.removesTilesArea(new Position(9, 0));
        assertTrue(board.getAreaTiles(new Position(0, 1)) == 0
                && board.getAreaTiles(new Position(0, 0)) == 0
                && board.getAreaTiles(new Position(9, 0)) == 0
        );
    }

    @Test
    public void testRemovesTilesAreaThrowsException() {
        setUp();
        assertThrows(IllegalArgumentException.class, () -> board.removesTilesArea(new Position(15, 15)));
    }

    @Test
    public void testIsAnotherPlay_ok() {
        setUp();
        createArtificialArea(board);
        board.removesTilesArea(new Position(0, 0));
        assertTrue(board.isAnotherPlay());
    }

    @Test
    public void testIsAnotherPlay_not_ok() {
        setUp();
        createArtificialArea(board);
        board.removesTilesArea(new Position(0, 0));
        board.removesTilesArea(new Position(0, 1));
        board.removesTilesArea(new Position(9, 0));
        assertFalse(board.isAnotherPlay());
    }


    @Test
    public void testRefactorBoard_Column() {
        setUp();
        //Area1: the tiles are aligned on the column
        board.putTile(new Tile(3), new Position(9, 6));
        board.putTile(new Tile(3), new Position(9, 7));
        board.putTile(new Tile(3), new Position(9, 8));
        board.putTile(new Tile(3), new Position(9, 9));
        board.refactorBoard();
        assertNotNull(board.getTiles()[9][0]);
    }

    @Test
    public void testRefactorBoard_Row() {
        setUp();
        //Area1: the tiles are aligned on the Row
        board.putTile(new Tile(3), new Position(0, 0));
        board.putTile(new Tile(3), new Position(1, 0));
        board.putTile(new Tile(3), new Position(2, 0));
        board.putTile(new Tile(3), new Position(3, 0));
        board.putTile(new Tile(3), new Position(9, 0));
        board.refactorBoard();
        assertNotNull(board.getTiles()[9][0]);
    }

    private void createArtificialArea(Board board) {
        //Area1: 4 Tiles
        board.putTile(new Tile(1), new Position(0, 1));
        board.putTile(new Tile(1), new Position(0, 2));
        board.putTile(new Tile(1), new Position(0, 3));
        board.putTile(new Tile(1), new Position(0, 4));
        //Area2: 2 Tiles
        board.putTile(new Tile(2), new Position(0, 0));
        board.putTile(new Tile(2), new Position(1, 0));
        //Area3: 7 Tiles
        board.putTile(new Tile(3), new Position(9, 0));
        board.putTile(new Tile(3), new Position(9, 1));
        board.putTile(new Tile(3), new Position(9, 2));
        board.putTile(new Tile(3), new Position(9, 3));
        board.putTile(new Tile(3), new Position(9, 4));
        board.putTile(new Tile(3), new Position(9, 5));
        board.putTile(new Tile(3), new Position(9, 6));
        //Area 4: Single Tile
        board.putTile(new Tile(4), new Position(5, 5));
    }
}