package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.Assertions;


import it.polimi.ingsw.model.gods.Pan;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(player.getGodPower().winCondition(board, player), player);
        worker.setLastMovement(0);
        Assertions.assertNull(player.getGodPower().winCondition(board, player));
    }
}
