package it.polimi.ingsw.model.godsTest;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gods.Zeus;
import it.polimi.ingsw.view.ServerView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ZeusTest {
    @Test
    public void BuildTest(){
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
        board.getCell(3, 1).setWorker(testWorker01);
        board.getCell(4, 0).setWorker(testWorker11);
        board.getCell(0, 0).setWorker(testWorker21);
        Zeus zeus = new Zeus();
        serverView.printBoard(board, match);
        Cell selectedCell = board.getCell(0, 0);
        Assertions.assertEquals(1, zeus.building(board, selectedCell, 0, 0, 0, 0));
        serverView.printBoard(board, match);
        Assertions.assertEquals(1, zeus.building(board, selectedCell, 0, 0, 0, 0));
        serverView.printBoard(board, match);
        Assertions.assertEquals(1, zeus.building(board, selectedCell, 0, 0, 0, 0));
        serverView.printBoard(board, match);
        Assertions.assertEquals(-7, zeus.building(board, selectedCell, 0, 0, 0, 0));
        serverView.printBoard(board, match);
        zeus.resetGod();
        Assertions.assertEquals(-1, zeus.building(board, selectedCell, 5, 5, 0, 0));
        Assertions.assertEquals(-2, zeus.building(board, selectedCell, 4, 4, 0, 0));
        board.getCell(4, 0).setWorker(null);
        board.getCell(1, 0).setWorker(testWorker11);
        board.getCell(0, 1).setBuilding(3);
        serverView.printBoard(board, match);
        Assertions.assertEquals(1, zeus.building(board, selectedCell, 0, 1, 0, 0));
        zeus.resetGod();
        Assertions.assertEquals(-4, zeus.building(board, selectedCell, 0, 1, 0, 0));
        serverView.printBoard(board, match);
        Assertions.assertEquals(-3, zeus.building(board, selectedCell, 1, 0, 0, 0));
        
    }
}