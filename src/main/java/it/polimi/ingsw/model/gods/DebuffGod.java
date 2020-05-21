package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.Player;

/**
 * Class DebuffGod can modify the debuff values of the player worker
 *
 * @author Davide
 * Created on 21/05/2020
 */
public class DebuffGod extends God {
    protected boolean debuff = false;

    /**
     * Method DebuffWorker searches for player's workers and debuffs them
     *
     * @param board  of type Board
     * @param player of type Player
     */
    public void DebuffWorker(Board board, Player player) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getCell(i, j).getWorker() != null)
                    if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(player.getNickname())) {
                        board.getCell(i, j).getWorker().setDebuff(debuff);
                    }
            }
        }
    }

    /**
     * Method setDebuff sets the debuff of this DebuffGod object.
     *
     * @param debuff the debuff of this DebuffGod object.
     */
    public void setDebuff(boolean debuff) {
        this.debuff = debuff;
    }

}
