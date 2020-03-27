package it.polimi.ingsw;

public class Match
{
    private Cell[][] table;
    private Turn turn;
    private SetupMatch setup;
    public void StartMatch()
    {
        this.table = new Cell[5][5];
        this.setup = new SetupMatch();
        this.turn = new Turn();
    }
    public void NextTurn() {
        turn.SetTurn(turn.GetTurn() + 1);
    }
}
