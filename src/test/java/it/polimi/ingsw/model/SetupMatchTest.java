package it.polimi.ingsw.model;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

public class SetupMatchTest
{
    @Test
    public void GetPlayersTest()
    {
        SetupMatch setup = new SetupMatch();
        ArrayList<String> players = new ArrayList<String>();
        players.add("pino");
        players.add("pippo");
        setup.setPlayers(players);
        assertEquals("player are wrong", players.toArray()[0], setup.getPlayers().get(0).getNickname());
        assertEquals("player are wrong", players.toArray()[1], setup.getPlayers().get(1).getNickname());
    }
}
