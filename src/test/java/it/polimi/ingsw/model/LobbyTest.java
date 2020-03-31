package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class LobbyTest
{
    @Test
    public void GetPlayersTest()
    {
        Lobby lobby = new Lobby();
        ArrayList<String> players = new ArrayList<String>();
        players.add("pino");
        players.add("pippo");
        lobby.AddPlayer("pino");
        lobby.AddPlayer("pippo");
        assertEquals("player are wrong", players, lobby.getPlayers());
    }
}
