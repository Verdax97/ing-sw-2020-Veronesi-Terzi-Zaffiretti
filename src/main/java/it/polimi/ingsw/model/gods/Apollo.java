package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;

/**
 * Class Apollo implements Artemis functionalities
 */
public class Apollo extends ForceMovementGods {
    /**
     * Constructor Apollo creates a new Apollo instance.
     */
    public Apollo() {
        this.name = "Apollo";
        this.description = "Your Move: Your Worker may move into an opponent Worker’s space by forcing their Worker to the space yours just vacated.";
    }

    /**
     * @see it.polimi.ingsw.model.God#Move(Board, Cell, int, int)
     */
    @Override
    public int Move(Board board, Cell selectedCell, int x, int y) {
        targetPosX = selectedCell.getPos()[0];
        targetPosY = selectedCell.getPos()[1];
        return ForceMove(board, selectedCell, x, y);
    }
}
