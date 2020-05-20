package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;


import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void getGodPowerTest(){
        Player playerTest = new Player("Pino");
        God godPower = new God();
        playerTest.setGodPower(godPower);
        Assertions.assertEquals(godPower, playerTest.getGodPower());
    }

    @Test
    public void getNickNameTest(){
        String name = "Pino";
        String nameTest = "Gianni";
        Player playerTest = new Player(name);
        playerTest.setNickname(nameTest);
        Assertions.assertEquals(nameTest, playerTest.getNickname());
    }

    @Test
    public void getActiveTest() {
        Player playerTest = new Player("Pino");
        playerTest.setActive(true);
        Assertions.assertTrue(playerTest.getActive());
    }

}
