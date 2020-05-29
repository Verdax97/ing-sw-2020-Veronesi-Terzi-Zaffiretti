package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

/**
 * Class Hephaestus implements Hephaestus functionalities
 *
 * @author Davide
 * Created on 22/05/2020
 */
public class Hephaestus extends MultipleActionGod {

    private int lastX;
    private int lastY;

    /**
     * Constructor Hephaestus creates a new Hephaestus instance.
     */
    public Hephaestus() {
        this.name = "Hephaestus";
        this.description = "Your Build: your worker may build one additional block (not dome) on top of your first block";
        this.useLimit = 2;
    }

    /**
     * @see it.polimi.ingsw.model.God#Building(Board, Cell, int, int, int, int)
     */
    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) {
        int built = CheckBuild(board, selectedCell, x, y);
        if (built < 0)
            return built;

        if (use == 0) {
            lastX = x;
            lastY = y;
        }
        int building = board.getCell(x, y).getBuilding();
        if (building < 3) {
            board.getCell(x, y).setBuilding(1);
        } else if (building == 3)
            board.getCell(x, y).setDome(true);
        board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
        board.getCell(x, y).setBuiltTurn(turnNumber);
        use++;
        if (board.getCell(x, y).getBuilding() == 3)
            return 1;
        return CheckUse();
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
            return 1;

        if (x == lastX && y == lastY) {
            if (!(board.getCell(x, y).getBuilding() < 3))
                return -6;//Building is > 2
            else
                return CheckUse();
        }
        return -1;
    }
}
