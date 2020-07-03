package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;

/**
 * Class Charon implements Charon functionalities
 */
public class Charon extends MoveEnemyGods {

    /**
     * Constructor Charon creates a new Charon instance.
     */
    public Charon() {
        this.name = "Charon";
        this.description = "Before your worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if the space is unoccupied";
    }

    /** @see it.polimi.ingsw.model.God#playerTurn(Board, Cell, int, int)*/
    @Override
    public int playerTurn(Board board, Cell selectedCell, int x, int y) {
        int ret = checkPlayerTurn(board, selectedCell, x, y);
        if (ret <= 0)
            return ret;

        return moveEnemy(board.getCell(x, y).getWorker(), board, selectedCell, x, y);
    }

    /** @see it.polimi.ingsw.model.God#checkPlayerTurn(Board, Cell, int, int) */
    @Override
    public int checkPlayerTurn(Board board, Cell selectedCell, int x, int y)
    {
        if (board.getCell(x, y).getWorker() == null)
            return -2;//no valid worker to make the move
        if (board.getCell(x, y).getWorker().getPlayer().getNickname().equals(selectedCell.getWorker().getPlayer().getNickname()))
            return -2;
        int dx = selectedCell.getPos()[0] - x;
        int dy = selectedCell.getPos()[1] - y;
        targetPosX = selectedCell.getPos()[0] + dx;
        targetPosY = selectedCell.getPos()[1] + dy;
        if (targetPosX < 0 || targetPosX > 4 || targetPosY < 0 || targetPosY > 4)
            return -7; //target space is out of board
        if ((board.getCell(targetPosX, targetPosY).getWorker() != null || board.getCell(targetPosX, targetPosY).getDome()) && (selectedCell != board.getCell(targetPosX, targetPosY)))
            return -5;//no space to move enemy worker
        return 1;
    }
}
