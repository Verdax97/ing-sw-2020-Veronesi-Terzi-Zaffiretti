package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CellTest {
    @Test
    public void GetWorkerTest()
    {
        Cell cell = new Cell();
        Worker worker = new Worker ();
        cell.setWorker(worker);
        assertEquals( "Worker is wrong", cell.getWorker(), worker);
    }
    @Test
    public void GetBuildingTest()
    {
        Cell cell = new Cell();
        int building = 2;
        cell.setBuilding(building);
        assertEquals("Building is wrong", cell.getBuilding(), building);
    }
    @Test
    public void GetDomeTest()
    {
        Cell cell = new Cell();
        boolean dome = true;
        cell.setDome(dome);
        assertEquals("Dome is wrong", cell.getDome(), dome);
    }
    @Test
    public void GetPosTest()
    {
        Cell cell = new Cell();
        int x = 5;
        int y = 5;
        cell.setPos(x,y);
        assertEquals("Position is wrong", cell.getPos()[0], x);
        assertEquals("Position is wrong", cell.getPos()[1], y);
    }
    @Test
    public void GetBuiltTurn()
    {
        Cell cell = new Cell();
        int turn = 5;
        cell.setBuiltTurn(turn);
        assertEquals("BuiltTurn is wrong", cell.getBuiltTurn(), turn);
    }
    @Test
    public void GetBuiltBy()
    {
        Cell cell = new Cell();
        Player player = new Player("Pino");
        cell.setBuiltBy(player);
        assertEquals("BuiltBy is Wrong", cell.getBuiltBy(), player);
    }

}



