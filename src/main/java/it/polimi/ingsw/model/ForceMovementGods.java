package it.polimi.ingsw.model;

public class ForceMovementGods extends God
{
    protected int targetPosX;
    protected int targetPosY;

    protected int ForceMove(Board board, Cell selectedCell, int x, int y) {
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (selectedCell.IsNotHigh(board, x, y)) {
                    if (selectedCell.IsFreeDome(board, x, y)) {
                        Worker targetedWorker = board.getCell(x, y).getWorker();
                        if (board.getCell(x, y).getWorker() == null) {
                            board.getCell(x, y).setWorker(selectedCell.getWorker());
                            selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
                            selectedCell.setWorker(null);
                        } else if (board.getCell(targetPosX, targetPosY).getWorker() == null || board.getCell(targetPosX, targetPosY).getWorker() == selectedCell.getWorker()) {
                            board.getCell(x, y).setWorker(selectedCell.getWorker());
                            selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
                            selectedCell.setWorker(null);
                            targetedWorker.setLastMovement(0);
                            board.getCell(targetPosX, targetPosY).setWorker(targetedWorker);
                        } else return -5;//no space to move enemy worker
                    } else return -4;//cell is occupied
                } else return -3;//cell is too high
            } else return-2;// throw new RuntimeException("Target cell is too far");
        } else return -1;//throw new RuntimeException("Target cell out of board");
        return 1;
    }
}
