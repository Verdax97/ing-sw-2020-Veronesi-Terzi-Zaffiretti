package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class StateTest {
    @Test
    public  void TestStates() {
        Assertions.assertEquals("LOBBY", State.LOBBY.name());
        Assertions.assertEquals("MOVE", State.MOVE.name());
        Assertions.assertEquals("PLACEWORKERS", State.PLACEWORKERS.name());
        Assertions.assertEquals("SETUP", State.SETUP.name());
        Assertions.assertEquals("STARTTURN", State.STARTTURN.name());
        Assertions.assertEquals("BUILD", State.BUILD.name());
        Assertions.assertEquals("ENDMATCH", State.ENDMATCH.name());
        Assertions.assertEquals("SELECT", State.SELECT.name());
    }

}