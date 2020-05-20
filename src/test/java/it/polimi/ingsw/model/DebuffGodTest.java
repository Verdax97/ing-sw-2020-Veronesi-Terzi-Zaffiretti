package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.DebuffGod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


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
        Assertions.assertTrue(testWorker00.isDebuff());
    }
}