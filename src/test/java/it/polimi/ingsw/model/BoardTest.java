package it.polimi.ingsw.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class BoardTest {

    @Test
    public void getBoardTest(){
        Cell[][] board = new Cell[5][5];
        Board boardTest = new Board();
        boardTest.setBoard(board);
        assertEquals("Board is wrong", board, boardTest.getBoard());
    }

    @Test
    public void getCellTest(){
        Cell[][] board = new Cell[5][5];
        Board boardTest = new Board();
        int x = 3, y = 2;
        Cell cellTest = new Cell(0,0);
        board[x][y] = cellTest;
        boardTest.setBoard(board);
        assertEquals("Cell is wrong", cellTest, boardTest.getCell(x, y));
    }
    @Test
    public void cloneTest(){
        Cell[][] board = new Cell[5][5];
        Board boardTest = new Board();
        boardTest.Clone();
        assertEquals("Cell is wrong", true, true);
    }
}
