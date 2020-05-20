package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Artemis;
import it.polimi.ingsw.model.gods.Demeter;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;

public class DemeterTest{

    @Test
    public void BuildingTest(){
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
        Demeter demeter = new Demeter();
        serverView.PrintBoard(board, match);
        Cell selectedCell = board.getCell(0, 0);
        Assertions.assertEquals(2, demeter.Building(board, selectedCell, 1, 1, 0, 0));
        serverView.PrintBoard(board, match);
        Assertions.assertEquals(-8, demeter.Building(board, selectedCell, 1, 1, 0, 0));
        serverView.PrintBoard(board, match);
        Assertions.assertEquals(1, demeter.Building(board, selectedCell, 0, 1, 0, 0));
        serverView.PrintBoard(board, match);
        demeter.ResetGod();
        Assertions.assertEquals(-1, demeter.Building(board, selectedCell, 5, 5, 0, 0));
        Assertions.assertEquals(-2, demeter.Building(board, selectedCell, 4, 4, 0, 0));
        board.getCell(4, 0).setWorker(null);
        board.getCell(1, 0).setWorker(testWorker);
        Assertions.assertEquals(-3, demeter.Building(board, selectedCell, 1, 0, 0, 0));
        board.getCell(0, 1).setBuilding(2);
        Assertions.assertEquals(2, demeter.Building(board, selectedCell, 0, 1, 0, 0));
        demeter.ResetGod();
        Assertions.assertEquals(-4, demeter.Building(board, selectedCell, 0, 1, 0, 0));

    }
}
