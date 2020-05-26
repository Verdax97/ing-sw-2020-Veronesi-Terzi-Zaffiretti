package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameSaverTest {

    @Test
    public void checkForGames(){
        ArrayList<String> players = new ArrayList<>();
        players.add("GinoTest");
        players.add("PinoTest");
        Lobby lobby = new Lobby();
        lobby.AddPlayer(players.get(0));
        lobby.AddPlayer(players.get(1));
        Assertions.assertFalse(GameSaver.checkForGames(lobby));
        Assertions.assertTrue(GameSaver.checkForGames(lobby));
    }

    @Test
    public void saveGame(){
        ArrayList<String> players = new ArrayList<>();
        players.add("GinoTest");
        players.add("PinoTest");
        Match match = new Match(players);
        Lobby lobby = new Lobby();
        lobby.AddPlayer(players.get(0));
        lobby.AddPlayer(players.get(1));
        GameSaver.checkForGames(lobby);
        match.StartGame();
        MsgToServer msg = new MsgToServer(match.getPlayerTurn().getNickname(),0,-5,-5,-5);
        match.PickGod(msg);
        match.PickGod(msg);
        match.SelectPlayerGod(msg);
        match.SelectPlayerGod(msg);
        msg = new MsgToServer(match.getPlayerTurn().getNickname(),0,0,1,1);
        match.PlaceWorker(msg);
        msg = new MsgToServer(match.getPlayerTurn().getNickname(),4,4,3,3);
        match.PlaceWorker(msg);
        GameSaver.saveGame(match);
        Assertions.assertTrue(true);
    }


}
