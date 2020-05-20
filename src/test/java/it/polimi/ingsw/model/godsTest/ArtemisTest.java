package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Artemis;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;

public class ArtemisTest
{
    @Test
    public void MoveTest()
    {
        ArrayList<String> players = new ArrayList<>();
        players.add("pino");
        players.add("pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        ServerView serverView = new ServerView();
        Board board = match.getBoard();
        Worker testWorker00 = new Worker();
        testWorker00.setPlayer(new Player(players.get(0)));
        Worker testWorker11 = new Worker();
        testWorker11.setPlayer(new Player(players.get(1)));
        Worker testWorker22 = new Worker();
        testWorker22.setPlayer(new Player(players.get(2)));
        board.getCell(0, 4).setWorker(testWorker00);
        board.getCell(1, 3).setWorker(testWorker11);
        board.getCell(2, 2).setWorker(testWorker22);
        board.getCell(3, 1).setWorker(testWorker11);
        board.getCell(4, 0).setWorker(testWorker00);
        board.getCell(0, 0).setWorker(testWorker22);
        Artemis artemis = new Artemis();
        serverView.PrintBoard(board, match);
        Cell selectedCell = board.getCell(0, 0);
        Assertions.assertEquals(2, artemis.Move(board, selectedCell, 1, 1));
        serverView.PrintBoard(board, match);
        selectedCell = board.getCell(1, 1);
        Assertions.assertEquals(-6, artemis.Move(board, selectedCell, 0, 0));
        serverView.PrintBoard(board, match);
        artemis.ResetGod();
        artemis.Move(board, selectedCell, 0, 0);
        artemis.ResetGod();
        selectedCell = board.getCell(0, 0);
        Assertions.assertEquals(-1, artemis.Move(board, selectedCell, 5, 5));
        Assertions.assertEquals(-2, artemis.Move(board, selectedCell, 4, 4));
        board.getCell(1, 0).setBuilding(2);
        Assertions.assertEquals(-3, artemis.Move(board, selectedCell, 1, 0));
        board.getCell(1, 1).setDome(true);
        Assertions.assertEquals(-4, artemis.Move(board, selectedCell, 1, 1));
        board.getCell(1, 1).setDome(false);
        board.getCell(4, 0).setWorker(null);
        board.getCell(1, 1).setWorker(testWorker00);
        Assertions.assertEquals(-4, artemis.Move(board, selectedCell, 1, 1));
        Assertions.assertEquals(2, artemis.Move(board, selectedCell, 0, 1));
        serverView.PrintBoard(board, match);

    }
}
