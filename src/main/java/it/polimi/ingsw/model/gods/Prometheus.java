package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import javafx.scene.image.Image;

public class Prometheus extends DebuffGod
{
    public Prometheus()
    {
        this.name = "Prometheus";
        this.description = "Your Turn: If your Worker does not move up, it may build both before and after moving";
        img = new Image("Images/godCards/" + name + ".png");
    }

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

    @Override
    public int CheckPlayerTurn(Board board, Cell selectedCell, int x, int y){
        return CheckBuild(board, selectedCell, x, y);
    }
}
