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
            lastX = selectedCell.getPos()[0];
            lastY = selectedCell.getPos()[1];
        }
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)){
            if (selectedCell.isAdjacent(x, y)) {
                if (x != lastX || y != lastY) {
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
                        } else { throw new RuntimeException("Target cell has a Dome, you cannot build"); }
                    } else { throw new RuntimeException("Target cell has a worker on it"); }
                } else {throw new RuntimeException("Target cell is same as previous");}
            } else { throw new RuntimeException("Target cell is too far!");}
        } else {throw new RuntimeException("Target cell is out of the board!");}
        return CheckUse();
    }
}
