package it.polimi.ingsw;
import java.util.Arrays;
public class Cell
{
    private Worker worker;
    private int building;
    private boolean dome;
    private int coordX;
    private int coordY;
    private Player builtBy;
    private int builtTurn;

    public Worker GetWorker()
    {
        return this.worker;
    }

    public void SetWorker(Worker worker)
    {
        this.worker = worker;
    }

    public int GetBuilding()
    {
        return building;
    }

    public void SetBuilding(int diff)
    {
        this.building += diff;
    }

    public boolean GetDome()
    {
        return this.dome;
    }

    public void SetDome(boolean dome)
    {
        this.dome = dome;
    }

    public int [] GetPos()
    {
        int[] arr={this.coordX, this.coordY};
        return arr;
    }

    public void SetPos(int x, int y)
    {
        this.coordX = x;
        this.coordY = y;
    }

    public int GetBuiltTurn()
    {
        return builtTurn;
    }

    public void SetBuiltTurn(int turn)
    {
        this.builtTurn = turn;
    }

    public Player GetBuiltBy()
    {
        return this.builtBy;
    }

    public void SetBuiltBy(Player builder)
    {
        this.builtBy = builder;
    }
}
