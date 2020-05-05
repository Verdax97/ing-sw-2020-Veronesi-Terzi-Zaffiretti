package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.State;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StateTest {
    @Test
    public  void TestStates()
    {
        assertEquals("LOBBY", State.LOBBY.name());
        assertEquals("MOVE", State.MOVE.name());
        assertEquals("PLACEWORKERS", State.PLACEWORKERS.name());
        assertEquals("SETUP", State.SETUP.name());
        assertEquals("STARTTURN", State.STARTTURN.name());
        assertEquals("BUILD", State.BUILD.name());
        assertEquals("ENDMATCH", State.ENDMATCH.name());
        assertEquals("SELECT", State.SELECT.name());
    }

}