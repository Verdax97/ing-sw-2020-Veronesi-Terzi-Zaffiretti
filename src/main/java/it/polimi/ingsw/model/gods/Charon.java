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
    public int PlayerTurn(Board board, Player player, int x, int y)
    {
        Cell cell = SearchAnyPlayerWorker(board, player, x, y);
        if (cell == null || board.getCell(x, y).getWorker().getPlayer().getNickname().equals(player.getNickname()))
            return -2;//no valid worker to make the move
        int dx = cell.getPos()[0] - x;
        int dy = cell.getPos()[1] - y;
        targetPosX = cell.getPos()[0] + dx;
        targetPosY = cell.getPos()[1] + dy;
        if (targetPosX < 0 || targetPosX > 4 || targetPosY < 0 || targetPosY > 4)
            return -7; //target space is out of board
        return MoveEnemy(board.getCell(targetPosX, targetPosY).getWorker(), board);
    }
}
