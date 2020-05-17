package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby
{
    private int nPlayer = 1;
    private final ArrayList<String> players = new ArrayList<>();

    public int getnPlayer() {
        return nPlayer;
    }

    public void setnPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    public boolean AddPlayer(String toAdd)
    {
        this.players.add(toAdd);
        return true;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }
}