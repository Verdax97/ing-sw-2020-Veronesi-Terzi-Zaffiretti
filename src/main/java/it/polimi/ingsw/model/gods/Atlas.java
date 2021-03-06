package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.God;

/**
 * Class Atlas implements Atlas functionalities
 */
public class Atlas extends God {
    /**
     * Constructor Atlas creates a new Atlas instance.
     */
    public Atlas() {
        this.name = "Atlas";
        this.description = "Your Build: your worker may build a dome at any level including the ground";
    }

    /** @see it.polimi.ingsw.model.God#building(Board, Cell, int, int, int, int) */
    @Override
    public int building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) {
        /* typeBuild 0 normale costruzione, typebuild 1 cupola */
        int built = checkBuild(board, selectedCell, x, y);
        if (built > 0) {
            int building = board.getCell(x, y).getBuilding();
            if (typeBuild == 1) {
                board.getCell(x, y).setDome(true);
            } else if (building == 3) {
                board.getCell(x, y).setDome(true);
            } else {
                board.getCell(x, y).setBuilding(1);
            }
            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
            board.getCell(x, y).setBuiltTurn(turnNumber);
        }
        return built;
    }
}
