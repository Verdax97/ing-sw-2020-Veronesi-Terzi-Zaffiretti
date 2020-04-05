package it.polimi.ingsw.model;

import java.util.List;

public class Turn
{
    private int turnNumber;
    private List<Worker> workers;
    private int validMoves;
    private Cell selectedCell;

    public int getTurn() {
        return turnNumber;
    }

    public void setTurn(int turn) {
        this.turnNumber = turn;
    }

    public int getValidMoves() {
        return validMoves;
    }

    public void setValidMoves(int validMoves) {
        this.validMoves = validMoves;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public Cell getSelectedCell() { return selectedCell; }

    public void setSelectedCell(Cell selectedCell) { this.selectedCell = selectedCell; }

    public void StartTurn()
    {

    }
    public void Move(Board board, int x, int y) throws RuntimeException
    {
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0))
        {
            if (this.selectedCell.isAdjacent(x, y))
            {
                board.getCell(x,y).setWorker(this.selectedCell.getWorker());
                this.selectedCell.setWorker(null);
            }
            else { throw new RuntimeException("Target cell is too far");}
        }
        else { throw new RuntimeException("Target cell out of board");}
    }
    public void Build(Worker worker, int x, int y)
    {

    }
    public void CheckLostOthers()
    {

    }
    public void CheckWinCondition()
    {

    }

}