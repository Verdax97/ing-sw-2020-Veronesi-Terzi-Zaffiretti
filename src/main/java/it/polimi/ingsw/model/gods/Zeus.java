package it.polimi.ingsw.model.gods;
import it.polimi.ingsw.model.*;

public class Zeus extends God{

    public Zeus(){
        this.name = "Zeus";
        this.description = "Your Build: your worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up to the third level";
    }

    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber){
        int built = CheckBuild(board, selectedCell, x, y);
        if (built > 0) {
            int building = board.getCell(x, y).getBuilding();
            if (building < 3) {
                board.getCell(x, y).setBuilding(1);
            } else if (building == 3 && built == 1) {
                board.getCell(x, y).setDome(true);
            } else return -7;//third level build forbidden
            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
            board.getCell(x, y).setBuiltTurn(turnNumber);
            return 1;
        }
        return built;
    }

    @Override
    public int CheckBuild(Board board, Cell selectedCell, int x, int y) {

        int built = super.CheckBuild(board, selectedCell, x, y);
        if (built < 0 && x == selectedCell.getPos()[0] && y == selectedCell.getPos()[1]){
            return 2;
        }
        return built;
    }
}