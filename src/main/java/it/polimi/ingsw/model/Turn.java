package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Turn
 */
public class Turn {
    private int turnNumber;
    private ArrayList<Worker> workers;
    private int validMoves;
    private Cell selectedCell;

    /**
     * Method getTurn returns the turn of this Turn object.
     *
     * @return the turn (type int) of this Turn object.
     */
    public int getTurn() {
        return turnNumber;
    }

    /**
     * Method setTurn sets the turn of this Turn object.
     *
     * @param turn the turn of this Turn object.
     */
    public void setTurn(int turn) {
        this.turnNumber = turn;
    }

    /**
     * Method getValidMoves returns the validMoves of this Turn object.
     *
     * @return the validMoves (type int) of this Turn object.
     */
    public int getValidMoves() {
        return validMoves;
    }

    /**
     * Method setValidMoves sets the validMoves of this Turn object.
     *
     * @param validMoves the validMoves of this Turn object.
     */
    public void setValidMoves(int validMoves) {
        this.validMoves = validMoves;
    }

    /**
     * Method getWorkers returns the workers of this Turn object.
     *
     * @return the workers (type List<Worker>) of this Turn object.
     */
    public List<Worker> getWorkers() {
        return workers;
    }

    /**
     * Method setWorkers sets the workers of this Turn object.
     *
     * @param workers the workers of this Turn object.
     */
    public void setWorkers(ArrayList<Worker> workers) {
        this.workers = workers;
    }

    /**
     * Method getSelectedCell returns the selectedCell of this Turn object.
     *
     * @return the selectedCell (type Cell) of this Turn object.
     */
    public Cell getSelectedCell() {
        return selectedCell;
    }

    /**
     * Method setSelectedCell sets the selectedCell of this Turn object.
     *
     * @param selectedCell the selectedCell of this Turn object.
     */
    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    /**
     * Check lost move boolean.
     *
     * @param player  the player
     * @param board   the board
     * @param workers the workers
     * @return the boolean
     */
    public boolean CheckLostMove(Player player, Board board, ArrayList<int[]> workers) {
        boolean flag = false;
        for (int[] coords : workers) {
            int i = coords[0];
            int j = coords[1];
            if (CheckAround(board, i, j, player.getGodPower(), 0).size() == 0) {
                if (CheckAround(board, i, j, player.getGodPower(), 1).size() == 0)
                    flag = true;
                else
                    return false;
            } else
                return false;
        }
        return flag;
    }

    /**
     * Check lost build boolean.
     *
     * @param board the board
     * @return the boolean
     */
    public boolean CheckLostBuild(Board board) {
        ArrayList<int[]> buildPossibilities = CheckAround(board, getSelectedCell().getPos()[0], getSelectedCell().getPos()[1], selectedCell.getWorker().getPlayer().getGodPower(), 2);
        return buildPossibilities.size() <= 0;
    }

    /**
     * Start turn int.
     *
     * @param ActivePlayers the active players
     * @param player        the player
     * @param board         the board
     * @param workers       the workers
     * @return the int
     */
    public int StartTurn(ArrayList<Player> ActivePlayers, Player player, Board board, ArrayList<int[]> workers)
    /*
    -1 Player lost
    0 Player neither lost nor won
    1 Player won
     */ {
        // if last, player won.
        if (ActivePlayers.size() == 1 || CheckWinCondition(board, player, workers) == player) {
            return 1;
        }

        for (Player p : ActivePlayers) {
            if (p != player) {
                p.getGodPower().EnemyTurn(board, player, p);
            }
        }
        // Check if we have to kill him
        if (CheckLostMove(player, board, workers)) { return -1;}
        return 0;
    }

    /**
     * Before move int.
     *
     * @param board the board
     * @param x     the x
     * @param y     the y
     * @return the int
     */
/*
    1 all good needed for check cause 0 means useless

    */
    public int BeforeMove(Board board, int x, int y) {
        if (x == -5 && y == -5)
            return 1;
        return selectedCell.getWorker().getPlayer().getGodPower().PlayerTurn(board, selectedCell, x, y);
    }

    /**
     * Move int.
     *
     * @param board the board
     * @param x     the x
     * @param y     the y
     * @return the int
     */
/*
    -1 Target cell out of board
    -2 Target cell is too far
    -3 Cell is too high
    -4 Cell is occupied by Dome/Worker
    -5 Unable to move enemy Worker
    -6 (Artemis) Same cell as the first one
    -7 (ForcedMovementGod) no space to move enemy worker or (Zeus) dome under worker
    */
    public int Move(Board board, int x, int y) {
        int moved = selectedCell.getWorker().getPlayer().getGodPower().Move(board, selectedCell, x, y);
        if (moved > 0){selectedCell = board.getCell(x, y);}
        return moved;
    }

    /**
     * Build int.
     *
     * @param board     the board
     * @param x         the x
     * @param y         the y
     * @param typeBuild the type build
     * @return the int
     */
/*
    -5 (Hephaestus) Not same as last built cell
    -6 (Hephaestus) Building is > 2
    -7 (Zeus) third level build forbidden
    -8 (Demeter) same as last built
    -9 (Hestia) cell is a perimeter space
    */
    public int Build(Board board, int x, int y, int typeBuild) {
        return selectedCell.getWorker().getPlayer().getGodPower().Building(board, selectedCell, x, y, typeBuild, turnNumber);

    }

    /**
     * Check win condition player.
     *
     * @param board   the board
     * @param player  the player
     * @param workers the workers
     * @return the player
     */
    public Player CheckWinCondition(Board board, Player player, ArrayList<int[]> workers) {
        for (int[] coords : workers) {
            int i = coords[0];
            int j = coords[1];
            if (board.getCell(i, j).getBuilding() == 3 && board.getCell(i, j).getWorker().getLastMovement() > 0)
                return player;
        }
        return player.getGodPower().WinCondition(board, player);
    }

    /**
     * Check around array list.
     *
     * @param board the board
     * @param tempx the tempx
     * @param tempy the tempy
     * @param god   the god
     * @param phase the phase
     * @return the array list
     */
    public ArrayList<int[]> CheckAround(Board board, int tempx, int tempy, God god, int phase) {
        ArrayList<int[]> arr = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int x = tempx + i;
                int y = tempy + j;
                if ((x >= 0) && (x < 5) && (y >= 0) && (y < 5)) {
                    int ret = CheckPhase(board, tempx, tempy, god, x, y, phase);
                    if (ret > 0) {
                        arr.add(new int[]{x, y});//add to the list of possible moves
                    }
                }
            }
        }
        return arr;
    }

    /**
     * Check phase int.
     *
     * @param board the board
     * @param tempx the tempx
     * @param tempy the tempy
     * @param god   the god
     * @param x     the x
     * @param y     the y
     * @param phase the phase
     * @return the int
     */
    public int CheckPhase(Board board, int tempx, int tempy, God god, int x, int y, int phase) {
        int ret = -1;
        if (phase == 0)//beforeMove
            ret = god.CheckPlayerTurn(board, board.getCell(tempx, tempy), x, y);
        if (phase == 1)//move
            ret = god.CheckMove(board, board.getCell(tempx, tempy), x, y);
        if (phase == 2)//build
            ret = god.CheckBuild(board, board.getCell(tempx, tempy), x, y);
        return ret;//add to the list of possible moves
    }
}