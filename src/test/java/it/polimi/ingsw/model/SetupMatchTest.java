package it.polimi.ingsw.model;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SetupMatchTest
{
    @Test
    public void GetPlayersTest() {
        SetupMatch setup = new SetupMatch();
        ArrayList<String> players = new ArrayList<>();
        players.add("pino");
        players.add("pippo");
        setup.CreatePlayersFromNickname(players);
        Assertions.assertEquals(players.get(0), setup.getPlayers().get(0).getNickname());
        Assertions.assertEquals(players.get(1), setup.getPlayers().get(1).getNickname());
    }

    @Test
    public void getGodPicked() {
        // First Look, NOT FINISHED
        ArrayList<Player> players = new ArrayList<>();
        SetupMatch setupMatch = new SetupMatch();
        setupMatch.SetPlayers(players);
        setupMatch.AddGodPicked(setupMatch.getGodList().get(0));
        Assertions.assertEquals("Apollo", setupMatch.getGodPicked().get(0).name);
        Assertions.assertEquals("Apollo", setupMatch.PickGod(0).name);
    }
}
