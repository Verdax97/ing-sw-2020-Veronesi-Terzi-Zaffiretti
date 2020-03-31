package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class SetupMatch {

    private List<God> godList;
    private List<God> godPicked;
    private ArrayList<Player> players = new ArrayList<Player>();

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

    public void setPlayers(ArrayList<String> player)
    {
        for (String elem: player)
        {
            players.add(new Player(elem));
        }
    }
}
