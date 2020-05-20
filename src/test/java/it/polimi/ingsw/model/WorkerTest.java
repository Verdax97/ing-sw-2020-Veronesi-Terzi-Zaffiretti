package it.polimi.ingsw.model;
import org.junit.jupiter.api.Assertions;


import org.junit.jupiter.api.Test;
public class WorkerTest
{
    @Test
    public void GetPlayerTest()
    {
        Worker worker = new Worker();
        Player player = new Player("pino");
        worker.setPlayer(player);
        Assertions.assertEquals(player, worker.getPlayer());
    }

    @Test
    public void GetLastMovementTest()
    {
        Worker worker = new Worker();
        int val = 10;
        worker.setLastMovement(val);
        Assertions.assertEquals(val, worker.getLastMovement());
    }

    @Test
    public void isDebuffTest() {
        Worker worker = new Worker();
        Player player = new Player("Pino");
        worker.setPlayer(player);
        worker.setDebuff(true);
        Assertions.assertTrue(worker.isDebuff());
    }
}
