package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.DebuffGod;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class DebuffGodTest {

    @Test
    public void debuffWorkerTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        Worker testWorker00 = new Worker();
        testWorker00.setPlayer(new Player(players.get(0)));
        board.getCell(0,0).setWorker(testWorker00);
        DebuffGod gianni = new DebuffGod();
        gianni.setDebuff(true);
        gianni.DebuffWorker(board, testWorker00.getPlayer());
        assertTrue("IsFreeDome is wrong", testWorker00.isDebuff());
    }
}