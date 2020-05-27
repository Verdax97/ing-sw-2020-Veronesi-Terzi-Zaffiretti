package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cell;
import javafx.scene.image.Image;

public class Minotaur extends ForceMovementGods
{
    public Minotaur()
    {
        this.name = "Minotaur";
        this.description = "Your Move: Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.";
    }

    @Override
    public int Move(Board board, Cell selectedCell, int x, int y)
    {
        int dx = x - selectedCell.getPos()[0];
        int dy = y - selectedCell.getPos()[1];
        targetPosX = x + dx;
        targetPosY = y + dy;
        return ForceMove(board, selectedCell, x, y);
    }
}
