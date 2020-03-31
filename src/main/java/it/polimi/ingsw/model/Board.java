package it.polimi.ingsw.model;

public class Board implements Cloneable
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

    @Override
    protected final Board clone() {
        final Board result = new Board();
        for(int i = 0; i < 5; i++){
            result.board[i] = board[i].clone();
        }
        return result;
    }
}
