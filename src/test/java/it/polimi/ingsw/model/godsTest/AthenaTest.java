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
        players.add("pino");
        players.add("pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        Worker testWorker = new Worker();
        testWorker.setPlayer(new Player(players.get(0)));
        Worker testWorker3 = new Worker();
        testWorker3.setPlayer(new Player(players.get(2)));
        board.getCell(0, 4).setWorker(testWorker);
        board.getCell(0, 3).setBuilding(1);
        Athena athena = new Athena();
        assertEquals("random error", athena.EnemyTurn(board, testWorker.getPlayer(), testWorker3.getPlayer()), 0);
        assertEquals("random error", testWorker.isDebuff(), false);
        board.getCell(0, 0).setWorker(testWorker3);
        testWorker3.setLastMovement(1);
        assertEquals("random error", athena.EnemyTurn(board, testWorker.getPlayer(), testWorker3.getPlayer()), 1);
        assertEquals("random error", testWorker.isDebuff(), true);
    }
}
