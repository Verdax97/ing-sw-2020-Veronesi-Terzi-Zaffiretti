package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.Player;

public class DebuffGod extends God
{
    protected boolean debuff = false;

    public void DebuffWorker(Board board, Player player)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (board.getCell(i, j).getWorker() != null)
                    if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(player.getNickname()))
                    {
                        board.getCell(i, j).getWorker().setDebuff(debuff);
                    }
            }
        }
    }

    public void setDebuff(boolean debuff) {
        this.debuff = debuff;
    }

}
