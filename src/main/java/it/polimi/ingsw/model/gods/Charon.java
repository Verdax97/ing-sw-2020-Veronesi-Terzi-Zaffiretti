package it.polimi.ingsw.model.gods;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import it.polimi.ingsw.model.ForceMovementGods;
import it.polimi.ingsw.model.Player;

public class Charon extends ForceMovementGods {

    public Charon(){
        this.name = "Charon";
        this.description = "Before your worker moves, you may force a neighboring opponent Worker to the space directly on the other side of your Worker, if the space is unoccupied";
    }

    @Override
    public int PlayerTurn(Board board, Player player, Cell selectedCell, int x, int y)
    {
        if (board.getCell(x, y).getWorker() == null || board.getCell(x, y).getWorker().getPlayer().getNickname().equals(player.getNickname()))
            return -2;//no valid worker to make the move
        int dx = selectedCell.getPos()[0] - x;
        int dy = selectedCell.getPos()[1] - y;
        targetPosX = selectedCell.getPos()[0] + dx;
        targetPosY = selectedCell.getPos()[1] + dy;
        if (targetPosX < 0 || targetPosX > 4 || targetPosY < 0 || targetPosY > 4)
            return -7; //target space is out of board
        return MoveEnemy(board.getCell(x, y).getWorker(), board);
    }
}
