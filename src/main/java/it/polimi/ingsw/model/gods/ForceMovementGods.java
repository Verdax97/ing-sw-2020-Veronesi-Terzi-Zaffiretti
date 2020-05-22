package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.Worker;

/**
 * Class ForceMovementGods ...
 */
public class ForceMovementGods extends MoveEnemyGods {

    /**
     * Method ForceMove combination of Move and MoveEnemy
     *
     * @param board of type Board
     * @param selectedCell of type Cell
     * @param x of type int
     * @param y of type int
     * @return int
     */
    public int ForceMove(Board board, Cell selectedCell, int x, int y) {
        int moved = CheckMove(board, selectedCell, x, y);
        if (moved > 0) {
            Worker playerWorker = selectedCell.getWorker();
            Worker targetedWorker = board.getCell(x, y).getWorker();
            if (targetedWorker != null) {
                int val = MoveEnemy(targetedWorker, board, selectedCell, x, y);
                if (val < 0)
                    return -5;// no space to move enemy worker
            }
            board.getCell(x, y).setWorker(playerWorker);
            playerWorker.setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
            if (board.getCell(targetPosX, targetPosY) != selectedCell || targetedWorker == null)
                selectedCell.setWorker(null);
        }
        return moved;
    }

    /**
     * Method CheckMove checks if Move is possible
     *
     * @param board of type Board
     * @param selectedCell of type Cell
     * @param x of type int
     * @param y of type int
     * @return int
     * @see it.polimi.ingsw.model.God#CheckMove(Board, Cell, int, int)
     */
    @Override
    public int CheckMove(Board board, Cell selectedCell, int x, int y) {
        int result = 0;
        int moved = super.CheckMove(board, selectedCell, x, y);
        if (moved > 0 || (moved == -4 && !selectedCell.getDome())) {
            Worker worker = board.getCell(x, y).getWorker();
            if (worker != null) {
                if (worker.getPlayer().getNickname().equals(selectedCell.getWorker().getPlayer().getNickname()))//try to move your other worker
                {
                    result = moved;//should return -4
                } else if (targetPosX < 0 || targetPosX > 4 || targetPosY < 0 || targetPosY > 4) {
                    result = -7;//target space is out of board
                }

            }
            if (result == 0) {
                result = 1;
            }
        }
        if (result == 0) {
            result = moved;
        }
        return result;
    }
}