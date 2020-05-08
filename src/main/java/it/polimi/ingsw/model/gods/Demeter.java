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
        if ((x < 5 && x >= 0) && (y < 5 && y >= 0)){
            if (selectedCell.isAdjacent(x, y)) {
                if (x != lastX || y != lastY || use == 0) {
                    if (board.getCell(x, y).getWorker() == null) {
                        if (!board.getCell(x, y).getDome()) {
                            int building = board.getCell(x, y).getBuilding();
                            if (building < 3)
                                board.getCell(x, y).setBuilding(1);
                            else if (building == 3)
                                board.getCell(x, y).setDome(true);
                            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                            board.getCell(x, y).setBuiltTurn(turnNumber);
                            use++;
                        }else return -4;//Cell occupied by a dome
                    } else return -3;//Worker on the cell
                } else return -8;//same as last built
            } else return -2;//Target cell is too far
        } else return -1;//Target cell out of board
        return CheckUse();
    }
}
