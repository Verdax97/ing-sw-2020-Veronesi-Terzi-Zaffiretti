package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Minotaur;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MinotaurTest {
    @Test
    public void MoveTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        ServerView serverView = new ServerView();
        Board board = match.getBoard();
        Worker testWorker00 = new Worker();
        Worker testWorker01 = new Worker();
        testWorker01.setPlayer(new Player(players.get(0)));
        testWorker00.setPlayer(new Player(players.get(0)));
        Worker testWorker10 = new Worker();
        Worker testWorker11 = new Worker();
        testWorker10.setPlayer(new Player(players.get(1)));
        testWorker11.setPlayer(new Player(players.get(1)));
        Worker testWorker20 = new Worker();
        Worker testWorker21 = new Worker();
        testWorker20.setPlayer(new Player(players.get(2)));
        testWorker21.setPlayer(new Player(players.get(2)));
        board.getCell(0, 4).setWorker(testWorker00);
        board.getCell(1, 3).setWorker(testWorker21);
        board.getCell(3, 1).setWorker(testWorker20);
        board.getCell(4, 0).setWorker(testWorker01);
        board.getCell(0, 0).setWorker(testWorker11);
        board.getCell(0, 1).setWorker(testWorker10);
        Minotaur minotaur = new Minotaur();
        serverView.printBoard(board, match);
        Cell selectedCell = board.getCell(1, 3);
        Assertions.assertEquals(-7, minotaur.move(board, selectedCell, 0, 4));
        serverView.printBoard(board, match);
        selectedCell = board.getCell(0, 4);
        Assertions.assertEquals(1, minotaur.move(board, selectedCell, 1, 3));
        serverView.printBoard(board, match);
        selectedCell = board.getCell(1, 3);
        Assertions.assertEquals(-5, minotaur.move(board, selectedCell, 2, 2));
        serverView.printBoard(board, match);
        board.getCell(0, 4).setDome(true);
        selectedCell = board.getCell(2, 2);
        Assertions.assertEquals(-5, minotaur.move(board, selectedCell, 1, 3));
        serverView.printBoard(board, match);
    }
}
