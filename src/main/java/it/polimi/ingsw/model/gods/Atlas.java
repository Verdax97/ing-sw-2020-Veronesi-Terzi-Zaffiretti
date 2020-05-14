package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.God;

public class Atlas extends God
{
    public Atlas(){
        this.name = "Atlas";
        this.description = "Your Build: your worker may build a dome at any level including the ground";
    }

    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber){
        /* typeBuild 0 normale costruzione, typebuild 1 cupola */
        int built = CheckBuild(board, selectedCell, x, y);
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
