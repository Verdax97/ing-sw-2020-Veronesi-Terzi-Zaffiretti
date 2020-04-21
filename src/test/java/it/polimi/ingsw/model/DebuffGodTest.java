package it.polimi.ingsw.model;

import org.junit.Test;
import java.util.ArrayList;

public class DebuffGodTest{

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
        DebuffGod gianni = new DebuffGod();
        gianni.debuff = true;
        gianni.DebuffWorker(board, testWorker00.getPlayer());
        boolean test = testWorker00.isDebuff();
        if (test == true){
            return;
        }
        else {throw new RuntimeException("Error");}
    }
}