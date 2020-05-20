package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Hestia;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;

public class HestiaTest
{
    @Test
    public void BuildTest()
    {
        ArrayList<String> players = new ArrayList<>();
        players.add("pino");
        players.add("pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        ServerView serverView = new ServerView();
        Board board = match.getBoard();
        Worker testWorker = new Worker();
        testWorker.setPlayer(new Player(players.get(0)));
        Worker testWorker2 = new Worker();
        testWorker2.setPlayer(new Player(players.get(1)));
        Worker testWorker3 = new Worker();
        testWorker3.setPlayer(new Player(players.get(2)));
        board.getCell(0, 4).setWorker(testWorker);
        board.getCell(1, 3).setWorker(testWorker2);
        board.getCell(2, 2).setWorker(testWorker3);
        board.getCell(3, 1).setWorker(testWorker2);
        board.getCell(4, 0).setWorker(testWorker);
        board.getCell(0, 0).setWorker(testWorker3);
        Hestia hestia = new Hestia();
        serverView.PrintBoard(board, match);
        Cell selectedCell = board.getCell(0, 0);
        Assertions.assertEquals(2, hestia.Building(board, selectedCell, 1, 1, 0, 0));
        serverView.PrintBoard(board, match);
        Assertions.assertEquals(1, hestia.Building(board, selectedCell, 1, 1, 0, 0));
        serverView.PrintBoard(board, match);
        Assertions.assertEquals(2, hestia.Building(board, selectedCell, 1, 1, 0, 0));
        Assertions.assertEquals(-9, hestia.Building(board, selectedCell, 0, 1, 0, 0));
        serverView.PrintBoard(board, match);
        hestia.ResetGod();
        Assertions.assertEquals(-1, hestia.Building(board, selectedCell, 5, 5, 0, 0));
        Assertions.assertEquals(-2, hestia.Building(board, selectedCell, 4, 4, 0, 0));
        board.getCell(4, 0).setWorker(null);
        board.getCell(1, 0).setWorker(testWorker);
        Assertions.assertEquals(-3, hestia.Building(board, selectedCell, 1, 0, 0, 0));
        board.getCell(0, 1).setBuilding(3);
        Assertions.assertEquals(2, hestia.Building(board, selectedCell, 0, 1, 0, 0));
        hestia.ResetGod();
        Assertions.assertEquals(-4, hestia.Building(board, selectedCell, 0, 1, 0, 0));
    }
}
