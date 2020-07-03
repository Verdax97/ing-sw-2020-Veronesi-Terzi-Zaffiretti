package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;

/**
 * Class Minotaur implements Minotaur functionalities
 */
public class Minotaur extends ForceMovementGods
{
    /**
     * Constructor Minotaur creates a new Minotaur instance.
     */
    public Minotaur()
    {
        this.name = "Minotaur";
        this.description = "Your Move: Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";
    }

    /** @see it.polimi.ingsw.model.God#move(Board, Cell, int, int)  */
    @Override
    public int move(Board board, Cell selectedCell, int x, int y) {
        computeTargetPos(selectedCell, x, y);
        return forceMove(board, selectedCell, x, y);
    }

    /** @see it.polimi.ingsw.model.gods.MoveEnemyGods#computeTargetPos(Cell, int, int)  */
    @Override
    public void computeTargetPos(Cell selectedCell, int x, int y) {
        int dx = x - selectedCell.getPos()[0];
        int dy = y - selectedCell.getPos()[1];
        targetPosX = x + dx;
        targetPosY = y + dy;
    }
}
