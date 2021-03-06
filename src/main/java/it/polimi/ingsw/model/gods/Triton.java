package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

/**
 * Class Triton implements Triton functionalities
 */
public class Triton extends MultipleActionGod {
    /**
     * Constructor Triton creates a new Triton instance.
     */
    public Triton() {
        this.name = "Triton";
        this.description = "Your Move: Each time your Worker moves into a perimeter space, it may immediately move again.";
        this.useLimit = 1;
    }

    /** @see it.polimi.ingsw.model.God#move(Board, Cell, int, int) */
    @Override
    public int move(Board board, Cell selectedCell, int x, int y) {
        int moved = checkMove(board, selectedCell, x, y);
        if (moved > 0) {
            board.getCell(x, y).setWorker(selectedCell.getWorker());
            selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
            selectedCell.setWorker(null);
            use++;
            if (x == 4 || x == 0 || y == 4 || y == 0)
                use = 0;
            else use = 1;
            return checkUse();
        }
        return moved;
    }

}
