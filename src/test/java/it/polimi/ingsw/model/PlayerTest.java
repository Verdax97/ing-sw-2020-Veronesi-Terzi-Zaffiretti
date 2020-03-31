package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void getGodPowerTest(){
        Player playerTest = new Player("Pino");
        God godPower = new God();
        playerTest.setGodPower(godPower);
        assertEquals("GodPower is wrong", godPower, playerTest.getGodPower());
    }

    @Test
    public void getNickNameTest(){
        String name = "Pino";
        String nameTest = "Gianni";
        Player playerTest = new Player(name);
        playerTest.setNickname(nameTest);
        assertEquals("Nickname is wrong", nameTest, playerTest.getNickname());
    }


}
