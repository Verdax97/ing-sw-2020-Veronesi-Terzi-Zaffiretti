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
        int fullTowers = 0;
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                if (board.getCell(i, j).getBuilding()==3 && board.getCell(i, j).getDome()==true){
                    fullTowers++;
                }
            }
        }
        if (fullTowers>=5)
            return player;
        else return null;
    }
}
