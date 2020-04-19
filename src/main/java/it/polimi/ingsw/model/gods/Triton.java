package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

public class Triton extends MultipleActionGod
{
    public Triton(){
        this.name = "Triton";
        this.description = "Your Move: Each time your Worker moves into a perimeter space, it may immediately move again.";
        this.useLimit = 1;
    }

    @Override
    public int Move(Board board, Cell selectedCell, int x, int y)
    {
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0))
        {
            if (selectedCell.isAdjacent(x, y))
            {
                if (selectedCell.IsNotHigh(board, x, y))
                {
                    if (selectedCell.IsFreeDome(board, x, y))
                    {
                        if (selectedCell.IsFreeWorker(board, x, y))
                        {
                            board.getCell(x, y).setWorker(selectedCell.getWorker());
                            selectedCell.getWorker().setLastMovement(board.getCell(x,y).getBuilding() - selectedCell.getBuilding());
                            selectedCell.setWorker(null);
                            if (selectedCell.getPos()[0] < 4 && selectedCell.getPos()[0] > 0 && selectedCell.getPos()[1] < 4 && selectedCell.getPos()[1] > 0)
                            {
                                if (x == 4 || x == 0 || y == 4 || y == 0)
                                    use = 0;
                                else
                                    use = 1;
                            }
                        } else return -4;//cell is occupied
                    } else return -4;//cell is occupied
                } else return -3;//cell is too high
            } else return-2;// throw new RuntimeException("Target cell is too far");
        } else return -1;//throw new RuntimeException("Target cell out of board");
        return CheckUse();
    }

}
