package it.polimi.ingsw.model;

public class Cell
{
    private Worker worker;
    private int building;
    private boolean dome;
    private int coordX;
    private int coordY;
    private Player builtBy;
    private int builtTurn;

    public Cell (int x, int y)
    {
        this.worker = null;
        this.building = 0;
        this.dome = false;
        this.coordX = x;
        this.coordY = y;
        this.builtBy = null;
        this.builtTurn = 0;
    }

    public Worker getWorker()
    {
        return this.worker;
    }

    public void setWorker(Worker worker)
    {
        this.worker = worker;
    }

    public int getBuilding()
    {
        return building;
    }

    public void setBuilding(int diff)
    {
        this.building += diff;
    }

    public boolean getDome()
    {
        return this.dome;
    }

    public void setDome(boolean dome)
    {
        this.dome = dome;
    }

    public int [] getPos()
    {
        int[] arr={this.coordX, this.coordY};
        return arr;
    }

    public void setPos(int x, int y)
    {
        this.coordX = x;
        this.coordY = y;
    }

    public boolean isAdjacent(int x, int y)
    {
        if (this.coordX - 1 <= x && this.coordX + 1 >= x)
        {
            if (this.coordY == y || this.coordY == y+1 || this.coordY == y-1)
            {
                return !(this.coordX == x && this.coordY == y);
            }
            else return false;
        }
        else return false;
    }

    public int getBuiltTurn()
    {
        return builtTurn;
    }

    public void setBuiltTurn(int turn)
    {
        this.builtTurn = turn;
    }

    public Player getBuiltBy()
    {
        return this.builtBy;
    }

    public void setBuiltBy(Player builder)
    {
        this.builtBy = builder;
    }
}
