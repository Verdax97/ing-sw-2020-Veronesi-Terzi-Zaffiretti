package it.polimi.ingsw;

public class ForceMovementGods extends God
{
    private int targetPosX;
    private int targetPosY;
    public int GetTargetPosX()
    {
        return targetPosX;
    }

    public void SetTargetPosX(int targetPosX) {
        this.targetPosX = targetPosX;
    }

    public int GetTargetPosY() {
        return targetPosY;
    }

    public void SetTargetPosY(int targetPosY) {
        this.targetPosY = targetPosY;
    }
}
