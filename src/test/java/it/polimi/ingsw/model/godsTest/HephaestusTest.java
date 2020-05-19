package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Demeter;
import it.polimi.ingsw.model.gods.Hephaestus;
import it.polimi.ingsw.view.ServerView;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HephaestusTest {

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
        Hephaestus hephaestus = new Hephaestus();
        serverView.PrintBoard(board, match);
        Cell selectedCell = board.getCell(0, 0);
        assertEquals("Return value is wrong", 2, hephaestus.Building(board, selectedCell, 1, 1, 0, 0));
        serverView.PrintBoard(board, match);
        assertEquals("Return value is wrong", 1, hephaestus.Building(board, selectedCell, 1, 1, 0, 0));
        serverView.PrintBoard(board, match);
        board.getCell(0,1).setBuilding(2);
        serverView.PrintBoard(board, match);
        assertEquals("Return value is wrong", 1, hephaestus.Building(board, selectedCell, 0, 1, 0, 0));
        serverView.PrintBoard(board, match);
        assertEquals("Return value is wrong", 2, hephaestus.Building(board, selectedCell, 1, 0, 0, 0));
        serverView.PrintBoard(board, match);
        assertEquals("Return value is wrong", 1, hephaestus.Building(board, selectedCell, 0, 1, 0, 0));
        serverView.PrintBoard(board, match);
        hephaestus.ResetGod();
        assertEquals("Return value is wrong", -1, hephaestus.Building(board, selectedCell,5,5,0, 0));
        assertEquals("Return value is wrong", -2, hephaestus.Building(board, selectedCell,4,4,0, 0));
        board.getCell(4, 0).setWorker(null);
        board.getCell(1, 0).setWorker(testWorker);
        assertEquals("Return value is wrong", -3, hephaestus.Building(board, selectedCell,1,0,0, 0));
        assertEquals("Return value is wrong", 1, hephaestus.Building(board, selectedCell,0,1,0,0));
        hephaestus.ResetGod();
        assertEquals("Return value is wrong", -4, hephaestus.Building(board,selectedCell,0,1,0,0));
        serverView.PrintBoard(board, match);
        // For coverage only (not reacheable)
        selectedCell = board.getCell(1,3);
        hephaestus.ResetGod();
        hephaestus.Building(board, selectedCell,0,3,0,0);
        board.getCell(0,3).setBuilding(2);
        assertEquals("Return value is wrong", -6, hephaestus.Building(board,selectedCell,0,3,0,0));
    }
}
