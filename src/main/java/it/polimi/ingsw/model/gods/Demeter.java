package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;
import javafx.scene.image.Image;

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

    /** @see it.polimi.ingsw.model.God#Building(Board, Cell, int, int, int, int) */
    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) {
        if (use == 0) {
            lastX = x;
            lastY = y;
        }
        int built = CheckBuild(board, selectedCell, x, y);
        if (built > 0) {
            super.Building(board, selectedCell, x, y, typeBuild, turnNumber);
            use++;
            return CheckUse();
        }
        return built;
    }

    /**
     * @see it.polimi.ingsw.model.God#CheckBuild(Board, Cell, int, int)
     */
    @Override
    public int CheckBuild(Board board, Cell selectedCell, int x, int y) {
        int ret = super.CheckBuild(board, selectedCell, x, y);
        if (ret < 0)
            return ret;

        if (use == 0)
            return CheckUse();

        if (x != lastX || y != lastY) {
            return CheckUse();
        } else return -8; // same as last Build
    }
}
