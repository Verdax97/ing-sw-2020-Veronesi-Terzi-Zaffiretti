package it.polimi.ingsw.model;

import java.util.List;

public class SetupMatch {

    public List<God> godList;
    public List<God> godPicked;
    public List<Player> players;

    public void PickGod() { }

    public void CreateTable() { }

    public void AddGod(God god) {
        this.godList.add(god);
    }

    public void AddGodPicked(God god) {
        this.godPicked.add(god);
    }
}
