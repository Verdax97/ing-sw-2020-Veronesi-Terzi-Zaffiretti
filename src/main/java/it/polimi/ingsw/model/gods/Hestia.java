package it.polimi.ingsw.model.gods;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

public class Hestia extends MultipleActionGod{

    private int lastX;
    private int lastY;

    public Hestia(){
        this.name = "Hestia";
        this.description = "Your move: Your worker may move one additional time, but not back to the space it started on.";
        this.useLimit = 2;
    }

    @Override
    public int Move(Board board, Cell selectedCell, int x, int y)
    {
        if (use == 0)
        {
            lastX = x;
            lastY = y;
        }
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (selectedCell.IsNotHigh(board, x, y)) {
                    if (x != lastX && y!= lastY || use == 0) {
                        if (selectedCell.IsFreeDome(board, x, y)) {
                            if (selectedCell.IsFreeWorker(board, x, y)) {
                                board.getCell(x, y).setWorker(selectedCell.getWorker());
                                selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
                                selectedCell.setWorker(null);
                            } else return -4;//cell is occupied worker
                        } else return -4;//cell is occupied dome
                    } else return -9;//same has last move
                } else return -3;//cell is too high
            } else return-2;//cell is too far
        } else return -1;//cell out of board
        return CheckUse();
    }
}
