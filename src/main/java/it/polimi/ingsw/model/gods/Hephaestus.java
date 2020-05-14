package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.MultipleActionGod;

public class Hephaestus extends MultipleActionGod {

    private int lastX;
    private int lastY;

    public Hephaestus(){
        this.name = "Hephaestus";
        this.description = "Your Build: your worker may build one additional block (not dome) on top of your first block";
        this.useLimit = 2;
    }

    @Override
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber){

        boolean again = false;

        int built = CheckBuild(board, selectedCell, x, y);
        if (built > 0) {
            if (use == 0) {
                lastX = x;
                lastY = y;
                int building = board.getCell(x, y).getBuilding();
                if (building < 3) {
                    if (building < 2) {
                        again = true;
                    }
                    board.getCell(x, y).setBuilding(1);
                } else if (building == 3)
                    board.getCell(x, y).setDome(true);
                board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                board.getCell(x, y).setBuiltTurn(turnNumber);
                use++;
            } else if (x == lastX && y == lastY) {
                int building = board.getCell(x, y).getBuilding();
                if (building < 3) {
                    board.getCell(x, y).setBuilding(1);
                    board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
                    board.getCell(x, y).setBuiltTurn(turnNumber);
                    use++;
                } else return -6;//Building is > 2
            }
            //the player doesn't have the possibility to use his hero power
            if (!again) {
                use = 0;
                return 1;
            }
            return CheckUse();
        }
        return built;
    }
}
