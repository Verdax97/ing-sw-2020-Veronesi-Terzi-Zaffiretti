package it.polimi.ingsw.model;

public class ForceMovementGods extends God
{
    private int targetPosX;
    private int targetPosY;
    public int getTargetPosX()
    {
        return targetPosX;
    }

    public void setTargetPosX(int targetPosX) {
        this.targetPosX = targetPosX;
    }

    public int getTargetPosY() {
        return targetPosY;
    }

    public void setTargetPosY(int targetPosY) {
        this.targetPosY = targetPosY;
    }
}
