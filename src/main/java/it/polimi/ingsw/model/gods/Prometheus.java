package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.DebuffGod;
import it.polimi.ingsw.model.Player;

public class Prometheus extends DebuffGod
{
    public Prometheus()
    {
        this.name = "Prometheus";
        this.description = "Your Turn: If your Worker does not move up, it may build both before and after moving";
    }
    @Override
    public int PlayerTurn(Board board, Player player, Cell selectedCell, int x, int y)
    {
        int built = CheckBuild(board, selectedCell, x, y);
        if (built > 0) {
            int building = board.getCell(x, y).getBuilding();
            if (building < 3)
                board.getCell(x, y).setBuilding(1);
            else if (building == 3)
                board.getCell(x, y).setDome(true);
            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
            board.getCell(x, y).setBuiltTurn(0);
            debuff = true;
            DebuffWorker(board, player);
        }
        return built;
    }
}
