package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MultipleActionGodTest {

    @Test
    public void checkUseTest(){
        MultipleActionGod gianni = new MultipleActionGod();
        gianni.use = 0;
        gianni.useLimit = 2;
        assertEquals("Return value is wrong", 2, gianni.CheckUse());
        gianni.use = 2;
        assertEquals("Return value is wrong", 1, gianni.CheckUse());
    }
}
