package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.Charon;
import it.polimi.ingsw.model.gods.Hephaestus;
import it.polimi.ingsw.model.gods.Pan;
import it.polimi.ingsw.model.gods.Triton;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class MatchTest {

    @Test
    public void getLastActionTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        assertEquals("getLastAction error", 0, match.getLastAction());
    }

    @Test
    public void getPlayerTurnTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);

        match.StartGame();
        assertEquals("Getter error", players.get(1), match.getPlayerTurn().getNickname());
    }

    @Test
    public void getSetupTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        match.getSetup();
    }

    @Test
    public void getBoard(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        Board board = new Board();
        match.setBoard(board);
        assertEquals("getBoard error", board, match.getBoard());
    }

    @Test
    public void pickGodTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        match.StartGame();
        MsgToServer msg50 = new MsgToServer("Pino", 50, 0, 0, 0 );
        match.PickGod(msg50);
        assertEquals("getLastAction error", -1, match.getLastAction());
        MsgToServer msg1 = new MsgToServer("Pino", 1, 0, 0, 0 );
        match.PickGod(msg1);
        assertEquals("getLastAction error", 1, match.getLastAction());
        MsgToServer msg2 = new MsgToServer("Pino", 2, 0, 0, 0 );
        match.PickGod(msg2);
        assertEquals("getLastAction error", 1, match.getLastAction());
    }

    @Test
    public void PlaceWorkerTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);

        match.StartGame();
        God god1 = new God();
        God god2 = new God();
        match.getPlayers().get(0).setGodPower(god1);
        match.getPlayers().get(1).setGodPower(god2);
        MsgToServer msg0511 = new MsgToServer("Pino", 0, 5, 1, 1);
        match.PlaceWorker(msg0511);
        assertEquals("getLastAction error", -1, match.getLastAction());
        MsgToServer msg0000 = new MsgToServer("Pino", 0, 0, 0, 0);
        match.PlaceWorker(msg0000);
        assertEquals("getLastAction error", -1, match.getLastAction());
        MsgToServer msg0011 = new MsgToServer("Pino", 0, 0, 1, 1);
        match.PlaceWorker(msg0011);
        assertEquals("getLastAction error", 1, match.getLastAction());
        MsgToServer msg4433 = new MsgToServer("Pino", 4, 4, 3, 3);
        match.PlaceWorker(msg4433);
        assertEquals("getLastAction error", 2, match.getLastAction());
    }

    @Test
    public void SelectGodPlayerTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);

        match.StartGame();
        MsgToServer msg = new MsgToServer("Gino", 1, 0, 0, 0);
        match.PickGod(msg);
        match.PickGod(msg);
        MsgToServer msg1 = new MsgToServer("Gino", -1, 0, 0, 0);
        match.SelectPlayerGod(msg1);
        assertEquals("getLastActionError", -1, match.getLastAction());
        match.SelectPlayerGod(msg);
        assertEquals("getLastActionError", 1, match.getLastAction());
        match.SelectPlayerGod(msg);
        assertEquals("getLastActionError", 1, match.getLastAction());
    }

    @Test
    public void StartTurnTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);

        match.StartGame();
        MsgToServer msg = new MsgToServer("Pino", 0, 0, 2, 2);
        match.PlaceWorker(msg);
        match.getBoard().getCell(1, 0).setBuilding(3);
        match.getBoard().getCell(1, 1).setBuilding(3);
        match.getBoard().getCell(0, 1).setBuilding(3);
        match.StartTurn();
        assertEquals("getLastActionError", 0, match.getLastAction());
        match.getBoard().getCell(2, 2).setWorker(null);
        match.StartTurn();
        assertEquals("getLastActionError", -10, match.getLastAction());
        match.getBoard().getCell(0,0).getWorker().setPlayer(match.getPlayerTurn());
        match.StartTurn();
        assertEquals("getLastActionError", 1, match.getLastAction());
    }

    @Test
    public void MoveTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        MsgToServer msgPacket = new MsgToServer("Pino", 0, 0, 1, 1);
        Worker worker = new Worker();
        worker.setPlayer(match.getPlayerTurn());
        Worker worker1 = new Worker();
        worker1.setPlayer(match.getPlayers().get(0));
        Triton triton = new Triton();
        God god = new God();

        match.StartGame();
        match.getPlayerTurn().setGodPower(triton);
        match.getBoard().getCell(1, 1).setWorker(worker);
        match.getPlayers().get(0).setGodPower(god);
        match.getBoard().getCell(3, 3).setWorker(worker1);
        match.StartTurn();
        msgPacket = new MsgToServer("Pino", 1, 0, 1, 0);
        match.Move(msgPacket);
        assertEquals("getLastActionError", 2, match.getLastAction());
        msgPacket = new MsgToServer("Pino", 0, 0, 0, 1);
        match.Move(msgPacket);
        assertEquals("getLastActionError", 1, match.getLastAction());
        msgPacket = new MsgToServer("Pino", 1,0,1,1);
        match.Move(msgPacket);
        assertEquals("getLastActionError", 1, match.getLastAction());
        triton.ResetGod();
        msgPacket = new MsgToServer("Pino", 1,0,5,5);
        match.Move(msgPacket);
        assertEquals("getLastActionError", -1, match.getLastAction());
        msgPacket = new MsgToServer("Pino", 0,0,5,5);
        match.Move(msgPacket);
        assertEquals("getLastActionError", -1, match.getLastAction());
        match.getBoard().getCell(0,1).setBuilding(3);
        match.getBoard().getCell(1,1).setBuilding(2);
        msgPacket = new MsgToServer("Pino", 1,0,0,1);
        match.Move(msgPacket);
        assertEquals("getLastActionError", 10, match.getLastAction());
    }

    @Test
    public void BuildTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);

        match.StartGame();
        MsgToServer msgPacket = new MsgToServer("Pino", 0, 0, 0, 0);
        Worker worker = new Worker();
        worker.setPlayer(match.getPlayerTurn());
        Worker worker1 = new Worker();
        worker1.setPlayer(match.getPlayers().get(0));
        Hephaestus hephaestus = new Hephaestus();
        God god = new God();
        match.getPlayerTurn().setGodPower(hephaestus);
        match.getBoard().getCell(1, 1).setWorker(worker);
        match.getPlayers().get(0).setGodPower(god);
        match.getBoard().getCell(3,3).setWorker(worker1);
        match.StartTurn();
        match.Build(msgPacket);
        assertEquals("getLastActionError", 2, match.getLastAction());
        match.Build(msgPacket);
        assertEquals("getLastActionError", 1, match.getLastAction());
        msgPacket = new MsgToServer("Pino", 1,0,5,5);
        match.Build(msgPacket);
        assertEquals("getLastActionError", -1, match.getLastAction());
        msgPacket = new MsgToServer("Pino", 0,0,5,5);
        match.Build(msgPacket);
        assertEquals("getLastActionError", -1, match.getLastAction());
        match.getBoard().getCell(3,3).setWorker(null);
        msgPacket = new MsgToServer("Pino", 0,0,0,0);
        match.getBoard().getCell(0,0).setBuilding(-1);
        match.Move(msgPacket);
        match.getBoard().getCell(0,0).setBuilding(2);
        msgPacket = new MsgToServer("Pino", 0,0,1,1);
        Pan pan = new Pan();
        match.getPlayerTurn().setGodPower(pan);
        match.getBoard().getCell(0,0).getWorker().setLastMovement(-2);
        match.Build(msgPacket);
        assertEquals("getLastActionError", 10, match.getLastAction());
        match.getBoard().getCell(1,0).setDome(true);
        match.getBoard().getCell(1,1).setDome(true);
        match.getBoard().getCell(0,1).setDome(true);
        match.Build(msgPacket);
        assertEquals("getLastActionError", -10, match.getLastAction());
    }

    @Test
    public void PacketTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        match.getBoard().getCell(0, 0).setDome(true);

        match.SendPacket("Gino", "Test", "TestAlt", null);
        match.SendPacket("Gino", "Test", "TestAlt", match.getBoard());
    }
}
