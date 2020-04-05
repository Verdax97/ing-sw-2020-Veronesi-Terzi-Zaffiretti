package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ServerViewTest
{
    @Test
    public void PrintBoardTest()
    {
        ArrayList<String> players = new ArrayList<>();
        players.add("pino");
        players.add("pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        SetupMatch setupMatch = new SetupMatch();
        ServerView serverView = new ServerView();
        Board board = match.getBoard();
        board.getCell(4,4).setDome(true);
        board.getCell(3,3).setBuilding(3);
        board.getCell(2,2).setBuilding(2);
        board.getCell(1,1).setBuilding(1);
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
        board.getCell(4, 4).setWorker(testWorker3);
        board.getCell(0, 0).setWorker(testWorker3);
        serverView.PrintBoard(match.getBoard(), match);
    }
}
