package it.polimi.ingsw.model;

import java.util.List;

public class SetupMatch {

    private List<God> godList;
    private List<God> godPicked;
    private List<Player> players;

    public void PickGod() { }

    public void AddGod(God god) {
        this.godList.add(god);
    }

    public void AddGodPicked(God god) {
        this.godPicked.add(god);
    }

    public List<Player> GetPlayers() {
        return players;
    }

    public void setPlayers(@org.jetbrains.annotations.NotNull List<String> players)
    {
        for (String player:players)
        {
            this.players.add(new Player(player));
        }
    }
}
