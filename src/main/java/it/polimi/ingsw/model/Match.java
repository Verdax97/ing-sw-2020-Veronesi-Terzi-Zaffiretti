package it.polimi.ingsw.model;

public class Match
{
        private Board table;
        private Turn turn;
        private SetupMatch setup;
        public void StartMatch()
        {
        this.table = new Board();
        this.setup = new SetupMatch();
        this.turn = new Turn();
    }
    public void NextTurn() {
        turn.SetTurn(turn.GetTurn() + 1);
    }
}
