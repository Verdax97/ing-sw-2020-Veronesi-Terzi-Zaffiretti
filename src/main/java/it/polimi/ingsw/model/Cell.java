package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Cell contains information about a single element of the game board
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
     * Constructor Cell creates a new Cell instance.
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
     * Method getWorker return the worker of this Cell object.
     *
     * @return worker of type Worker
     */
    public Worker getWorker()
    {
        return this.worker;
    }

    /**
     * Method setWorker sets the worker of this Cell object.
     *
     * @param worker of type Worker
     */
    public void setWorker(Worker worker)
    {
        this.worker = worker;
    }

    /**
     * Method getBuilding returns the building of this Cell object.
     *
     * @return building of type int
     */
    public int getBuilding()
    {
        return building;
    }

    /**
     * Method setBuilding sets the building of this Cell object.
     *
     * @param diff of type int
     */
    public void setBuilding(int diff)
    {
        this.building += diff;
    }

    /**
     * Method getDome returns the dome of this Cell object.
     *
     * @return dome of type boolean
     */
    public boolean getDome()
    {
        return this.dome;
    }

    /**
     * Method setDome sets the dome of this Cell object.
     *
     * @param dome of type boolean
     */
    public void setDome(boolean dome)
    {
        this.dome = dome;
    }

    /**
     * Method getPos returns the array arr which contains the coordX, coordY of this Cell object.
     *
     * @return arr of type int[]
     */
    public int [] getPos()
    {
        int[] arr={this.coordX, this.coordY};
        return arr;
    }

    /**
     * Method setPos sets the position of this Cell object.
     *
     * @param x of type int
     * @param y of type int
     */
    public void setPos(int x, int y)
    {
        this.coordX = x;
        this.coordY = y;
    }

    /**
     * Method isAdjacent checks if another cell is adjacent of this Cell object.
     *
     * @param x of type int
     * @param y of type int
     * @return boolean value
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
     * Method getBuiltTurn return the builtTurn of this Cell object.
     *
     * @return builtTurn of type int
     */
    public int getBuiltTurn()
    {
        return builtTurn;
    }

    /**
     * Method setBuiltTurn sets the builtTurn of this Cell object.
     *
     * @param turn of type int
     */
    public void setBuiltTurn(int turn)
    {
        this.builtTurn = turn;
    }

    /**
     * Method getBuiltBy returns the builtBy of this Cell object.
     *
     * @return builtby of type Player
     */
    public Player getBuiltBy()
    {
        return this.builtBy;
    }

    /**
     * Method setBuiltBy sets the builtBy of this Cell object.
     *
     * @param builder of type Player
     */
    public void setBuiltBy(Player builder)
    {
        this.builtBy = builder;
    }

    /**
     * Method isNotHigh checks if this Cell object is high.
     *
     * @param board of type Board
     * @param x     of type int
     * @param y     of type int
     * @return boolean value
     */
    public boolean IsNotHigh(Board board, int x, int y)
    {
        return (((this.getBuilding() == board.getCell(x,y).getBuilding()-1) && !this.getWorker().isDebuff()) || (this.getBuilding() >= board.getCell(x, y).getBuilding()));
    }

    /**
     * Method isFreeDome checks if this Cell object has a dome.
     *
     * @param board the board
     * @param x     the x
     * @param y     the y
     * @return boolean value
     */
    public boolean IsFreeDome(Board board, int x, int y)
    {
        return !board.getCell(x, y).getDome();
    }

    /**
     * Method isFreeWorker checks if this Cell object has a worker.
     *
     * @param board of type Board
     * @param x     of type int
     * @param y     of type int
     * @return boolean value
     */
    public boolean IsFreeWorker(Board board, int x, int y)
    {
        return board.getCell(x, y).getWorker() == null;
    }

}
