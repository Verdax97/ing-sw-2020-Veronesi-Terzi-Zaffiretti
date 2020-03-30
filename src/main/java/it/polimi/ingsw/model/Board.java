package it.polimi.ingsw.model;

public class Board implements Cloneable
{
    private Cell[][] board = new Cell[5][5];

    public Cell[][] GetBoard() {
        return board;
    }

    public void SetBoard(Cell[][] board) {
        this.board = board;
    }

    public Cell GetCell(int x, int y)
    {
        return board[x][y];
    }

    @Override
    protected final Board clone() {
        final Board result = new Board();
        for(int i = 0; i < 5; i++){
            result.board[i] = board[i].clone();
        }
        return result;
    }
}
