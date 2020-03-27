package it.polimi.ingsw.model;

public class Worker {
    private Player player;
    private int lastMovement;

    public void SetPlayer(Player player)
    {
        this.player = player;
    }

    public Player GetPlayer()
    {
        return this.player;
    }

    public int getLastMovement() { return lastMovement; }

    public void setLastMovement(int lastMovement) { this.lastMovement = lastMovement; }
}
