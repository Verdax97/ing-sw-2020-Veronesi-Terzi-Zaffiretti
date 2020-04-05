package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.God;

public class Atlas extends God
{
    public Atlas(){
        this.name = "Atlas";
        this.description = "Your Build: your worker may build a dome at any level including the ground";
    }

    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber){
        /* typeBuild 0 normale costruzione, typebuild 1 cupola */
        int a = selectedCell.getWorker().getPlayer().getGodPower().Building(board, selectedCell, x, y, typeBuild, turnNumber);
        if (a != 0)
            return a;//1 default return value, 2 need to repeat move
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)){
            if (selectedCell.isAdjacent(x, y)) {
                if (board.getCell(x, y).getWorker() == null) {
                    if (!selectedCell.getDome()) {
                        if (typeBuild == 1) {
                            board.getCell(x, y).setDome(true);
                        }
                        else { board.getCell(x, y).setBuilding(1);}
                        board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                        board.getCell(x, y).setBuiltTurn(turnNumber);
                    } else { throw new RuntimeException("Target cell has a Dome, you cannot build"); }
                } else { throw new RuntimeException("Target cell has a worker on it");}
            } else { throw new RuntimeException("Target cell is too far!");}
        } else {throw new RuntimeException("Target cell is out of the board!");}
        return 1;
    }
}
