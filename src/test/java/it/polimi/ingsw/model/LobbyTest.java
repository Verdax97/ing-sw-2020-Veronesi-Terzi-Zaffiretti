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
        ArrayList<String> players = new ArrayList<>();
        players.add("pino");
        players.add("pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        lobby.AddPlayer("pino");
        lobby.AddPlayer("pippo");
        lobby.AddPlayer("Magnifico Rettore Ferruccio Resta");
        assertEquals("player are wrong", players, lobby.getPlayers());
        lobby.setnPlayer(3);
        assertEquals("nPlayers wrong",3, lobby.getnPlayer());
    }
}
