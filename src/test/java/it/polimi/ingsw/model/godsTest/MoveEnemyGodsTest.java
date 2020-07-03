package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Worker;
import it.polimi.ingsw.model.gods.MoveEnemyGods;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MoveEnemyGodsTest {
    @Test
    public void moveEnemyTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        ServerView serverView = new ServerView();
        Board board = match.getBoard();
        Worker testWorker00 = new Worker();
        Worker testWorker01 = new Worker();
        testWorker00.setPlayer(new Player(players.get(0)));
        testWorker01.setPlayer(new Player(players.get(0)));
        Worker testWorker10 = new Worker();
        Worker testWorker11 = new Worker();
        testWorker10.setPlayer(new Player(players.get(1)));
        testWorker11.setPlayer(new Player(players.get(1)));
        Worker testWorker20 = new Worker();
        Worker testWorker21 = new Worker();
        testWorker20.setPlayer(new Player(players.get(2)));
        testWorker21.setPlayer(new Player(players.get(2)));
        board.getCell(0, 4).setWorker(testWorker00);
        board.getCell(1, 3).setWorker(testWorker10);
        board.getCell(2, 1).setWorker(testWorker20);
        board.getCell(0, 0).setWorker(testWorker11);
        board.getCell(2, 2).setWorker(testWorker01);
        board.getCell(1, 1).setWorker(testWorker21);
        MoveEnemyGods moveEnemyGods = new MoveEnemyGods();
        Assertions.assertEquals(-5, moveEnemyGods.moveEnemy(board.getCell(2, 2).getWorker(), board, board.getCell(1, 1), 2, 2));
    }
}
