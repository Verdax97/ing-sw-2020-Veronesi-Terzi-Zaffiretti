package it.polimi.ingsw.model;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class WorkerTest
{
    @Test
    public void GetPlayerTest()
    {
        Worker worker = new Worker();
        Player player = new Player("pino");
        worker.setPlayer(player);
        assertEquals("Player is correct", player, worker.getPlayer());
    }

    @Test
    public void GetLastMovementTest()
    {
        Worker worker = new Worker();
        int val = 10;
        worker.setLastMovement(val);
        assertEquals("turn is correct", val, worker.getLastMovement());
    }
}
