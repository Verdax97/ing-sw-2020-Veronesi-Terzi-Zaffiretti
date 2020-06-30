package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Player;
import javafx.scene.image.Image;

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

    /** @see it.polimi.ingsw.model.God#PlayerTurn(Board, Cell, int, int)   */
    @Override
    public int PlayerTurn(Board board, Cell selectedCell, int x, int y)
    {
        int built = CheckPlayerTurn(board, selectedCell, x, y);
        if (built > 0) {
            int building = board.getCell(x, y).getBuilding();
            if (building < 3)
                board.getCell(x, y).setBuilding(1);
            else if (building == 3)
                board.getCell(x, y).setDome(true);
            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
            board.getCell(x, y).setBuiltTurn(0);
            debuff = true;
            DebuffWorker(board, selectedCell.getWorker().getPlayer());
        }
        return built;
    }

    /** @see it.polimi.ingsw.model.God#CheckPlayerTurn(Board, Cell, int, int)   */
    @Override
    public int CheckPlayerTurn(Board board, Cell selectedCell, int x, int y){
        return CheckBuild(board, selectedCell, x, y);
    }
}
