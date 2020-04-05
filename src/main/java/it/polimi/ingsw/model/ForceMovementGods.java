package it.polimi.ingsw.model;

public class ForceMovementGods extends God
{
    protected int targetPosX;
    protected int targetPosY;

    protected void ForceMove(Board board, Cell selectedCell, int x, int y)
    {
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0))
        {
            if (selectedCell.isAdjacent(x, y))
            {
                if (selectedCell.getBuilding() + 1 >= board.getCell(x, y).getBuilding())
                {
                    if (!board.getCell(x, y).getDome())
                    {
                        Worker targetedWorker = board.getCell(x, y).getWorker();
                        if (board.getCell(x, y).getWorker() == null)
                        {
                            board.getCell(x, y).setWorker(selectedCell.getWorker());
                            selectedCell.getWorker().setLastMovement(board.getCell(x,y).getBuilding() - selectedCell.getBuilding());
                            selectedCell.setWorker(null);
                        }
                        else if (board.getCell(x, y).getWorker() != null && (board.getCell(targetPosX, targetPosY).getWorker() == null || board.getCell(targetPosX, targetPosY).getWorker() == selectedCell.getWorker()))
                        {
                            board.getCell(x, y).setWorker(selectedCell.getWorker());
                            selectedCell.getWorker().setLastMovement(board.getCell(x,y).getBuilding() - selectedCell.getBuilding());
                            selectedCell.setWorker(null);
                            board.getCell(targetPosX, targetPosY).setWorker(targetedWorker);
                        } else throw new RuntimeException("Can't move opponent worker to target position");
                    }else throw new RuntimeException("Target cell is occupied by a dome");
                } else throw new RuntimeException("Target cell is too high/low");
            } else throw new RuntimeException("Target cell is too far");
        } else throw new RuntimeException("Target cell out of board");
    }
}
