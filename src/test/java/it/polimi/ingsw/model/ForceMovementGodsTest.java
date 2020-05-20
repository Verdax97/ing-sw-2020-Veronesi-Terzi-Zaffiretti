package it.polimi.ingsw.model;
import it.polimi.ingsw.model.gods.ForceMovementGods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        Assertions.assertEquals(-1, forceMovementGod.ForceMove(board, selectedCell, 0, 9));

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
        Assertions.assertEquals(-2, forceMovementGod.ForceMove(board, selectedCell, 0, 4));

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
        Assertions.assertEquals(-3, forceMovementGod.ForceMove(board, selectedCell, 0, 2));
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
        Assertions.assertEquals(1, forceMovementGod.ForceMove(board, selectedCell, 0, 2));
    }

    @Test
    public void invalidWorkerToMove() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        ForceMovementGods forceMovementGod = new ForceMovementGods();
        Cell selectedCell = board.getCell(0, 1);
        Player player = new Player(players.get(0));
        Worker worker = new Worker();
        worker.setPlayer(player);
        selectedCell.setWorker(worker);
        board.getCell(0, 2).setWorker(worker);
        Assertions.assertEquals(-4, forceMovementGod.ForceMove(board, selectedCell, 0, 2));
    }
}
