package it.polimi.ingsw.model;

import it.polimi.ingsw.view.ServerView;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
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
    public void MoveTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Pino");
        players.add("Pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        Match match = new Match(players);
        SetupMatch setupMatch = new SetupMatch();
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
}
