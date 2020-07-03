package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.God;

/**
 * Class Zeus implements Zeus functionalities
 */
public class Zeus extends God {

    /**
     * Constructor Zeus creates a new Zeus instance.
     */
    public Zeus() {
        this.name = "Zeus";
        this.description = "Your Build: your worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up to the third level";
    }

    /** @see it.polimi.ingsw.model.God#building(Board, Cell, int, int, int, int)  */
    @Override
    public int building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber){
        int built = checkBuild(board, selectedCell, x, y);
        if (built > 0) {
            int building = board.getCell(x, y).getBuilding();
            if (building < 3) {
                board.getCell(x, y).setBuilding(1);
            } else if (building == 3 && selectedCell != board.getCell(x, y)) {
                board.getCell(x, y).setDome(true);
            } else return -7;//third level build forbidden
            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
            board.getCell(x, y).setBuiltTurn(turnNumber);
            return 1;
        }
        return built;
    }

    /** @see it.polimi.ingsw.model.God#checkBuild(Board, Cell, int, int)  */
    @Override
    public int checkBuild(Board board, Cell selectedCell, int x, int y) {

        int built = super.checkBuild(board, selectedCell, x, y);
        if (built < 0 && x == selectedCell.getPos()[0] && y == selectedCell.getPos()[1]){
            return 1;
        }
        return built;
    }
}