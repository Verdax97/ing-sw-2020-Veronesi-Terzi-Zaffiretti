package it.polimi.ingsw.model;

import java.io.Serializable;

public class Worker implements Serializable {
    private Player player;
    private int lastMovement = 0;
    private boolean debuff = false;


    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer()
    {
        return this.player;
    }

    public int getLastMovement() { return lastMovement; }

    public void setLastMovement(int lastMovement) { this.lastMovement = lastMovement; }

    public boolean isDebuff() {
        return debuff;
    }

    public void setDebuff(boolean debuff) {
        this.debuff = debuff;
    }
}
