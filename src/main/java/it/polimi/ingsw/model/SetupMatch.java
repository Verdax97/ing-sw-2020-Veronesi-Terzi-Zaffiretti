package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.*;

import java.util.ArrayList;

public class SetupMatch {

    private ArrayList<God> godList;
    private ArrayList<God> godPicked = new ArrayList<God>();
    private ArrayList<Player> players = new ArrayList<Player>();

    public SetupMatch() {
        godList = new ArrayList<God>();
        this.AddGod(new Apollo());
        this.AddGod(new Artemis());
        this.AddGod(new Athena());
        this.AddGod(new Atlas());
        this.AddGod(new Charon());
        this.AddGod(new Chrono());
        this.AddGod(new Demeter());
        this.AddGod(new Hephaestus());
        this.AddGod(new Hestia());
        this.AddGod(new Minotaur());
        this.AddGod(new Pan());
        this.AddGod(new Prometheus());
        this.AddGod(new Triton());
        this.AddGod(new Zeus());
    }


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

    public void CreatePlayersFromNickname(ArrayList<String> player) {
        for (String elem : player) {
            players.add(new Player(elem));
        }
    }
}
