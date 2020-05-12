package it.polimi.ingsw.model;

//import java.awt.*;

public class God {
    protected String name;
    public String description;
    //protected Image img;

    public String getName() { return name; }

    public String getDescription() {return description; }

    public int PlayerTurn(Board board, Player player, Cell selectedCell, int x, int y) { return 1; }

    public int Move(Board board, Cell selectedCell, int x, int y) {
        int moved = CheckMove(board, selectedCell, x, y);
        if (moved > 0){
            board.getCell(x, y).setWorker(selectedCell.getWorker());
            selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
            selectedCell.setWorker(null);
        }
        return moved;
    }

    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) { return 0; }

    public int EnemyTurn(Board board, Player turnPlayer, Player player) { return 0; }


    public Player WinCondition(Board board, Player player) { return null; }

    public void ResetGod() {}

    public int CheckMove(Board board, Cell selectedCell, int x, int y) {
        if ((x < 5 && x >= 0) && (y < 5 && y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (selectedCell.IsNotHigh(board, x, y)) {
                    if (selectedCell.IsFreeDome(board, x, y)) {
                        if (selectedCell.IsFreeWorker(board, x, y)) {
                        } else return -4;//cell is occupied
                    } else return -4;//cell is occupied
                } else return -3;//cell is too high
            } else return-2;//cell is too far
        } else return -1;//cell out of board
        return 1;//1 default return value, 2 need to repeat action
    }

}
