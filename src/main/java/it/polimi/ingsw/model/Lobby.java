package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby
{
    private int nPlayer = 1;
    private ArrayList<String> players = new ArrayList<String>();

    public int getnPlayer() {
        return nPlayer;
    }

    public void setnPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    public boolean AddPlayer(String toAdd)
    {
        if (this.nPlayer == 3)
            return false;
        this.players.add(toAdd);
        return true;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }
}