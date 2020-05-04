package it.polimi.ingsw.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LobbyTest
{
    @Test
    public void GetPlayersTest()
    {
        Lobby lobby = new Lobby();
        ArrayList<String> players = new ArrayList<String>();
        players.add("pino");
        players.add("pippo");
        players.add("Magnifico Rettore Ferruccio Resta");
        lobby.AddPlayer("pino");
        lobby.AddPlayer("pippo");
        lobby.AddPlayer("Magnifico Rettore Ferruccio Resta");
        assertEquals("player are wrong", players, lobby.getPlayers());
        lobby.setnPlayer(3);
        players.add("Rifiutato");
        assertFalse("return false error", lobby.AddPlayer("Rifiutato"));
        assertEquals("nPlayers wrong",3, lobby.getnPlayer());
    }
}
