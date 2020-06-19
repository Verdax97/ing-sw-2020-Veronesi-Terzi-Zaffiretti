package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Atlas;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AtlasTest {
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
        Atlas atlas = new Atlas();
        serverView.printBoard(board, match);
        Cell selectedCell = board.getCell(0, 4);
        Assertions.assertEquals(1, atlas.Building(board, selectedCell, 1, 4, 0, 0));
        serverView.printBoard(board, match);
        Assertions.assertEquals(1, atlas.Building(board, selectedCell, 0, 3, 1, 0));
        serverView.printBoard(board, match);
        Assertions.assertEquals(-4, atlas.Building(board, selectedCell, 0, 3, 1, 0));
        serverView.printBoard(board, match);
        Assertions.assertEquals(1, atlas.Building(board, selectedCell, 1, 4, 1, 0));
        serverView.printBoard(board, match);
        selectedCell = board.getCell(0, 0);
        board.getCell(0, 1).setBuilding(3);
        Assertions.assertEquals(1, atlas.Building(board, selectedCell, 0, 1, 0, 0));
        selectedCell = board.getCell(4, 0);
        Assertions.assertEquals(-3, atlas.Building(board, selectedCell, 3, 1, 0, 0));
        Assertions.assertEquals(-2, atlas.Building(board, selectedCell, 0, 1, 0, 0));
        Assertions.assertEquals(-1, atlas.Building(board, selectedCell, 5, 5, 0, 0));

    }
}
