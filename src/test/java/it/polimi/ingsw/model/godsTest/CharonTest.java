package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Charon;
import it.polimi.ingsw.view.ServerView;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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
        serverView.PrintBoard(board, match);
        assertEquals("Return value is wrong", -7, charon.PlayerTurn(board, testWorker00.getPlayer(), 1, 3));
        serverView.PrintBoard(board, match);
        assertEquals("Return value is wrong", 1, charon.PlayerTurn(board, testWorker11.getPlayer(), 2, 1));
        serverView.PrintBoard(board, match);
        assertEquals("Return value is wrong", -5, charon.PlayerTurn(board, testWorker20.getPlayer(), 3, 1));
        serverView.PrintBoard(board, match);
    }
}
