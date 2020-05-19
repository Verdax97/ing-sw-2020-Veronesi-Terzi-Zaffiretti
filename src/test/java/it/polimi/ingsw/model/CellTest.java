package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {

    @Test
    public void GetWorkerTest()
    {
        Cell cell = new Cell(0,0);
        Worker worker = new Worker ();
        cell.setWorker(worker);
        assertEquals( "Worker is wrong", cell.getWorker(), worker);
    }

    @Test
    public void GetBuildingTest()
    {
        Cell cell = new Cell(0,0);
        int building = 2;
        cell.setBuilding(building);
        assertEquals("Building is wrong", cell.getBuilding(), building);
    }

    @Test
    public void GetDomeTest()
    {
        Cell cell = new Cell(0,0);
        boolean dome = true;
        cell.setDome(dome);
        assertEquals("Dome is wrong", cell.getDome(), dome);
    }

    @Test
    public void GetPosTest()
    {
        int x = 5;
        int y = 5;
        Cell cell = new Cell(x,y);
        assertEquals("Position is wrong", cell.getPos()[0], x);
        assertEquals("Position is wrong", cell.getPos()[1], y);
    }

    @Test
    public void GetBuiltTurnTest()
    {
        Cell cell = new Cell(0,0);
        int turn = 5;
        cell.setBuiltTurn(turn);
        assertEquals("BuiltTurn is wrong", cell.getBuiltTurn(), turn);
    }

    @Test
    public void GetBuiltByTest()
    {
        Cell cell = new Cell(0,0);
        Player player = new Player("Pino");
        cell.setBuiltBy(player);
        assertEquals("BuiltBy is Wrong", cell.getBuiltBy(), player);
    }

    @Test
    public void IsAdjacentTest ()
    {
        Cell cell = new Cell(0, 0);
        cell.setPos(3, 3);
        assertTrue("isAdjacent is wrong", cell.isAdjacent(4, 4));
        assertFalse("isAdjacent is wrong", cell.isAdjacent(4, 5));
        assertFalse("isAdjacent is wrong", cell.isAdjacent(3, 3));
    }

    @Test
    public void IsNotHighTest(){
        Board board = new Board();
        Player player = new Player("Pino");
        Worker worker = new Worker();
        worker.setPlayer(player);
        worker.setDebuff(false);
        board.getCell(0,0).setWorker(worker);
        board.getCell(0,1).setBuilding(2);
        Cell cell = board.getCell(0,0);
        assertFalse("IsNotHigh is high", cell.IsNotHigh(board, 0, 1));
        assertTrue("IsNotHigh is high", cell.IsNotHigh(board, 1, 1));
        board.getCell(0,1).setBuilding(-1);
        assertTrue("IsNotHigh is high", cell.IsNotHigh(board, 0, 1));
        worker.setDebuff(true);
        assertFalse("IsNotHigh is high", cell.IsNotHigh(board, 0, 1));
    }

    @Test
    public void IsFreeDomeTest(){
        Board board = new Board();
        Cell cell = board.getCell(0, 0);
        cell.setDome(true);
        assertFalse("IsFreeDome is wrong", cell.IsFreeDome(board, 0, 0));
        cell.setDome(false);
        assertTrue("IsFreeDome is wrong", cell.IsFreeDome(board, 0, 0));
    }

    @Test
    public void IsFreeWorker() {
        Board board = new Board();
        Worker worker = new Worker();
        Cell cell = board.getCell(0, 0);
        cell.setWorker(worker);
        assertFalse("IsFreeWorker is wrong", cell.IsFreeWorker(board, 0, 0));
        assertTrue("IsFreeWorker is wrong", cell.IsFreeWorker(board, 0, 1));
    }
}



