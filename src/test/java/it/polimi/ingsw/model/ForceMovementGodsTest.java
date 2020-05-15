package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.Minotaur;
import it.polimi.ingsw.view.ServerView;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ForceMovementGodsTest {
    @Test
    public void OutsideBoard()
    {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        ForceMovementGods forceMovementGod = new ForceMovementGods();
        Cell selectedCell = board.getCell(0, 1);
        assertEquals("Return value is wrong", -1, forceMovementGod.ForceMove(board, selectedCell, 0, 9));

    }

    @Test
    public void CellTooFar()
    {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        ForceMovementGods forceMovementGod = new ForceMovementGods();
        Cell selectedCell = board.getCell(0, 1);
        assertEquals("Return value is wrong", -2, forceMovementGod.ForceMove(board, selectedCell, 0, 4));

    }

    @Test
    public void CellIsHigh()
    {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        ForceMovementGods forceMovementGod = new ForceMovementGods();
        Cell selectedCell = board.getCell(0, 1);
        board.getCell(0,2).setBuilding(2);
        Worker worker = new Worker();
        selectedCell.setWorker(worker);
        assertEquals("Return value is wrong", -3, forceMovementGod.ForceMove(board, selectedCell, 0, 2));
    }

    @Test
    public void NoWorkerToMove()
    {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        ForceMovementGods forceMovementGod = new ForceMovementGods();
        Cell selectedCell = board.getCell(0, 1);
        Worker worker = new Worker();
        selectedCell.setWorker(worker);
        assertEquals("Return value is wrong", 1, forceMovementGod.ForceMove(board, selectedCell, 0, 2));
    }

    @Test
    public  void MoveEnemyTest()
    {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        ForceMovementGods forceMovementGod = new ForceMovementGods();
        forceMovementGod.targetPosX = 1;
        forceMovementGod.targetPosY = 0;
        Cell selectedCell = board.getCell(0, 1);
        Worker worker = new Worker();
        Worker enemyWorker = new Worker();
        worker.setPlayer(match.getPlayers().get(0));
        enemyWorker.setPlayer(match.getPlayers().get(1));
        selectedCell.setWorker(worker);
        board.getCell(1, 0).setWorker(enemyWorker);
        assertEquals("Return value is wrong", 1, forceMovementGod.MoveEnemy(worker, board, selectedCell, 1, 0));
    }
}
