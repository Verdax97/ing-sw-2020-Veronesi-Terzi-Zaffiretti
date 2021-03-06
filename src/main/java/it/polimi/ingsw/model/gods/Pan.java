package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.Player;

/**
 * Class Pan implements Pan functionalities
 */
public class Pan extends God {

    /**
     * Constructor Pan creates a new Pan instance.
     */
    public Pan(){
        this.name = "Pan";
        this.description = "Win Condition: you also win if your worker moves down two or more levels";
    }

    /** @see it.polimi.ingsw.model.God#winCondition(Board, Player)  */
    @Override
    public Player winCondition(Board board, Player player) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getCell(i, j).getWorker() != null) {
                    if (board.getCell(i, j).getWorker().getPlayer() != null) {
                        if (board.getCell(i, j).getWorker().getPlayer().getNickname().compareTo(player.getNickname()) == 0) {
                            if (board.getCell(i, j).getWorker().getLastMovement() == -2) {
                                return player;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}