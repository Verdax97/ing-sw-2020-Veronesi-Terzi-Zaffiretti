package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * Class Lobby
 */
public class Lobby
{
    private int nPlayer = 1;
    private final ArrayList<String> players = new ArrayList<>();

    /**
     * Method getnPlayer returns the nPlayer of this Lobby object.
     *
     * @return nPlayer of type int
     */
    public int getnPlayer() {
        return nPlayer;
    }

    /**
     * Method setnPlayer sets the nPlayer of this Lobby object.
     *
     * @param nPlayer of type int
     */
    public void setnPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    /**
     * Method addPlayer adds toAdd string to the arrayList players of this Lobby object then returns true.
     *
     * @param toAdd of type String
     * @return boolean value
     */
    public boolean AddPlayer(String toAdd)
    {
        this.players.add(toAdd);
        return true;
    }

    /**
     * Method getPlayers returns the array of string of players of this Lobby object.
     *
     * @return players of type ArrayList&lt;String&gt;
     */
    public ArrayList<String> getPlayers() {
        return players;
    }
}