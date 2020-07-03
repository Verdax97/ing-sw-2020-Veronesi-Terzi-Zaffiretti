package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class SimpleBoard
 */
public class SimpleBoard implements Serializable {

    /**
     * The Board.
     */
    public final int[][] board;
    /**
     * The Gods.
     */
    public final ArrayList<SimpleGod> gods;
    /**
     * The Players.
     */
    public final ArrayList<String> players;
    /**
     * The Workers.
     */
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
            this.board = board.copyValuesInNewSimpleBoard();
        else
            this.board = null;
        this.gods = gods;
        this.players = players;
        this.workers = workers;
    }
}
