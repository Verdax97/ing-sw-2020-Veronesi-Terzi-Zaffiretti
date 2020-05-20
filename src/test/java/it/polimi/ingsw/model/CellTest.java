package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions;

public class CellTest {

    @Test
    public void GetWorkerTest()
    {
        Cell cell = new Cell(0,0);
        Worker worker = new Worker ();
        cell.setWorker(worker);
        Assertions.assertEquals(cell.getWorker(), worker);
    }

    @Test
    public void GetBuildingTest()
    {
        Cell cell = new Cell(0,0);
        int building = 2;
        cell.setBuilding(building);
        Assertions.assertEquals(cell.getBuilding(), building);
    }

    @Test
    public void GetDomeTest() {
        Cell cell = new Cell(0, 0);
        cell.setDome(true);
        Assertions.assertEquals(cell.getDome(), cell.getDome());
    }

    @Test
    public void GetPosTest() {
        int x = 5;
        int y = 5;
        Cell cell = new Cell(x, y);
        Assertions.assertEquals(cell.getPos()[0], x);
        Assertions.assertEquals(cell.getPos()[1], y);
    }

    @Test
    public void GetBuiltTurnTest()
    {
        Cell cell = new Cell(0,0);
        int turn = 5;
        cell.setBuiltTurn(turn);
        Assertions.assertEquals(cell.getBuiltTurn(), turn);
    }

    @Test
    public void GetBuiltByTest()
    {
        Cell cell = new Cell(0,0);
        Player player = new Player("Pino");
        cell.setBuiltBy(player);
        Assertions.assertEquals(cell.getBuiltBy(), player);
    }

    @Test
    public void IsAdjacentTest () {
        Cell cell = new Cell(0, 0);
        cell.setPos(3, 3);
        Assertions.assertTrue(cell.isAdjacent(4, 4));
        Assertions.assertFalse(cell.isAdjacent(4, 5));
        Assertions.assertFalse(cell.isAdjacent(3, 3));
    }

    @Test
    public void IsNotHighTest() {
        Board board = new Board();
        Player player = new Player("Pino");
        Worker worker = new Worker();
        worker.setPlayer(player);
        worker.setDebuff(false);
        board.getCell(0, 0).setWorker(worker);
        board.getCell(0, 1).setBuilding(2);
        Cell cell = board.getCell(0, 0);
        Assertions.assertFalse(cell.IsNotHigh(board, 0, 1));
        Assertions.assertTrue(cell.IsNotHigh(board, 1, 1));
        board.getCell(0, 1).setBuilding(-1);
        Assertions.assertTrue(cell.IsNotHigh(board, 0, 1));
        worker.setDebuff(true);
        Assertions.assertFalse(cell.IsNotHigh(board, 0, 1));
    }

    @Test
    public void IsFreeDomeTest(){
        Board board = new Board();
        Cell cell = board.getCell(0, 0);
        cell.setDome(true);
        Assertions.assertFalse(cell.IsFreeDome(board, 0, 0));
        cell.setDome(false);
        Assertions.assertTrue(cell.IsFreeDome(board, 0, 0));
    }

    @Test
    public void IsFreeWorker() {
        Board board = new Board();
        Worker worker = new Worker();
        Cell cell = board.getCell(0, 0);
        cell.setWorker(worker);
        Assertions.assertFalse(cell.IsFreeWorker(board, 0, 0));
        Assertions.assertTrue(cell.IsFreeWorker(board, 0, 1));
    }
}



