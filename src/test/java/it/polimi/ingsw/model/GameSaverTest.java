package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class GameSaverTest {

    @Test
    public void checkForGames(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        Lobby lobby = new Lobby();
        lobby.AddPlayer(players.get(0));
        lobby.AddPlayer(players.get(1));
        GameSaver.checkForGames(lobby);
    }
}
