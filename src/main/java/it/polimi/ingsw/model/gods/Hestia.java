package it.polimi.ingsw.model.gods;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

/**
 * Class Hestia implements Hestia functionalities
 */
public class Hestia extends MultipleActionGod {

    /**
     * Constructor Hestia creates a new Hestia instance.
     */
    public Hestia() {
        this.name = "Hestia";
        this.description = "Your Build: Your Worker may build one additional time, but this cannot be on a perimeter space.";
        this.useLimit = 2;
    }

    /** @see it.polimi.ingsw.model.God#building(Board, Cell, int, int, int, int) */
    @Override
    public int building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) {
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
            return ret;
        if (!(x == 0 || x == 4 || y == 0 || y == 4)) {
            return checkUse();
        } else return -9; //cell is in a perimeter space
    }
}
