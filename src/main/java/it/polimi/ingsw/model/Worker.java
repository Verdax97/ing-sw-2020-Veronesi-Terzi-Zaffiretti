package it.polimi.ingsw.model;

public class Worker {
    private Player player;
    private int lastMovement;
    private boolean debuff = false;

    public void setPlayer(Player player)
    {
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
