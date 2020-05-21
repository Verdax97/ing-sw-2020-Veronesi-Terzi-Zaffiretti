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
     * Method Move implements the move for Artemis
     *
     * @param board of type Board
     * @param selectedCell of type Cell
     * @param x of type int
     * @param y of type int
     * @return int
     */
    @Override
    public int Move(Board board, Cell selectedCell, int x, int y) {
        int moved = CheckMove(board, selectedCell, x, y);
        if (moved > 0) {
            if (use == 0) {
                lastX = selectedCell.getPos()[0];
                lastY = selectedCell.getPos()[1];
            }
            if (x != lastX || y != lastY) {
                if (selectedCell.IsFreeWorker(board, x, y)) {
                    board.getCell(x, y).setWorker(selectedCell.getWorker());
                    selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
                    selectedCell.setWorker(null);
                    use++;
                }
            } else return -6; // Same cell as the first one
            return CheckUse();
        }
        return moved;
    }


}
