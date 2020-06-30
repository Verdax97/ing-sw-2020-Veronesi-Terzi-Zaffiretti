package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Cell
 */
public class Cell implements Serializable {
    private Worker worker;
    private int building;
    private boolean dome;
    private int coordX;
    private int coordY;
    private Player builtBy;
    private int builtTurn;

    /**
     * Instantiates a new Cell.
     *
     * @param x the x
     * @param y the y
     */
    public Cell(int x, int y)
    {
        this.worker = null;
        this.building = 0;
        this.dome = false;
        this.coordX = x;
        this.coordY = y;
        this.builtBy = null;
        this.builtTurn = 0;
    }

    /**
     * Gets worker.
     *
     * @return the worker
     */
    public Worker getWorker()
    {
        return this.worker;
    }

    /**
     * Sets worker.
     *
     * @param worker the worker
     */
    public void setWorker(Worker worker)
    {
        this.worker = worker;
    }

    /**
     * Gets building.
     *
     * @return the building
     */
    public int getBuilding()
    {
        return building;
    }

    /**
     * Sets building.
     *
     * @param diff the diff
     */
    public void setBuilding(int diff)
    {
        this.building += diff;
    }

    /**
     * Gets dome.
     *
     * @return the dome
     */
    public boolean getDome()
    {
        return this.dome;
    }

    /**
     * Sets dome.
     *
     * @param dome the dome
     */
    public void setDome(boolean dome)
    {
        this.dome = dome;
    }

    /**
     * Get pos int [ ].
     *
     * @return the int [ ]
     */
    public int [] getPos()
    {
        int[] arr={this.coordX, this.coordY};
        return arr;
    }

    /**
     * Sets pos.
     *
     * @param x the x
     * @param y the y
     */
    public void setPos(int x, int y)
    {
        this.coordX = x;
        this.coordY = y;
    }

    /**
     * Is adjacent boolean.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
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

    /**
     * Gets built turn.
     *
     * @return the built turn
     */
    public int getBuiltTurn()
    {
        return builtTurn;
    }

    /**
     * Sets built turn.
     *
     * @param turn the turn
     */
    public void setBuiltTurn(int turn)
    {
        this.builtTurn = turn;
    }

    /**
     * Gets built by.
     *
     * @return the built by
     */
    public Player getBuiltBy()
    {
        return this.builtBy;
    }

    /**
     * Sets built by.
     *
     * @param builder the builder
     */
    public void setBuiltBy(Player builder)
    {
        this.builtBy = builder;
    }

    /**
     * Is not high boolean.
     *
     * @param board the board
     * @param x     the x
     * @param y     the y
     * @return the boolean
     */
    public boolean IsNotHigh(Board board, int x, int y)
    {
        return (((this.getBuilding() == board.getCell(x,y).getBuilding()-1) && !this.getWorker().isDebuff()) || (this.getBuilding() >= board.getCell(x, y).getBuilding()));
    }

    /**
     * Is free dome boolean.
     *
     * @param board the board
     * @param x     the x
     * @param y     the y
     * @return the boolean
     */
    public boolean IsFreeDome(Board board, int x, int y)
    {
        return !board.getCell(x, y).getDome();
    }

    /**
     * Is free worker boolean.
     *
     * @param board the board
     * @param x     the x
     * @param y     the y
     * @return the boolean
     */
    public boolean IsFreeWorker(Board board, int x, int y)
    {
        return board.getCell(x, y).getWorker() == null;
    }

}
