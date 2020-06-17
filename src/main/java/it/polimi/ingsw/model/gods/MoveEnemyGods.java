package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.Worker;

/**
 * Class MoveEnemyGods move the enemies workers to target position
 */
public class MoveEnemyGods extends God {

    protected int targetPosX;
    protected int targetPosY;

    /**
     * Method MoveEnemy move the enemy to the target pos is possible
     *
     * @param worker       of type Worker
     * @param board        of type Board
     * @param selectedCell of type Cell
     * @param x            of type int
     * @param y            of type int
     * @return int
     */
    public int MoveEnemy(Worker worker, Board board, Cell selectedCell, int x, int y) {
        if ((board.getCell(targetPosX, targetPosY).getWorker() == null && !board.getCell(targetPosX, targetPosY).getDome()) || (selectedCell == board.getCell(targetPosX, targetPosY))) {
            worker.setLastMovement(0);
            board.getCell(targetPosX, targetPosY).setWorker(worker);
            board.getCell(x, y).setWorker(null);
        } else return -5;//no space to move enemy worker
        return 1;
    }

    public void computeTargetPos(Cell selectedCell, int x, int y) {
    }
}
