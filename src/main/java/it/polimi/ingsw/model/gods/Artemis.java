package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

/**
 * Class Artemis implements Artemis functionalities
 */
public class Artemis extends MultipleActionGod {
    private int lastX;
    private int lastY;

    /**
     * Constructor Artemis creates a new Artemis instance.
     */
    public Artemis() {
        this.name = "Artemis";
        this.description = "Your move: Your worker may move one additional time, but not back to the space it started on.";
        this.useLimit = 2;
    }

    /**
     * @see it.polimi.ingsw.model.God#Move(Board, Cell, int, int)
     */
    @Override
    public int Move(Board board, Cell selectedCell, int x, int y) {
        int moved = CheckMove(board, selectedCell, x, y);
        if (moved > 0) {
            if (use == 0) {
                lastX = selectedCell.getPos()[0];
                lastY = selectedCell.getPos()[1];
            }
            board.getCell(x, y).setWorker(selectedCell.getWorker());
            selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
            selectedCell.setWorker(null);
            use++;
            return CheckUse();
        }
        return moved;
    }


    /**
     * @see it.polimi.ingsw.model.God#CheckMove(Board, Cell, int, int)
     */
    @Override
    public int CheckMove(Board board, Cell selectedCell, int x, int y) {
        int ret = super.CheckMove(board, selectedCell, x, y);
        if (ret < 0)
            return ret;

        if (use == 0)
            return CheckUse();

        if (x != lastX || y != lastY) {
            return CheckUse();
        } else return -6; // Same cell as the first one
    }
}
