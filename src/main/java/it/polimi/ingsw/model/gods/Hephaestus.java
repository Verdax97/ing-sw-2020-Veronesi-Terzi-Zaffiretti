package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

public class Hephaestus extends MultipleActionGod {

    private int lastX;
    private int lastY;

    public Hephaestus(){
        this.name = "Hephaestus";
        this.description = "Your Build: your worker may build one additional block (not dome) on top of your first block";
        this.useLimit = 2;
    }

    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber){
        if (use == 0)
        {
            lastX = x;
            lastY = y;

        }
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (board.getCell(x, y).getWorker() == null) {
                    if (!selectedCell.getDome()) {
                        if (use == 0) {
                            int building = board.getCell(x, y).getBuilding();
                            if (building < 3)
                                board.getCell(x, y).setBuilding(1);
                            else if (building == 3)
                                board.getCell(x, y).setDome(true);
                            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                            board.getCell(x, y).setBuiltTurn(turnNumber);
                            use++;
                        } else if (x == lastX && y == lastY) {
                            int building = board.getCell(x, y).getBuilding();
                            if (building < 3) {
                                board.getCell(x, y).setBuilding(1);
                                board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                                board.getCell(x, y).setBuiltTurn(turnNumber);
                                use++;
                            } else return -6;//Building is > 2
                        } else return -5;//Not same as last built cell
                    } else return -4;//Cell occupied by a dome
                } else return -3;//Worker on the cell
            } else return -2; //Target cell is too far
        } else return -1;//Target cell out of board
        return CheckUse();
    }
}
