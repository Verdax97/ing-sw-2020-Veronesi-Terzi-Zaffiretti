package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.DebuffGod;
import it.polimi.ingsw.model.Player;

public class Prometheus extends DebuffGod
{
    public Prometheus()
    {
        this.name = "Prometheus";
        this.description = "Your Turn: If your Worker does not move up, it may build both before and after moving";
    }
    @Override
    public void PlayerTurn(Board board, Player player)
    {
        debuff = false;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(player.getNickname()) && board.getCell(i, j).getWorker().getLastMovement() > 0)
                {
                    debuff = true;
                }
            }
        }
        DebuffWorker(board, player);
    }
}
