package it.polimi.ingsw.model;

import java.io.Serializable;

public class Board implements Serializable {
    private Cell[][] board = new Cell[5][5];

    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public Cell getCell(int x, int y) {
        return board[x][y];
    }

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

    public final Board CopyValuesInNewBoard() {
        final Board result = new Board();
        for (int i = 0; i < 5; i++) {
            result.board[i] = board[i].clone();
        }
        return result;
    }

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
