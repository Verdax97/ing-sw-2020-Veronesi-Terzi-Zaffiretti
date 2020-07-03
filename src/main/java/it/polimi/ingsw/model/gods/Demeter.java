package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

/**
 * Class Demeter implements Demeter functionalities
 */
public class Demeter extends MultipleActionGod {

    private int lastX;
    private int lastY;

    /**
     * Constructor Demeter creates a new Demeter instance.
     */
    public Demeter() {
        this.name = "Demeter";
        this.description = "Your Build: your worker may build one additional time, but not on the same space";
        this.useLimit = 2;
    }

    /** @see it.polimi.ingsw.model.God#building(Board, Cell, int, int, int, int) */
    @Override
    public int building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) {
        if (use == 0) {
            lastX = x;
            lastY = y;
        }
        int built = checkBuild(board, selectedCell, x, y);
        if (built > 0) {
            super.building(board, selectedCell, x, y, typeBuild, turnNumber);
            use++;
            return checkUse();
        }
        return built;
    }

    /**
     * @see it.polimi.ingsw.model.God#checkBuild(Board, Cell, int, int)
     */
    @Override
    public int checkBuild(Board board, Cell selectedCell, int x, int y) {
        int ret = super.checkBuild(board, selectedCell, x, y);
        if (ret < 0)
            return ret;

        if (use == 0)
            return checkUse();

        if (x != lastX || y != lastY) {
            return checkUse();
        } else return -8; // same as last Build
    }
}
