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
        MsgPacket msg = new MsgPacket("Gino","50","", match.getBoard(), match.getPlayers());
        match.PickGod(msg);
        assertEquals("getLastAction error", -1, match.getLastAction());
        msg.msg = "1";
        match.PickGod(msg);
        assertEquals("getLastAction error", 1, match.getLastAction());
        msg.msg = "2";
        match.PickGod(msg);
        assertEquals("getLastAction error", 1, match.getLastAction());
    }

    @Test
    public void PlaceWorkerTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        God god1 = new God();
        God god2 = new God();
        match.getPlayers().get(0).setGodPower(god1);
        match.getPlayers().get(1).setGodPower(god2);
        MsgPacket msgPacket = new MsgPacket("Pino", "0 5 1 1","",match.getBoard(),match.getPlayers());
        match.PlaceWorker(msgPacket);
        assertEquals("getLastAction error", -1, match.getLastAction());
        msgPacket.msg = "0 0 0 0";
        assertEquals("getLastAction error", -1, match.getLastAction());
        msgPacket.msg = "0 0 1 1";
        match.PlaceWorker(msgPacket);
        assertEquals("getLastAction error", 1, match.getLastAction());
        msgPacket.nickname = "Gino";
        msgPacket.msg = "4 4 3 3";
        match.PlaceWorker(msgPacket);
        assertEquals("getLastAction error", 2, match.getLastAction());
    }

    @Test
    public void SelectGodPlayerTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        MsgPacket msgPacket = new MsgPacket("Gino", "1", "", match.getBoard(), match.getPlayers());
        match.PickGod(msgPacket);
        match.PickGod(msgPacket);
        msgPacket.msg = "-1";
        match.SelectPlayerGod(msgPacket);
        assertEquals("getLastActionError", -1, match.getLastAction());
        msgPacket.msg = "1";
        match.SelectPlayerGod(msgPacket);
        assertEquals("getLastActionError", 1, match.getLastAction());
        match.SelectPlayerGod(msgPacket);
        assertEquals("getLastActionError", 1, match.getLastAction());
    }

    @Test
    public void StartTurnTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        MsgPacket msgPacket = new MsgPacket("Gino", "0 1 1 1 0", "", match.getBoard(), match.getPlayers());
        Worker worker1 = new Worker();
        worker1.setPlayer(match.getPlayerTurn());
        God god = new God();
        God god2 = new God();
        match.getPlayerTurn().setGodPower(god);
        match.getPlayers().get(0).setGodPower(god2);
        match.getBoard().getCell(0,0).setWorker(worker1);
        match.StartTurn(msgPacket);
        assertEquals("getLastActionError", -2, match.getLastAction());
        msgPacket.msg = "0 0 1 1 0";
        match.StartTurn(msgPacket);
        assertEquals("getLastActionError", 0, match.getLastAction());
        Charon charon = new Charon();
        match.getPlayerTurn().setGodPower(charon);
        msgPacket.msg = "0 0 1 1 1";
        Worker worker2 = new Worker();
        match.getBoard().getCell(1,1).setWorker(worker2);
        worker2.setPlayer(match.getPlayers().get(0));
        match.StartTurn(msgPacket);
        assertEquals("getLastActionError", -7, match.getLastAction());
        msgPacket.msg = "0 0 1 1 0";
        match.getBoard().getCell(1,0).setBuilding(3);
        match.getBoard().getCell(1,1).setBuilding(3);
        match.getBoard().getCell(0,1).setBuilding(3);
        match.StartTurn(msgPacket);
        assertEquals("getLastActionError", -10, match.getLastAction());
        worker1.setPlayer(match.getPlayerTurn());
        match.getBoard().getCell(0,0).setWorker(worker1);
        match.StartTurn(msgPacket);
        assertEquals("getLastActionError", 1, match.getLastAction());
    }

    @Test
    public void MoveTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        MsgPacket msgPacket = new MsgPacket("Gino", "1 1 0 0 0", "", match.getBoard(), match.getPlayers());
        Worker worker = new Worker();
        worker.setPlayer(match.getPlayerTurn());
        Worker worker1 = new Worker();
        worker1.setPlayer(match.getPlayers().get(0));
        Triton triton = new Triton();
        God god = new God();
        match.getPlayerTurn().setGodPower(triton);
        match.getBoard().getCell(1,1).setWorker(worker);
        match.getPlayers().get(0).setGodPower(god);
        match.getBoard().getCell(3,3).setWorker(worker1);
        match.StartTurn(msgPacket);
        msgPacket.msg = "1 0 1";
        match.Move(msgPacket);
        assertEquals("getLastActionError", 2, match.getLastAction());
        msgPacket.msg = "0 1 0";
        match.Move(msgPacket);
        assertEquals("getLastActionError", 1, match.getLastAction());
        msgPacket.msg = "1 1 1";
        match.Move(msgPacket);
        assertEquals("getLastActionError", 1, match.getLastAction());
        triton.ResetGod();
        msgPacket.msg = "5 5 1";
        match.Move(msgPacket);
        assertEquals("getLastActionError", -1, match.getLastAction());
        msgPacket.msg = "5 5 0";
        match.Move(msgPacket);
        assertEquals("getLastActionError", -1, match.getLastAction());
        match.getBoard().getCell(0,1).setBuilding(3);
        match.getBoard().getCell(1,1).setBuilding(2);
        msgPacket.msg = "0 1 0";
        match.Move(msgPacket);
        assertEquals("getLastActionError", 10, match.getLastAction());
    }

    @Test
    public void BuildTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        MsgPacket msgPacket = new MsgPacket("Gino", "1 1 0 0 0", "", match.getBoard(), match.getPlayers());
        Worker worker = new Worker();
        worker.setPlayer(match.getPlayerTurn());
        Worker worker1 = new Worker();
        worker1.setPlayer(match.getPlayers().get(0));
        Hephaestus hephaestus = new Hephaestus();
        God god = new God();
        match.getPlayerTurn().setGodPower(hephaestus);
        match.getBoard().getCell(1,1).setWorker(worker);
        match.getPlayers().get(0).setGodPower(god);
        match.getBoard().getCell(3,3).setWorker(worker1);
        match.StartTurn(msgPacket);
        msgPacket.msg = "0 0 0 0";
        match.Build(msgPacket);
        assertEquals("getLastActionError", 2, match.getLastAction());
        msgPacket.msg = "0 0 0 0";
        match.Build(msgPacket);
        assertEquals("getLastActionError", 1, match.getLastAction());
        msgPacket.msg = "5 5 1 0";
        match.Build(msgPacket);
        assertEquals("getLastActionError", -1, match.getLastAction());
        msgPacket.msg = "5 5 0 0";
        match.Build(msgPacket);
        assertEquals("getLastActionError", -1, match.getLastAction());
        match.getBoard().getCell(3,3).setWorker(null);
        msgPacket.msg = "0 0 0";
        match.getBoard().getCell(0,0).setBuilding(-1);
        match.Move(msgPacket);
        match.getBoard().getCell(0,0).setBuilding(2);
        msgPacket.msg = "1 1 0 0";
        Pan pan = new Pan();
        match.getPlayerTurn().setGodPower(pan);
        match.getBoard().getCell(0,0).getWorker().setLastMovement(-2);
        match.Build(msgPacket);
        assertEquals("getLastActionError", 10, match.getLastAction());
        match.getBoard().getCell(1,0).setDome(true);
        match.getBoard().getCell(1,1).setDome(true);
        match.getBoard().getCell(0,1).setDome(true);
        msgPacket.msg = "1 1 0 0";
        match.Build(msgPacket);
        assertEquals("getLastActionError", -10, match.getLastAction());
    }

    @Test
    public void PacketTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        match.SendPacket("Gino", "Test", "TestAlt", match.getBoard());
    }
}
