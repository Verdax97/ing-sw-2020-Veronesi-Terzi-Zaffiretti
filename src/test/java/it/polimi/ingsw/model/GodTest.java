package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.Athena;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GodTest {

    @Test
    public void getNameTest() {
        God athena = new Athena();
        assertEquals("Return value is wrong", "Athena", athena.getName());
    }

    @Test
    public void getImgTest() {
        God god = new God();
        assertNull("Return value is wrong", god.getImg());
    }

    @Test
    public void getDescriptionTest() {
        God athena = new Athena();
        assertEquals("Return value is wrong", "Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", athena.getDescription());
    }

    @Test
    public void godTest() {
        Board board = new Board();
        God god = new God();
        Turn turn = new Turn();
        turn.setSelectedCell(board.getCell(0, 0));
        god.ResetGod();
        assertEquals("std Turn Error", 1, god.PlayerTurn(board, turn.getSelectedCell(), 1, 1));
    }

    @Test
    public void checkPlayerTurnTest() {
        Board board = new Board();
        God god = new God();
        Turn turn = new Turn();
        turn.setSelectedCell(board.getCell(0, 0));
        god.ResetGod();
        assertEquals("std Turn Error", 0, god.CheckPlayerTurn(board, turn.getSelectedCell(), 1, 1));
    }

}
