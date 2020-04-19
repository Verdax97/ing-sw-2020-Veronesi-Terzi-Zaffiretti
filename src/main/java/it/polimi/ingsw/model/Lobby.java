package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby
{
    public int getnPlayer() {
        return nPlayer;
    }

    public void setnPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    private int nPlayer = 1;
    private ArrayList<String> players = new ArrayList<String>();

    public void AddPlayer(String toAdd)
    {
        if (this.nPlayer == 3)
            return;
        this.players.add(toAdd);
    }

    public ArrayList<String> getPlayers() {
        return players;
    }
}