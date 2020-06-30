package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Board
 */
public class Board implements Serializable {

    private Cell[][] board = new Cell[5][5];

    /**
     * Get board cell [ ] [ ].
     *
     * @return the cell [ ] [ ]
     */
    public Cell[][] getBoard() {
        return board;
    }

    /**
     * Sets board.
     *
     * @param board the board
     */
    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    /**
     * Gets cell.
     *
     * @param x the x
     * @param y the y
     * @return the cell
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
     * Copy values in new board board.
     *
     * @return the board
     */
    public final Board CopyValuesInNewBoard() {
        final Board result = new Board();
        for (int i = 0; i < 5; i++) {
            result.board[i] = board[i].clone();
        }
        return result;
    }

    /**
     * Copy values in new simple board int [ ] [ ].
     *
     * @return the int [ ] [ ]
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
