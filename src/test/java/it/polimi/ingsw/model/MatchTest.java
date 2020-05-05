package it.polimi.ingsw.model;

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
        MsgPacket msgPacket = new MsgPacket("Gino", "", "", match.getBoard(), match.getPlayers());

    }
}
