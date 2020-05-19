package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.Worker;

public class MoveEnemyGods extends God {

    protected int targetPosX;
    protected int targetPosY;

    public int MoveEnemy(Worker worker, Board board, Cell selectedCell, int x, int y) {
        if ((board.getCell(targetPosX, targetPosY).getWorker() == null && !board.getCell(targetPosX, targetPosY).getDome()) || (selectedCell == board.getCell(targetPosX, targetPosY))) {
            worker.setLastMovement(0);
            board.getCell(targetPosX, targetPosY).setWorker(worker);
            board.getCell(x, y).setWorker(null);
        } else return -5;//no space to move enemy worker
        return 1;
    }
}
