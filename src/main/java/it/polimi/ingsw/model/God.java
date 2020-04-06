package it.polimi.ingsw.model;

import java.awt.*;

public class God {
    protected String name;
    protected String description;
    protected Image img;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void PlayerTurn(Board board, Player player) { }

    public int Move(Board board, Cell selectedCell, int x, int y) { return 0; }

    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) { return 0; }

    public void EnemyTurn(Board board, Player turnPlayer, Player player) { }

    public void EnemyMove() { }

    public void EnemyBuilding() { }

    public Player WinCondition(Board board, Player player) { return null; }
}
