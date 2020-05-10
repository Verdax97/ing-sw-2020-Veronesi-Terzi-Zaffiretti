package it.polimi.ingsw.model;

//import java.awt.*;

public class God {
    protected String name;
    public String description;
    //protected Image img;

    public String getName() { return name; }

    public String getDescription() {return description; }

    public int PlayerTurn(Board board, Player player, Cell selectedCell, int x, int y) { return 0; }

    public int Move(Board board, Cell selectedCell, int x, int y) { return 0; }

    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) { return 0; }

    public int EnemyTurn(Board board, Player turnPlayer, Player player) { return 0; }


    public Player WinCondition(Board board, Player player) { return null; }

    public void ResetGod() {}
}
