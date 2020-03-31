package it.polimi.ingsw.model;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
public class TurnTest
{
    @Test
    public void GetTurnTest()
    {
        Turn turn = new Turn();
        int ciccio = 5;
        turn.setTurn(ciccio);
        assertEquals("turn is wrong", ciccio, turn.getTurn());
    }

    @Test
    public void GetValidMovesTest()
    {
        Turn turn = new Turn();
        int ciccio = 5;
        turn.setValidMoves(ciccio);
        assertEquals("turn is wrong", ciccio, turn.getValidMoves());
    }
    @Test
    public void GetWorkersTest()
    {
        Turn turn = new Turn();
        ArrayList<Worker> workers = new ArrayList<Worker>();
        workers.add(new Worker());
        workers.add(new Worker());
        workers.add(new Worker());
        turn.setWorkers(workers);
        assertEquals("turn is wrong", workers, turn.getWorkers());
    }
}
