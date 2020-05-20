package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

public class MultipleActionGodTest {

    @Test
    public void checkUseTest(){
        MultipleActionGod gianni = new MultipleActionGod();
        gianni.use = 0;
        gianni.useLimit = 2;
        Assertions.assertEquals(2, gianni.CheckUse());
        gianni.use = 2;
        Assertions.assertEquals(1, gianni.CheckUse());
        gianni.ResetGod();
    }
}
