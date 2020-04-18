package it.polimi.ingsw.model;

import java.awt.*;

public class God {
    protected String name;
    protected String description;
    protected Image img;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int PlayerTurn(Board board, Player player, int x, int y) { return 0; }

    public int Move(Board board, Cell selectedCell, int x, int y) { return 0; }

    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) { return 0; }

    public int EnemyTurn(Board board, Player turnPlayer, Player player) { return 0; }

    public void EnemyMove() { }

    public void EnemyBuilding() { }

    protected Cell SearchAnyPlayerWorker(Board board, Player player, int x, int y)
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (board.getCell(i, j).isAdjacent(x,y) && board.getCell(i, j).getWorker() != null)
                {
                    if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(player.getNickname()))
                    {
                        return board.getCell(i,j);
                    }
                }
            }
        }
        return null;
    }

    public Player WinCondition(Board board, Player player) { return null; }
}
