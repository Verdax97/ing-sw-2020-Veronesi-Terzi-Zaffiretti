package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Hestia;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        serverView.printBoard(board, match);
        Cell selectedCell = board.getCell(0, 0);
        Assertions.assertEquals(2, hestia.building(board, selectedCell, 1, 1, 0, 0));
        serverView.printBoard(board, match);
        Assertions.assertEquals(1, hestia.building(board, selectedCell, 1, 1, 0, 0));
        serverView.printBoard(board, match);
        Assertions.assertEquals(2, hestia.building(board, selectedCell, 1, 1, 0, 0));
        Assertions.assertEquals(-9, hestia.building(board, selectedCell, 0, 1, 0, 0));
        serverView.printBoard(board, match);
        hestia.resetGod();
        Assertions.assertEquals(-1, hestia.building(board, selectedCell, 5, 5, 0, 0));
        Assertions.assertEquals(-2, hestia.building(board, selectedCell, 4, 4, 0, 0));
        board.getCell(4, 0).setWorker(null);
        board.getCell(1, 0).setWorker(testWorker);
        Assertions.assertEquals(-3, hestia.building(board, selectedCell, 1, 0, 0, 0));
        board.getCell(0, 1).setBuilding(3);
        Assertions.assertEquals(2, hestia.building(board, selectedCell, 0, 1, 0, 0));
        hestia.resetGod();
        Assertions.assertEquals(-4, hestia.building(board, selectedCell, 0, 1, 0, 0));
    }
}
