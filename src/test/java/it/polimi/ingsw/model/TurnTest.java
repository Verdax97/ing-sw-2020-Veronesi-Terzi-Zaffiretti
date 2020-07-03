package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import org.junit.jupiter.api.Assertions;

public class TurnTest
{
    @Test
    public void GetTurnTest() {
        Turn turn = new Turn();
        turn.setTurn(5);
        Assertions.assertEquals(5, turn.getTurn());
    }

    @Test
    public void GetValidMovesTest() {
        Turn turn = new Turn();
        turn.setValidMoves(5);
        Assertions.assertEquals(5, turn.getValidMoves());
    }

    @Test
    public void GetWorkersTest()
    {
        Turn turn = new Turn();
        ArrayList<Worker> workers = new ArrayList<>();
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        turn.setWorkers(workers);
        Assertions.assertEquals(workers, turn.getWorkers());
    }

    @Test
    public void GetSelectedCellTest()
    {
        Turn turn = new Turn();
        Cell cell = new Cell(0,0);
        turn.setSelectedCell(cell);
        Assertions.assertEquals(turn.getSelectedCell(), cell);
    }

    @Test
    public void BuildTest() {
        Board board = new Board();
        Turn turn = new Turn();
        Player player = new Player("Pino");
        Worker worker = new Worker();
        Worker worker2 = new Worker();
        worker.setPlayer(player);
        God god = new God();
        player.setGodPower(god);
        board.getCell(1, 0).setWorker(worker2);
        board.getCell(0, 0).setWorker(worker);
        turn.setSelectedCell(board.getCell(0, 0));
        board.getCell(0, 1).setDome(true);
        Assertions.assertEquals(turn.build(board, -1, -1, 0), -1);
        Assertions.assertEquals(turn.build(board, 1, 1, 0), 1);
        Assertions.assertEquals(turn.build(board, 4, 4, 0), -2);
        Assertions.assertEquals(turn.build(board, 1, 0, 0), -3);
        Assertions.assertEquals(turn.build(board, 0, 1, 0), -4);
        Demeter demeter = new Demeter();
        player.setGodPower(demeter);
        Assertions.assertEquals(turn.build(board, 1, 1, 0), 2);
        demeter.resetGod();
        board.getCell(1, 1).setBuilding(1);
        player.setGodPower(god);
        Assertions.assertEquals(turn.build(board, 1, 1, 0), 1);

    }

    @Test
    public void MoveTest(){
        Turn turn = new Turn();
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        Board board = match.getBoard();
        board.getCell(4,4).setDome(true);
        board.getCell(3,3).setBuilding(3);
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
        God god = new God();
        testWorker3.getPlayer().setGodPower(god);
        turn.setSelectedCell(board.getCell(0, 0));
        Assertions.assertEquals(turn.move(board, -1, -1), -1);
        Assertions.assertEquals(turn.move(board, 1, 1), 1);
        turn.setSelectedCell(board.getCell(1, 1));
        board.getCell(1, 2).setBuilding(3);
        Assertions.assertEquals(turn.move(board, 1, 2), -3);
        Assertions.assertEquals(turn.move(board, 4, 3), -2);
        Assertions.assertEquals(turn.move(board, 2, 2), -4);
        board.getCell(2, 1).setDome(true);
        Assertions.assertEquals(turn.move(board, 2, 1), -4);
        Triton triton = new Triton();
        testWorker3.getPlayer().setGodPower(triton);
        Assertions.assertEquals(turn.move(board, 0, 0), 2);


    }

    @Test
    public void StartTurnTest(){
        Board board = new Board();
        Turn turn = new Turn();
        Player player1 = new Player("Pino");
        Player player2 = new Player("Pippo");
        Worker worker1p1 =new Worker();
        Worker worker2p1 =new Worker();
        Worker worker1p2 =new Worker();
        Worker worker2p2 =new Worker();
        worker1p1.setPlayer(player1);
        worker2p1.setPlayer(player1);
        worker1p2.setPlayer(player2);
        worker2p2.setPlayer(player2);
        board.getCell(0,0).setWorker(worker1p1);
        board.getCell(0, 1).setWorker(worker2p1);
        board.getCell(1,0).setWorker(worker1p2);
        board.getCell(1,1).setWorker(worker2p2);
        God god1 = new God();
        player1.setGodPower(god1);
        God god2 = new God();
        player2.setGodPower(god2);
        ArrayList<Player> ActivePlayers = new ArrayList<>();
        ActivePlayers.add(player1);
        ActivePlayers.add(player2);
    }

    @Test
    public void BeforeMoveTest(){
        Board board = new Board();
        Turn turn = new Turn();
        Assertions.assertEquals(1, turn.beforeMove(board, -5, -5));
        turn.setSelectedCell(board.getCell(0,0));
        Worker worker = new Worker();
        Player player = new Player("Pino");
        God god = new God();
        player.setGodPower(god);
        worker.setPlayer(player);
        turn.getSelectedCell().setWorker(worker);
        Assertions.assertEquals(1, turn.beforeMove(board, 1, 1));
    }
}
