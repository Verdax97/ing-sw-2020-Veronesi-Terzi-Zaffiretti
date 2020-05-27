package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;
import javafx.scene.image.Image;

/**
 * Class Athena implements Athena functionalities
 */
public class Athena extends DebuffGod {
    /**
     * Constructor Athena creates a new Athena instance.
     */
    public Athena() {
        this.name = "Athena";
        this.description = "Opponent’s Turn: If one of your Workers moved up on your last turn, opponent Workers cannot move up this turn.";
        img = new Image("Images/godCards/" + name + ".png");
    }

    /** @see it.polimi.ingsw.model.God#EnemyTurn(Board, Player, Player) */
    @Override
    public int EnemyTurn(Board board, Player turnPlayer, Player player) {
        debuff = false;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getCell(i, j).getWorker() != null)
                    if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(player.getNickname()) && board.getCell(i, j).getWorker().getLastMovement() > 0) {
                        debuff = true;
                    }
            }
        }
        DebuffWorker(board, turnPlayer);
        return (debuff) ? 1: 0;
    }
}