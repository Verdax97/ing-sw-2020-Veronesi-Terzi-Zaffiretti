package it.polimi.ingsw.model;

public class ForceMovementGods extends God
{
    protected int targetPosX;
    protected int targetPosY;

    protected int ForceMove(Board board, Cell selectedCell, int x, int y) {
        int moved = CheckMove(board, selectedCell,x ,y);
        if (moved > 0) {
            Worker targetedWorker = board.getCell(x, y).getWorker();
            if (targetedWorker != null) {
                if (targetPosX < 0 || targetPosX > 4 || targetPosY < 0 || targetPosY > 4)
                    return -7; //target space is out of board
                int val = MoveEnemy(targetedWorker, board, x, y);
                if (val < 0)
                    return -5;// no space to move enemy worker
            }
            board.getCell(x, y).setWorker(selectedCell.getWorker());
            selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
            selectedCell.setWorker(null);
        }
        return moved;
    }

    protected int MoveEnemy(Worker worker, Board board, int x, int y)
    {
        if (board.getCell(targetPosX, targetPosY).getWorker() == null && !board.getCell(targetPosX, targetPosY).getDome())
        {
            if (worker != null)
                worker.setLastMovement(0);
            board.getCell(x, y).setWorker(null);
            board.getCell(targetPosX, targetPosY).setWorker(worker);
        }
        else return -5;//no space to move enemy worker
        return 1;
    }

    @Override
    public int CheckMove(Board board, Cell selectedCell, int x, int y) {
       int moved = super.CheckMove(board, selectedCell, x, y);
       if (moved > 0 || (moved == -4 && !selectedCell.getDome())) return 1;
       return moved;
    }
}
