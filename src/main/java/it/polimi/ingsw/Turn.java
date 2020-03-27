package it.polimi.ingsw;

import java.util.List;

public class Turn
{
    private int turnNumber;
    private List<Worker> workers;
    private int validMoves;

    public int GetTurn() {
        return turnNumber;
    }

    public void SetTurn(int turn) {
        this.turnNumber = turn;
    }

    public int GetValidMoves() {
        return validMoves;
    }

    public void SetValidMoves(int validMoves) {
        this.validMoves = validMoves;
    }

    public List<Worker> GetWorkers() {
        return workers;
    }

    public void SetWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public void StartTurn()
    {

    }
    public void Move()
    {

    }
    public void Build()
    {

    }
    public void CheckLostOthers()
    {

    }
    public void CheckWWinCondition()
    {

    }
}