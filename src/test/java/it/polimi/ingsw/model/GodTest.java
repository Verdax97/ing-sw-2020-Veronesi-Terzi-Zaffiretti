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

    @Test
    public void getDescriptionTest(){
        God athena = new Athena();
        assertEquals("Return value is wrong", "Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", athena.getDescription());
    }

    @Test
    public void godTest() {
        Board board = new Board();
        Player player = new Player("Gino");
        God god = new God();
        Turn turn = new Turn();
        turn.setSelectedCell(board.getCell(0,0));
        god.ResetGod();
        assertEquals("std Turn Error", god.PlayerTurn(board, player, turn.getSelectedCell(), 1,1), 0 );
    }

}
