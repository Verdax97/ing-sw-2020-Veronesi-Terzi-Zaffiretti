package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby
{
    private int nPlayer;
    private ArrayList<String> players;

    public void AddPlayer(String toAdd)
    {
        if (this.nPlayer == 3)
            return;
        this.players.add(toAdd);
        this.nPlayer = this.players.size();
    }

    public ArrayList<String> getPlayers() {
        return players;
    }
}