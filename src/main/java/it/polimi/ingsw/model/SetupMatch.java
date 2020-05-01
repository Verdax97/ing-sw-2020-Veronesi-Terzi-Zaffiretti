package it.polimi.ingsw.model;

import java.util.ArrayList;

public class SetupMatch {

    private ArrayList<God> godList;
    private ArrayList<God> godPicked;
    private ArrayList<Player> players = new ArrayList<Player>();


    public God PickGod(int i)
    {
        God god =godPicked.get(i);
        godPicked.remove(god);
        return god;
    }

    public ArrayList<God> getGodPicked()
    {
        return godPicked;
    }

    public ArrayList<God> getGodList()
    {
        return godList;
    }

    //for the initialization
    private void AddGod(God god) {
        this.godList.add(god);
    }

    //for the setup phase
    public void AddGodPicked(God god) {
        this.godPicked.add(god);
        this.godList.remove(god);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void SetPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }
    public void setPlayers(ArrayList<String> player)
    {
        for (String elem: player)
        {
            players.add(new Player(elem));
        }
    }
}
