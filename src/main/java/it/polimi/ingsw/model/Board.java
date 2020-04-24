package it.polimi.ingsw.model;

public class Board
{
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

    protected final Board Clone() {
        final Board result = new Board();
        for(int i = 0; i < 5; i++){
            result.board[i] = board[i].clone();
        }
        return result;
    }
}
