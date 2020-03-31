package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ForceMovementGodsTest {
    @Test
    public void GetTargetPosXTest()
    {
        ForceMovementGods forceMovementGods = new ForceMovementGods();
        int x = 5;
        forceMovementGods.setTargetPosX(x);
        assertEquals("PosX is Wrong", forceMovementGods.getTargetPosX(), x);
    }
    @Test
    public void GetTargetPosYTest()
    {
        ForceMovementGods forceMovementGods = new ForceMovementGods();
        int y = 5;
        forceMovementGods.setTargetPosX(y);
        assertEquals("PosY is Wrong", forceMovementGods.getTargetPosX(), y);
    }

}
