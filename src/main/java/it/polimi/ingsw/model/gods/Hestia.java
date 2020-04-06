package it.polimi.ingsw.model.gods;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

public class Hestia extends MultipleActionGod
{
    public Hestia(){
        this.name = "Hestia";
        this.description = "Your move: Your worker may move one additional time, but not back to the space it started on.";
        this.useLimit = 2;
    }

    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber)
    {
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)){
            if (selectedCell.isAdjacent(x, y)) {
                if (board.getCell(x, y).getWorker() == null) {
                    if (!selectedCell.getDome()) {
                        if (use != 1 || !(x == 0 || x == 4 || y == 0 || y == 4))
                        {
                            int building = board.getCell(x, y).getBuilding();
                            if (building < 3)
                                board.getCell(x, y).setBuilding(1);
                            else if (building == 3)
                                board.getCell(x, y).setDome(true);
                            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                            board.getCell(x, y).setBuiltTurn(turnNumber);
                            use++;
                        } else { throw new RuntimeException("Target cell is a perimeter space"); }
                    } else { throw new RuntimeException("Target cell has a Dome, you cannot build"); }
                } else { throw new RuntimeException("Target cell has a worker on it");}
            } else { throw new RuntimeException("Target cell is too far!");}
        } else {throw new RuntimeException("Target cell is out of the board!");}
        return CheckUse();
    }
}
