package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Board contains all the Cells of the game board
 */
public class Board implements Serializable {

    private Cell[][] board = new Cell[5][5];

    /**
     * Method getBoard returns the Board of this Board object.
     *
     * @return board (type Board) of this Board object.
     */
    public Cell[][] getBoard() {
        return board;
    }

    /**
     * Method setBoard sets the Board of this Board object.
     *
     * @param board the Board of this Board object.
     */
    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    /**
     * Method getCell returns the Cell in coordinate x coordinate y of this Board object.
     *
     * @param x of type int
     * @param y of type int
     * @return board[x][y] of this Board object
     */
    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    /**
     * Constructor Board creates a new Board instance.
     */
    public Board()
    {
        for (int x = 0; x < 5; x++)
        {
            for (int y = 0; y <5; y++)
            {
                board[x][y] = new Cell(x, y);
            }
        }
    }

    /**
     * Method copyValuesInNewBoard copies the Board on a new Board instance.
     * @return result of type Board
     */
    public final Board CopyValuesInNewBoard() {
        final Board result = new Board();
        for (int i = 0; i < 5; i++) {
            result.board[i] = board[i].clone();
        }
        return result;
    }

    /**
     * Method copyValuesInNewSimpleBoard copies the Board on a new SimpleBoard instance.
     * @return result of type SimpleBoard
     */
    public int[][] CopyValuesInNewSimpleBoard() {
        int[][] result = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j].getDome())
                    result[i][j] = 4;
                else
                    result[i][j] = board[i][j].getBuilding();
            }
        }
        return result;
    }
}
