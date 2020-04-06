package it.polimi.ingsw.model;

public class DebuffGod extends God
{
    protected boolean debuff = false;
    protected void DebuffWorker(Board board, Player player)
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
}
