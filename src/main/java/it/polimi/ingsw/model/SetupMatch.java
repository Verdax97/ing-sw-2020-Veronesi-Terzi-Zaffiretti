package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class SetupMatch {

    private List<God> godList;
    private List<God> godPicked;
    private ArrayList<Player> players;

    public void PickGod() { }

    public void AddGod(God god) {
        this.godList.add(god);
    }

    public void AddGodPicked(God god) {
        this.godPicked.add(god);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(@org.jetbrains.annotations.NotNull ArrayList<String> players)
    {
        for (String player:players)
        {
            this.players.add(new Player(player));
        }
    }
}
