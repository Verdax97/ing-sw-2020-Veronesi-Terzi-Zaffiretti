package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

public class Artemis extends MultipleActionGod
{
    public Artemis(){
            this.name = "Artemis";
            this.description = "Your move: Your worker may move one additional time, but not back to the space it started on.";
            this.useLimit = 2;
    }

    @Override
    public int Move(Board board, Cell selectedCell, int x, int y)
    {
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if ((selectedCell.getBuilding() == board.getCell(x, y).getBuilding() + 1) || (selectedCell.getBuilding() >= board.getCell(x, y).getBuilding())) {
                    if (!board.getCell(x, y).getDome() && board.getCell(x, y).getWorker() == null) {
                        board.getCell(x, y).setWorker(selectedCell.getWorker());
                        selectedCell.getWorker().setLastMovement(board.getCell(x,y).getBuilding() - selectedCell.getBuilding());
                        selectedCell.setWorker(null);
                        use++;
                    } else throw new RuntimeException("Target cell is occupied");
                } else throw new RuntimeException("Target cell is too high/low");
            } else throw new RuntimeException("Target cell is too far");
        } else throw new RuntimeException("Target cell out of board");
        return CheckUse();
    }

}
