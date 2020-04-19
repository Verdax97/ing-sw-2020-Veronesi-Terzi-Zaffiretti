package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Athena;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AthenaTest
{

    @Test
    public void EnemyTurnTest()
    {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        Worker testWorker0 = new Worker();
        testWorker0.setPlayer(new Player(players.get(0)));
        Worker testWorker2 = new Worker();
        testWorker2.setPlayer(new Player(players.get(2)));
        board.getCell(0, 4).setWorker(testWorker0);
        board.getCell(0, 3).setBuilding(1);
        Athena athena = new Athena();
        assertEquals("Error", athena.EnemyTurn(board, testWorker0.getPlayer(), testWorker2.getPlayer()), 0);
        assertEquals("Error", testWorker0.isDebuff(), false);
        board.getCell(0, 0).setWorker(testWorker2);
        testWorker2.setLastMovement(1);
        assertEquals("Error", athena.EnemyTurn(board, testWorker0.getPlayer(), testWorker2.getPlayer()), 1);
        assertEquals("Error", testWorker0.isDebuff(), true);
    }
}
