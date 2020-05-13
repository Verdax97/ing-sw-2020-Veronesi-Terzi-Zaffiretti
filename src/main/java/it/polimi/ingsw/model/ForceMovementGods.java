package it.polimi.ingsw.model;

public class ForceMovementGods extends God
{
    protected int targetPosX;
    protected int targetPosY;

    protected int ForceMove(Board board, Cell selectedCell, int x, int y) {
        int moved = CheckMove(board, selectedCell,x ,y);
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
            selectedCell.setWorker(null);
        }
        return moved;
    }

    protected int MoveEnemy(Worker worker, Board board, Cell selectedCell, int x, int y)
    {
        if ((board.getCell(targetPosX, targetPosY).getWorker() == null && !board.getCell(targetPosX, targetPosY).getDome()) || (selectedCell == board.getCell(targetPosX,targetPosY)))
        {
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
       if (moved > 0 || (moved == -4 && !selectedCell.getDome()))
       {
           Worker worker = board.getCell(x,y).getWorker();
           if (worker != null)
           {
               if (worker.getPlayer().getNickname().equals(selectedCell.getWorker().getPlayer().getNickname()))//try to move your other worker
                   return moved;//should return -4

               if (targetPosX < 0 || targetPosX > 4 || targetPosY < 0 || targetPosY > 4)
                   return -7; //target space is out of board
           }
           return 1;
       }
       return moved;
    }
}
