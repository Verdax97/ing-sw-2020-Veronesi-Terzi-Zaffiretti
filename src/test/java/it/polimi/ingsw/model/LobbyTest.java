package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;

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
        lobby.addPlayer("pino");
        lobby.addPlayer("pippo");
        lobby.addPlayer("Magnifico Rettore Ferruccio Resta");
        Assertions.assertEquals(players, lobby.getPlayers());
        lobby.setnPlayer(3);
        Assertions.assertEquals(3, lobby.getnPlayer());
    }
}
