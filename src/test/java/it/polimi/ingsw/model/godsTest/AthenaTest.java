package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Athena;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;

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
        Assertions.assertEquals(athena.enemyTurn(board, testWorker0.getPlayer(), testWorker2.getPlayer()), 0);
        Assertions.assertFalse(testWorker0.isDebuff());
        board.getCell(0, 0).setWorker(testWorker2);
        testWorker2.setLastMovement(1);
        Assertions.assertEquals(athena.enemyTurn(board, testWorker0.getPlayer(), testWorker2.getPlayer()), 1);
        Assertions.assertTrue(testWorker0.isDebuff());
    }
}
