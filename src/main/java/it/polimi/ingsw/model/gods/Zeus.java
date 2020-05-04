package it.polimi.ingsw.model.gods;
import it.polimi.ingsw.model.*;

public class Zeus extends God{

    public Zeus(){
        this.name = "Zeus";
        this.description = "Your Build: your worker may build under itself in its current space, forcing it up one level. You do not win by forcing yourself up to the third level";
    }

    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber){
        int i = selectedCell.getPos()[0];
        int j = selectedCell.getPos()[1];
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)){
            if (selectedCell.isAdjacent(x, y) || (x == i && y == j)) {
                if (board.getCell(x, y).getWorker() == null || (x == i && y == j)) {
                    if (!board.getCell(x, y).getDome()) {
                        int building = board.getCell(x, y).getBuilding();
                        if (building < 3) {
                            if (x == i && y == j) {
                                board.getCell(x, y).setBuilding(1);
                            }
                        }
                        else if (building == 3 && (x != i || y != j)) {
                            board.getCell(x, y).setDome(true);
                        } else return -7;//third level build forbidden
                        board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                        board.getCell(x, y).setBuiltTurn(turnNumber);
                    } else return -4; //cell has a Dome
                } else return -4; //cell has a worker on it
            } else return -2; //cell is too far
        } else return -1; //cell is out of the board
        return 1;
    }
}