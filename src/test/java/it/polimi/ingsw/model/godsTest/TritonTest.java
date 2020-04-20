package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Triton;
import it.polimi.ingsw.view.ServerView;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TritonTest {
    @Test
    public void MoveTest(){
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
        Triton triton = new Triton();
        serverView.PrintBoard(board, match);
        Cell selectedCell = board.getCell(3, 1);
        assertEquals("Return value is wrong", 2, triton.Move(board, selectedCell, 3, 0));
        serverView.PrintBoard(board, match);
        selectedCell = board.getCell(3, 0);
        assertEquals("Return value is wrong", 1, triton.Move(board, selectedCell, 2, 0));
        serverView.PrintBoard(board, match);
        selectedCell = board.getCell(2, 0);
        assertEquals("Return value is wrong", 1, triton.Move(board, selectedCell, 1, 0));
        serverView.PrintBoard(board, match);
    }
}
