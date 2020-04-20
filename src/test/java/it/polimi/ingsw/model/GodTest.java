package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.Athena;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GodTest{

    @Test
    public void getNameTest(){
        God athena = new Athena();
        assertEquals("Return value is wrong", "Athena", athena.getName());
    }

}
