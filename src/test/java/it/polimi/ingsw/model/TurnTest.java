package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.*;
import it.polimi.ingsw.view.ServerView;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TurnTest
{
    @Test
    public void GetTurnTest()
    {
        Turn turn = new Turn();
        int ciccio = 5;
        turn.setTurn(ciccio);
        assertEquals("turn is wrong", ciccio, turn.getTurn());
    }

    @Test
    public void GetValidMovesTest()
    {
        Turn turn = new Turn();
        int ciccio = 5;
        turn.setValidMoves(ciccio);
        assertEquals("turn is wrong", ciccio, turn.getValidMoves());
    }

    @Test
    public void GetWorkersTest()
    {
        Turn turn = new Turn();
        ArrayList<Worker> workers = new ArrayList<Worker>();
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        turn.setWorkers(workers);
        assertEquals("turn is wrong", workers, turn.getWorkers());
    }

    @Test
    public void GetSelectedCellTest()
    {
        Turn turn = new Turn();
        Cell cell = new Cell(0,0);
        turn.setSelectedCell(cell);
        assertEquals("SelectedCell is wrong", turn.getSelectedCell(), cell);
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
        board.getCell(1,0).setWorker(worker2);
        board.getCell(0,0).setWorker(worker);
        turn.setSelectedCell(board.getCell(0,0));
        board.getCell(0,1).setDome(true);
        assertEquals("Build out of board error", turn.Build(board, -1, -1,0),-1);
        assertEquals("Build success error", turn.Build(board,1,1,0), 1);
        assertEquals("Build too far error", turn.Build(board,4,4,0), -2);
        assertEquals("Build on worker error", turn.Build(board,1,0,0),-3);
        assertEquals("Build on dome error", turn.Build(board,0,1,0),-4);
        Demeter demeter = new Demeter();
        player.setGodPower(demeter);
        assertEquals("Build god repeat error", turn.Build(board,1,1,0), 2);
        demeter.ResetGod();
        board.getCell(1,1).setBuilding(1);
        player.setGodPower(god);
        assertEquals("Build dome for coverage", turn.Build(board,1,1,0), 1);

    }

    @Test
    public void MoveTest(){
        Turn turn = new Turn();
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        SetupMatch setupMatch = new SetupMatch();
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
        turn.setSelectedCell(board.getCell(0,0));
        assertEquals("Cell out of board error",turn.Move(board,-1,-1), -1);
        assertEquals("Move Success Error", turn.Move(board, 1, 1), 1 );
        turn.setSelectedCell(board.getCell(1,1));
        board.getCell(1,2).setBuilding(3);
        assertEquals("Cell is too high error", turn.Move(board,1,2), -3);
        assertEquals("Cell is Too far error", turn.Move(board, 4, 3), -2);
        assertEquals("Cell is occupied error", turn.Move(board,2,2),-4);
        board.getCell(2,1).setDome(true);
        assertEquals("Cell is occupied error", turn.Move(board,2,1),-4);
        Triton triton = new Triton();
        testWorker3.getPlayer().setGodPower(triton);
        assertEquals("Move special god repeat action", turn.Move(board, 0, 0), 2);


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
        ArrayList<Player> ActivePlayers = new ArrayList<Player>();
        ActivePlayers.add(player1);
        ActivePlayers.add(player2);
        assertEquals("Error lose false",turn.StartTurn(ActivePlayers, player1, board,0,0,false), 0);
        board.getCell(0,2).setBuilding(2);
        board.getCell(1,2).setBuilding(2);
        assertEquals("Error in Lose true",turn.StartTurn(ActivePlayers,player1,board,0,0,false),-1);
        ActivePlayers.remove(player2);
        assertEquals("Error Win True", turn.StartTurn(ActivePlayers, player1, board, 0,0,false), 1);
    }

    @Test
    public void CheckLostBuildTest() {
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
        turn.setSelectedCell(board.getCell(0,0));
        assertTrue("CheckLostBuild true Error", turn.CheckLostBuild(player1, board));
        turn.setSelectedCell(board.getCell(0,1));
        assertFalse("CheckLostBuild false error", turn.CheckLostBuild(player1, board));
        // special controls Zeus
        Zeus zeus = new Zeus();
        player1.setGodPower(zeus);
        turn.setSelectedCell(board.getCell(0,0));
        assertFalse("CheckLostBuild false: special Zeus Error", turn.CheckLostBuild(player1, board));
        board.getCell(0,0).setBuilding(3);
        assertTrue("CheckLostBuild True: special Zeus Error", turn.CheckLostBuild(player1,board));

    }

    @Test
    public void CheckWinConditionTest() {
        Turn turn = new Turn();
        Board board = new Board();
        Player player1 = new Player("Gino");
        God god = new God();
        player1.setGodPower(god);
        Worker worker1 = new Worker();
        worker1.setPlayer(player1);
        board.getCell(3,3).setWorker(worker1);
        assertNull("CheckWinCondition False error", turn.CheckWinCondition(board, player1));
        board.getCell(3,3).setBuilding(3);
        worker1.setLastMovement(1);
        assertEquals("CheckWinCondition True error", turn.CheckWinCondition(board, player1), player1);
        board.getCell(3,3).setBuilding(-1);
        Chrono chrono = new Chrono();
        player1.setGodPower(chrono);
        board.getCell(0,0).setBuilding(3);
        board.getCell(0,1).setBuilding(3);
        board.getCell(1,1).setBuilding(3);
        board.getCell(0,2).setBuilding(3);
        board.getCell(1,2).setBuilding(3);
        board.getCell(0,0).setDome(true);
        board.getCell(0,1).setDome(true);
        board.getCell(1,1).setDome(true);
        board.getCell(0,2).setDome(true);
        board.getCell(1,2).setDome(true);
        assertEquals("CheckWinCondition Chrono case True error", turn.CheckWinCondition(board, player1), player1);
        Pan pan = new Pan();
        player1.setGodPower(pan);
        worker1.setLastMovement(-2);
        assertEquals("CheckWinCondition Pan case True error", turn.CheckWinCondition(board, player1), player1);
    }
}
