package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.God;
import it.polimi.ingsw.model.Player;

public class Pan extends God {
    @Override
    public Player WinCondition(Board board, Player player) {
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