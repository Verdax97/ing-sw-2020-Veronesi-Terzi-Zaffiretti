package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Prometheus;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PrometheusTest {
    @Test
    public void PlayerTurnTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("pino");
        players.add("pippo");
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
        board.getCell(2, 2).setWorker(testWorker20);
        board.getCell(3, 1).setWorker(testWorker11);
        board.getCell(4, 0).setWorker(testWorker01);
        board.getCell(0, 0).setWorker(testWorker21);
        Prometheus prometheus = new Prometheus();
        board.getCell(0,3).setBuilding(1);
        board.getCell(1, 4).setBuilding(1);
        board.getCell(3, 0).setBuilding(1);
        board.getCell(4, 1).setBuilding(1);
        serverView.printBoard(board, match);
        Turn turn = new Turn();
        turn.setSelectedCell(board.getCell(0, 4));
        Assertions.assertEquals(1, prometheus.playerTurn(board, turn.getSelectedCell(), 0, 3));
        serverView.printBoard(board, match);
        Assertions.assertTrue(testWorker00.isDebuff());
        serverView.printBoard(board, match);
        turn.setSelectedCell(board.getCell(1, 3));
        Assertions.assertFalse(testWorker10.isDebuff());
        prometheus.playerTurn(board, turn.getSelectedCell(), 1, 2);
        Assertions.assertTrue(testWorker10.isDebuff());
        Assertions.assertEquals(-1, prometheus.playerTurn(board, turn.getSelectedCell(), 5, 5));
        Assertions.assertEquals(-2, prometheus.playerTurn(board, turn.getSelectedCell(), 4, 4));
        Assertions.assertEquals(-3, prometheus.playerTurn(board, turn.getSelectedCell(), 0, 4));
        board.getCell(0, 3).setBuilding(1);
        Assertions.assertEquals(1, prometheus.playerTurn(board, turn.getSelectedCell(), 0, 3));
        Assertions.assertEquals(-4, prometheus.playerTurn(board, turn.getSelectedCell(), 0, 3));
        serverView.printBoard(board, match);

    }
}
