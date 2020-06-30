package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class SimpleBoard
 */
public class SimpleBoard implements Serializable {

    public final int[][] board;
    public final ArrayList<SimpleGod> gods;
    public final ArrayList<String> players;
    public final ArrayList<int[]> workers;

    /**
     * Constructor SimpleBoard creates a new SimpleBoard instance.
     *
     * @param board   the board
     * @param gods    the gods
     * @param players the players
     * @param workers the workers
     */
    public SimpleBoard(Board board, ArrayList<SimpleGod> gods, ArrayList<String> players, ArrayList<int[]> workers) {
        if (board != null)
            this.board = board.CopyValuesInNewSimpleBoard();
        else
            this.board = null;
        this.gods = gods;
        this.players = players;
        this.workers = workers;
    }
}
