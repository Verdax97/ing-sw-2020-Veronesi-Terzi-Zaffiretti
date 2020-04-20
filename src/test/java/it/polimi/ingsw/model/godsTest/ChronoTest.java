package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Chrono;
import it.polimi.ingsw.view.ServerView;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ChronoTest {
    @Test
    public void WinConditionTest(){
        Board board = new Board();
        Player player = new Player("Pino");
        Worker worker = new Worker();
        God chrono = new Chrono();
        player.setGodPower(chrono);
        worker.setPlayer(player);
        board.getCell(3, 3).setWorker(worker);
        board.getCell(0,0).setBuilding(3);
        board.getCell(0,0).setDome(true);
        board.getCell(0,1).setBuilding(3);
        board.getCell(0,1).setDome(true);
        board.getCell(0,2).setBuilding(3);
        board.getCell(0,2).setDome(true);
        board.getCell(0,3).setBuilding(3);
        board.getCell(0,3).setDome(true);
        assertNull("WinCondition negative Fail", player.getGodPower().WinCondition(board, player));
        board.getCell(0,4).setBuilding(3);
        board.getCell(0,4).setDome(true);
        assertEquals("WinCondition Fail", player.getGodPower().WinCondition(board, player), player);
    }
}
