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
        if ((x < 5 && x >= 0) && (y < 5 && y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (board.getCell(x, y).getWorker() == null) {
                    if (!board.getCell(x, y).getDome()) {
                        int building = board.getCell(x, y).getBuilding();
                        if (building < 3)
                            board.getCell(x, y).setBuilding(1);
                        else if (building == 3)
                            board.getCell(x, y).setDome(true);
                        board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                        board.getCell(x, y).setBuiltTurn(0);
                    } else return -4;//Cell occupied by a dome
                } else return -3;//Worker on the cell
            }else return -2;//Target cell too far
        } else return -1;//Target cell out of board
        debuff = true;
        DebuffWorker(board, player);
        return 1;
    }
}
