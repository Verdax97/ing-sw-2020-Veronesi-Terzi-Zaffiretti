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
        match.getBoard().getCell(0,0);
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
}
