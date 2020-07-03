package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.Athena;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;


public class GodTest {

    @Test
    public void getNameTest() {
        God athena = new Athena();
        Assertions.assertEquals("Athena", athena.getName());
    }

    @Test
    public void getDescriptionTest() {
        God athena = new Athena();
        Assertions.assertEquals("Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.", athena.getDescription());
    }

    @Test
    public void godTest() {
        Board board = new Board();
        God god = new God();
        Turn turn = new Turn();
        turn.setSelectedCell(board.getCell(0, 0));
        god.resetGod();
        Assertions.assertEquals(1, god.playerTurn(board, turn.getSelectedCell(), 1, 1));
    }

    @Test
    public void checkPlayerTurnTest() {
        Board board = new Board();
        God god = new God();
        Turn turn = new Turn();
        turn.setSelectedCell(board.getCell(0, 0));
        god.resetGod();
        Assertions.assertEquals(0, god.checkPlayerTurn(board, turn.getSelectedCell(), 1, 1));
    }

}
