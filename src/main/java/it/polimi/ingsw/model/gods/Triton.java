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
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                int debuff = (selectedCell.getWorker().isDebuff()) ? 1: 0;
                if ((selectedCell.getBuilding() == board.getCell(x, y).getBuilding() + debuff) || (selectedCell.getBuilding() >= board.getCell(x, y).getBuilding())) {
                    if (!board.getCell(x, y).getDome() && board.getCell(x, y).getWorker() == null) {
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
                    } else throw new RuntimeException("Target cell is occupied");
                } else throw new RuntimeException("Target cell is too high/low");
            } else throw new RuntimeException("Target cell is too far");
        } else throw new RuntimeException("Target cell out of board");
        return CheckUse();
    }

}
