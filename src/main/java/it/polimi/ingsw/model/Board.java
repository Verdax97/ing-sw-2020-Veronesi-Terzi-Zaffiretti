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
}
