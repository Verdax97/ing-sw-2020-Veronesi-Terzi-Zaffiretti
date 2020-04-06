package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.DebuffGod;
import it.polimi.ingsw.model.Player;

public class Athena extends DebuffGod
{
    public Athena()
    {
        this.name = "Athena";
        this.description = "Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
    }
    @Override
    public void EnemyTurn(Board board, Player turnPlayer, Player player)
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
        DebuffWorker(board, turnPlayer);
    }
}