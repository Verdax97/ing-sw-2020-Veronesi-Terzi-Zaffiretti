package it.polimi.ingsw.model;

import java.util.List;

public class Lobby
{
    private int nPlayer;
    private List<String> players;

    public void AddPlayer(String toAdd)
    {
        if (this.nPlayer == 3)
            return;
        this.players.add(toAdd);
        this.nPlayer = this.players.size();
    }

    public List<String> getPlayers() {
        return players;
    }
}