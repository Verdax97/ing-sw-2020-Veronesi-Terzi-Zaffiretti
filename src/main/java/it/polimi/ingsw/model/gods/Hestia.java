package it.polimi.ingsw.model.gods;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

public class Hestia extends MultipleActionGod{

    public Hestia(){
        this.name = "Hestia";
        this.description = "Your Build: Your Worker may build one additional time, but this cannot be on a perimeter space.";
        this.useLimit = 2;
    }

    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber)
    {
        int built = CheckBuild(board, selectedCell, x, y);
        if (built > 0) {
            if (use != 1 || !(x == 0 || x == 4 || y == 0 || y == 4)) {
                int building = board.getCell(x, y).getBuilding();
                if (building < 3)
                    board.getCell(x, y).setBuilding(1);
                else if (building == 3)
                    board.getCell(x, y).setDome(true);
                board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                board.getCell(x, y).setBuiltTurn(turnNumber);
                use++;
                return CheckUse();
            } else return -9; //cell is in a perimeter space
        }
        return built;
    }
}
