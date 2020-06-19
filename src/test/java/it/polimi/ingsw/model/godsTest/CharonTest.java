package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Charon;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CharonTest{

    @Test
    public void CharonTest(){
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
        board.getCell(3, 1).setWorker(testWorker11);
        board.getCell(4, 0).setWorker(testWorker01);
        board.getCell(1, 1).setWorker(testWorker21);
        Charon charon = new Charon();
        Turn turn = new Turn();
        serverView.printBoard(board, match);
        turn.setSelectedCell(board.getCell(0, 4));
        Assertions.assertEquals(-7, charon.PlayerTurn(board, turn.getSelectedCell(), 1, 3));
        serverView.printBoard(board, match);
        turn.setSelectedCell(board.getCell(2, 1));
        Assertions.assertEquals(-2, charon.PlayerTurn(board, turn.getSelectedCell(), 1, 1));
        serverView.printBoard(board, match);
        turn.setSelectedCell(board.getCell(3, 1));
        Assertions.assertEquals(1, charon.PlayerTurn(board, turn.getSelectedCell(), 2, 1));
        serverView.printBoard(board, match);
        Assertions.assertEquals(-2, charon.PlayerTurn(board, turn.getSelectedCell(), 2, 1));
        board.getCell(2, 1).setWorker(board.getCell(1, 1).getWorker());
        board.getCell(1, 1).setWorker(null);
        serverView.printBoard(board, match);
        turn.setSelectedCell(board.getCell(3, 1));
        Assertions.assertEquals(-5, charon.PlayerTurn(board, turn.getSelectedCell(), 2, 1));
        serverView.printBoard(board, match);
    }
}
