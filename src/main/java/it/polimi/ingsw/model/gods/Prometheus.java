package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;

/**
 * Class Prometheus implements Prometheus functionalities
 */
public class Prometheus extends DebuffGod
{
    /**
     * Constructor Prometheus creates a new Prometheus instance.
     */
    public Prometheus()
    {
        this.name = "Prometheus";
        this.description = "Your Turn: If your Worker does not move up, it may build both before and after moving";
    }

    /** @see it.polimi.ingsw.model.God#playerTurn(Board, Cell, int, int)   */
    @Override
    public int playerTurn(Board board, Cell selectedCell, int x, int y)
    {
        int built = checkPlayerTurn(board, selectedCell, x, y);
        if (built > 0) {
            int building = board.getCell(x, y).getBuilding();
            if (building < 3)
                board.getCell(x, y).setBuilding(1);
            else if (building == 3)
                board.getCell(x, y).setDome(true);
            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
            board.getCell(x, y).setBuiltTurn(0);
            debuff = true;
            debuffWorker(board, selectedCell.getWorker().getPlayer());
        }
        return built;
    }

    /** @see it.polimi.ingsw.model.God#checkPlayerTurn(Board, Cell, int, int)   */
    @Override
    public int checkPlayerTurn(Board board, Cell selectedCell, int x, int y){
        return checkBuild(board, selectedCell, x, y);
    }
}
