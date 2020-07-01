package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GameSaverTest {

    @Test
    public void saveAndLoadGameTest() throws IOException {
        ArrayList<String> players = new ArrayList<>();
        players.add("GinoTest");
        players.add("PinoTest");
        Match match = new Match(players);
        Lobby lobby = new Lobby();
        lobby.AddPlayer(players.get(0));
        lobby.AddPlayer(players.get(1));
        GameSaver.checkForGames(lobby);
        match.StartGame();
        MsgToServer msg = new MsgToServer(match.getPlayerTurn().getNickname(),2,-5,-5,-5);
        match.PickGod(msg);
        match.PickGod(msg);
        msg = new MsgToServer(match.getPlayerTurn().getNickname(),0,-5,-5,-5);
        match.SelectPlayerGod(msg);
        match.SelectPlayerGod(msg);
        msg = new MsgToServer(match.getPlayerTurn().getNickname(),0,0,1,1);
        match.PlaceWorker(msg);
        msg = new MsgToServer(match.getPlayerTurn().getNickname(),4,4,3,3);
        match.PlaceWorker(msg);
        match.getBoard().getCell(2,2).setDome(true);
        match.getBoard().getCell(2,4).setBuilding(3);
        match.getBoard().getCell(4,4).setBuilding(2);
        match.getBoard().getCell(0,0).getWorker().setLastMovement(1);
        match.getBoard().getCell(4,4).getWorker().setLastMovement(1);
        match.NextTurn();
        match.NextPlayer();
        GameSaver.saveGame(match);
        Assertions.assertTrue(true);
        match = GameSaver.loadGame();
        Assertions.assertEquals(0, match.getBoard().getCell(4,4).getWorker().getLastMovement());
    }

    @Test
    public void checkForGamesAndDeleteGameDataTest() throws IOException {
        ArrayList<String> players = new ArrayList<>();
        players.add("GinoTest");
        players.add("PinoTest");
        Lobby lobby = new Lobby();
        lobby.AddPlayer(players.get(0));
        lobby.AddPlayer(players.get(1));
        GameSaver.checkForGames(lobby);
        Assertions.assertTrue(GameSaver.checkForGames(lobby));
        GameSaver.deleteGameData();
    }
}
