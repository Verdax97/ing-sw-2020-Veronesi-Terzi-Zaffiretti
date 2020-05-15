package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Turn
{
    private int turnNumber;
    private ArrayList<Worker> workers;
    private int validMoves;
    private Cell selectedCell;

    public int getTurn() {
        return turnNumber;
    }

    public void setTurn(int turn) {
        this.turnNumber = turn;
    }

    public int getValidMoves() {
        return validMoves;
    }

    public void setValidMoves(int validMoves) {
        this.validMoves = validMoves;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Worker> workers) {
        this.workers = workers;
    }

    public Cell getSelectedCell() { return selectedCell; }

    public void setSelectedCell(Cell selectedCell) { this.selectedCell = selectedCell; }

    //todo modify with thew new system
    public boolean CheckLostMove(Player player, Board board) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getCell(i, j).getWorker() != null) {
                    if (board.getCell(i, j).getWorker().getPlayer().getNickname().compareTo(player.getNickname()) == 0) {
                        for (int k = -1; k < 2; k++){
                            for (int l = -1; l < 2; l++){
                                if (((i+k >= 0 && i+k <5)
                                && (j+l >= 0 && j+l <5))
                                && !board.getCell(i+k, j+l).getDome()
                                && board.getCell(i+k, j+l).IsFreeWorker(board,i+k, j+l)
                                && board.getCell(i, j).IsNotHigh(board, i+k, j+l)){
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean CheckLostBuild(Board board)
    {
        int[] pos = selectedCell.getPos();
        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                int x = pos[0] + i;
                int y = pos[1] + j;
                if ((x >= 0) && (x < 5) && (y >= 0) && (y < 5))
                {
                    if (i == 0 && j == 0)
                    {
                        //check for zeus
                        if (selectedCell.getWorker().getPlayer().getGodPower().getName().equals("Zeus")
                                && selectedCell.getBuilding() < 3)
                            return false;
                    }
                    else if (selectedCell.IsFreeDome(board, x, y) && selectedCell.IsFreeWorker(board, x, y))
                        return false;
                }
            }
        }
        return true;
    }

    public int StartTurn(ArrayList<Player> ActivePlayers, Player player, Board board)
    /*
    -1 Player lost
    0 Player neither lost nor won
    1 Player won
     */
    {
        // if last, player won.
        if (ActivePlayers.size() == 1 || CheckWinCondition(board, player) == player) {
            return 1;
        }

        for (Player p:ActivePlayers)
        {
            if (p != player) {
                p.getGodPower().EnemyTurn(board, player, p);
            }
        }
        // Check if we have to kill him
        if (CheckLostMove(player, board)) { return -1;}
        return 0;
    }

    /*
    1 all good needed for check cause 0 means useless

    */
    public int BeforeMove(Board board, int x, int y)
    {
        if (x == -5 && y == -5)
            return 1;
        return selectedCell.getWorker().getPlayer().getGodPower().PlayerTurn(board, selectedCell, x, y);
    }

    /*
    -1 Target cell out of board
    -2 Target cell is too far
    -3 Cell is too high
    -4 Cell is occupied by Dome/Worker
    -5 Unable to move enemy Worker
    -6 (Artemis) Same cell as the first one
    -7 (ForcedMovementGod) no space to move enemy worker or (Zeus) dome under worker
    */
    public int Move(Board board, int x, int y)
    {
        int moved = selectedCell.getWorker().getPlayer().getGodPower().Move(board, selectedCell, x, y);
        if (moved > 0){selectedCell = board.getCell(x, y);}
        return moved;
    }

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

    public Player CheckWinCondition(Board board, Player player)
    {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getCell(i, j).getWorker() != null &&
                        board.getCell(i, j).getWorker().getPlayer() != null &&
                        board.getCell(i, j).getWorker().getPlayer().getNickname().compareTo(player.getNickname()) == 0 &&
                        board.getCell(i, j).getBuilding() == 3 && board.getCell(i, j).getWorker().getLastMovement() != 0)
                    return player;
            }
        }
        return player.getGodPower().WinCondition(board, player);
    }
}