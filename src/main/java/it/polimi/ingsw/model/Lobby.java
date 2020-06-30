package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Lobby
 */
public class Lobby
{
    private int nPlayer = 1;
    private final ArrayList<String> players = new ArrayList<>();

    /**
     * Gets player.
     *
     * @return the player
     */
    public int getnPlayer() {
        return nPlayer;
    }

    /**
     * Sets player.
     *
     * @param nPlayer the n player
     */
    public void setnPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    /**
     * Add player boolean.
     *
     * @param toAdd the to add
     * @return the boolean
     */
    public boolean AddPlayer(String toAdd)
    {
        this.players.add(toAdd);
        return true;
    }

    /**
     * Gets players.
     *
     * @return the players
     */
    public ArrayList<String> getPlayers() {
        return players;
    }
}