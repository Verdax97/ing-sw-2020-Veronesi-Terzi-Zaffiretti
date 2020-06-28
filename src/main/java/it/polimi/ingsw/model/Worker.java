package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * The type Worker.
 */
public class Worker implements Serializable {
    private Player player;
    private int lastMovement = 0;
    private boolean debuff = false;


    /**
     * Sets player.
     *
     * @param player the player
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public Player getPlayer()
    {
        return this.player;
    }

    /**
     * Gets last movement.
     *
     * @return the last movement
     */
    public int getLastMovement() { return lastMovement; }

    /**
     * Sets last movement.
     *
     * @param lastMovement the last movement
     */
    public void setLastMovement(int lastMovement) { this.lastMovement = lastMovement; }

    /**
     * Is debuff boolean.
     *
     * @return the boolean
     */
    public boolean isDebuff() {
        return debuff;
    }

    /**
     * Sets debuff.
     *
     * @param debuff the debuff
     */
    public void setDebuff(boolean debuff) {
        this.debuff = debuff;
    }
}
