package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import it.polimi.ingsw.model.gods.Pan;
import org.junit.Test;

public class PanTest {
    @Test
    public void WinConditionTest(){
        Board board = new Board();
        Player player = new Player("Pino");
        Worker worker = new Worker();
        God pan = new Pan();
        player.setGodPower(pan);
        worker.setPlayer(player);
        board.getCell(3, 3).setWorker(worker);
        worker.setLastMovement(-2);
        assertEquals("WinCondition Fail", player.getGodPower().WinCondition(board, player), player);
        worker.setLastMovement(0);
        assertNull("WinCondition negative Fail", player.getGodPower().WinCondition(board, player));
    }
}
