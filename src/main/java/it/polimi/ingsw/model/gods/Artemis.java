package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

public class Artemis extends MultipleActionGod
{
    private int lastX;
    private int lastY;
    public Artemis(){
            this.name = "Artemis";
            this.description = "Your move: Your worker may move one additional time, but not back to the space it started on.";
            this.useLimit = 2;
    }

    @Override
    public int Move(Board board, Cell selectedCell, int x, int y)
    {
        if (use == 0)
        {
            lastX = selectedCell.getPos()[0];
            lastY = selectedCell.getPos()[1];
        }
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (x != lastX || y != lastY){
                    if (selectedCell.IsNotHigh(board, x, y)) {
                        if (selectedCell.IsFreeDome(board, x, y)) {
                            if (selectedCell.IsFreeWorker(board, x, y)) {
                                board.getCell(x, y).setWorker(selectedCell.getWorker());
                                selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
                                selectedCell.setWorker(null);
                                use++;
                            } else return -4; //cell has a worker on it
                        } else return -4; //cell has a dome on it
                    } else return -3; //cell is too high
                } else return -6; //same as last position
            } else return-2; //cell is too far
        } else return -1; //cell out of board
        return CheckUse();
    }

}
