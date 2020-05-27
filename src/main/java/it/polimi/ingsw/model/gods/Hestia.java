package it.polimi.ingsw.model.gods;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;
import javafx.scene.image.Image;

/**
 * Class Hestia implements Hestia functionalities
 */
public class Hestia extends MultipleActionGod {

    /**
     * Constructor Hestia creates a new Hestia instance.
     */
    public Hestia() {
        this.name = "Hestia";
        this.description = "Your Build: Your Worker may build one additional time, but this cannot be on a perimeter space.";
        img = new Image("Images/godCards/" + name + ".png");
        this.useLimit = 2;
    }

    /** @see it.polimi.ingsw.model.God#Building(Board, Cell, int, int, int, int) */
    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) {
        int built = CheckBuild(board, selectedCell, x, y);
        if (built > 0) {
            int building = board.getCell(x, y).getBuilding();
            if (building < 3)
                board.getCell(x, y).setBuilding(1);
            else if (building == 3)
                board.getCell(x, y).setDome(true);
            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
            board.getCell(x, y).setBuiltTurn(turnNumber);
            use++;
            return CheckUse();
        }
        return built;
    }

    /**
     * @see it.polimi.ingsw.model.God#CheckBuild(Board, Cell, int, int)
     */
    @Override
    public int CheckBuild(Board board, Cell selectedCell, int x, int y) {
        int ret = super.CheckBuild(board, selectedCell, x, y);
        if (ret < 0)
            return ret;
        if (use == 0)
            return ret;
        if (!(x == 0 || x == 4 || y == 0 || y == 4)) {
            return CheckUse();
        } else return -9; //cell is in a perimeter space
    }
}
