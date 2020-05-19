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
        ArrayList<String> players = new ArrayList<>();
        players.add("pino");
        players.add("pippo");
        setup.CreatePlayersFromNickname(players);
        assertEquals("player are wrong", players.get(0), setup.getPlayers().get(0).getNickname());
        assertEquals("player are wrong", players.get(1), setup.getPlayers().get(1).getNickname());
    }

    @Test
    public void getGodPicked(){
        // First Look, NOT FINISHED
        ArrayList<Player> players = new ArrayList<>();
        SetupMatch setupMatch = new SetupMatch();
        setupMatch.SetPlayers(players);
        setupMatch.AddGodPicked(setupMatch.getGodList().get(0));
        assertEquals("SetupMatch Error", "Apollo", setupMatch.getGodPicked().get(0).name);
        assertEquals("SetupMatch Error", "Apollo", setupMatch.PickGod(0).name);
    }
}
