package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

public class Demeter extends MultipleActionGod {

    private int lastX;
    private int lastY;

    public Demeter(){
        this.name = "Demeter";
        this.description = "Your Build: your worker may build one additional time, but not on the same space";
        this.useLimit = 2;
    }

    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber){
        if (use == 0)
        {
            lastX = x;
            lastY = y;
        }
        int built = CheckBuild(board, selectedCell, x, y);
        if (built > 0) {
            if (x != lastX || y != lastY || use == 0) {
                int building = board.getCell(x, y).getBuilding();
                if (building < 3)
                    board.getCell(x, y).setBuilding(1);
                else if (building == 3)
                    board.getCell(x, y).setDome(true);
                board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                board.getCell(x, y).setBuiltTurn(turnNumber);
                use++;
                return CheckUse();
            }else return -8; // same as last Build
        }
        return built;
    }
}
