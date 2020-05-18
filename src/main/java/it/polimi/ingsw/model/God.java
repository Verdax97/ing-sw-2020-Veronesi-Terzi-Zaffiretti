package it.polimi.ingsw.model;

import java.io.Serializable;

public class God extends SimpleGod implements Serializable {
    public int PlayerTurn(Board board, Cell selectedCell, int x, int y) {
        return 1;
    }

    public int CheckPlayerTurn(Board board, Cell selectedCell, int x, int y) {
        return 0;
    }

    public int Move(Board board, Cell selectedCell, int x, int y) {
        int moved = CheckMove(board, selectedCell, x, y);
        if (moved > 0) {
            board.getCell(x, y).setWorker(selectedCell.getWorker());
            selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
            selectedCell.setWorker(null);
        }
        return moved;
    }

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
        }
        return built;
    }

    public int EnemyTurn(Board board, Player turnPlayer, Player player) {
        return 0;
    }


    public Player WinCondition(Board board, Player player) {
        return null;
    }

    public void ResetGod() {
        //no need to reset for all gods just need to implement in force move, multiple action, and debuff
    }

    public int CheckMove(Board board, Cell selectedCell, int x, int y) {
        if ((x < 5 && x >= 0) && (y < 5 && y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (selectedCell.IsNotHigh(board, x, y)) {
                    if (selectedCell.IsFreeDome(board, x, y)) {
                        if (!selectedCell.IsFreeWorker(board, x, y)) return -4;//cell is occupied
                    } else return -4;//cell is occupied
                } else return -3;//cell is too high
            } else return -2;//cell is too far
        } else return -1;//cell out of board
        return 1;//1 default return value, 2 need to repeat action
    }

    public int CheckBuild(Board board, Cell selectedCell, int x, int y){
        if ((x < 5 && x >= 0) && (y < 5 && y >= 0)){
            if (selectedCell.isAdjacent(x, y)) {
                if (board.getCell(x, y).getWorker() == null) {
                    if (board.getCell(x, y).getDome()) return -4;//Cell occupied by a dome
                } else return -3;//Worker on the cell
            } else return -2; //Target cell is too far
        } else return -1;//Target cell out of board
        return 1;
    }

}
