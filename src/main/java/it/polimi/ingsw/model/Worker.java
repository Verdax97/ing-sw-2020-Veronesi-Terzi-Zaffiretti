package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Worker contains information about a specific worker
 */
public class Worker implements Serializable {

    private Player player;
    private int lastMovement = 0;
    private boolean debuff = false;

    /**
     * Method setPlayer sets the player of this Workers object.
     *
     * @param player of type Player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Method getPlayer returns the player of this Worker object.
     *
     * @return player of type Player
     */
    public Player getPlayer()
    {
        return this.player;
    }

    /**
     * Method getLastMovement returns the lastMovement of this Worker object.
     *
     * @return lastMovement of type int
     */
    public int getLastMovement() { return lastMovement; }

    /**
     * Method setLastMovement sets the lastMovement of this Worker object.
     *
     * @param lastMovement of type int
     */
    public void setLastMovement(int lastMovement) { this.lastMovement = lastMovement; }

    /**
     * Method isDebuff returns the debuff of this Worker object.
     *
     * @return the boolean
     */
    public boolean isDebuff() {
        return debuff;
    }

    /**
     * Method setDebuff sets the debuff of this Worker object.
     *
     * @param debuff the debuff
     */
    public void setDebuff(boolean debuff) {
        this.debuff = debuff;
    }
}
