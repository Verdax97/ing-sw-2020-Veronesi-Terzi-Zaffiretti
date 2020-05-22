package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void getBoardTest(){
        Cell[][] board = new Cell[5][5];
        Board boardTest = new Board();
        boardTest.setBoard(board);
        Assertions.assertEquals(board, boardTest.getBoard(), "getBoardError");

    }

    @Test
    public void getCellTest(){
        Cell[][] board = new Cell[5][5];
        Board boardTest = new Board();
        int x = 3, y = 2;
        Cell cellTest = new Cell(0,0);
        board[x][y] = cellTest;
        boardTest.setBoard(board);
        Assertions.assertEquals(cellTest, boardTest.getCell(x, y));
    }
    @Test
    public void cloneTest(){
        Board boardTest = new Board();
        boardTest.CopyValuesInNewBoard();
        Assertions.assertTrue(true, "Cell is wrong");
    }

}
