package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.Player;

public class Chrono extends God{

    public Chrono(){
        this.name = "Chrono";
        this.description = "Win Condition: you also win where there are al least five Complete Towers on the board";
    }

    @Override
    public Player WinCondition(Board board, Player player){
        return null;
    }
}
