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

    public boolean CheckLostMove(Player player, Board board) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getCell(i, j).getWorker() != null) {
                    if (board.getCell(i, j).getWorker().getPlayer().getNickname().compareTo(player.getNickname()) == 0) {
                        for (int k = -1; k < 2; k++){
                            for (int l = -1; l < 2; l++){
                                if (((i+k >= 0 & i+k <5)
                                && (j+l >= 0 & j+l <5))
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

    public boolean CheckLostBuild(Player player, Board board){
        // Controlla che almeno una delle celle adiacenti sia costruibile o che lo sia la propria se hai Zeus
        int[] pos = this.selectedCell.getPos();
        for (int i = pos[0]-1; i < pos[0]+2; i++ ){
            for (int j = pos[1]-1; j < pos[1]+2; j++ ){
                if (((i >= 0 & i < 5)
                   && (j >= 0 & j < 5)
                   && this.selectedCell.isAdjacent(i,j)
                   && this.selectedCell.IsNotHigh(board, i, j)
                   && this.selectedCell.IsFreeDome(board, i, j)
                   && this.selectedCell.IsFreeWorker(board, i, j))
                   ||
                   (this.selectedCell.getWorker().getPlayer().getGodPower().name == "Zeus"
                   && this.selectedCell.getBuilding() < 3)

                ){return false;}
            }
        }
        return true;
    }

    public int StartTurn(ArrayList<Player> ActivePlayers, Player player, Board board, int x, int y, boolean godPower)
    /*0
    -5 no space to move enemy worker
    -2 (charon) no worker available
    -1 Player lost
    0 Player neither lost nor won
    1 Player won
     */
    {
        // if last, player won.
        if (ActivePlayers.size() == 1){
            return 1;
        }
        // Check if we have to kill him
        if (this.CheckLostMove(player, board)) { return -1;}
        // Check if we want to use the god power
        if (godPower){player.getGodPower().PlayerTurn(board, player, x, y);}
        for (Player p:ActivePlayers)
        {
            p.getGodPower().EnemyTurn(board, player, p);
        }
        return 0;
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
        int a = this.selectedCell.getWorker().getPlayer().getGodPower().Move(board, this.selectedCell, x, y);
        if (a != 0)//must update Selected cell to get the same reference to the builder
            return a;//1 default return value, 2 need to repeat action
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (selectedCell.IsNotHigh(board, x, y)) {
                    if (selectedCell.IsFreeDome(board, x, y)) {
                        if (selectedCell.IsFreeWorker(board, x, y)) {
                            board.getCell(x, y).setWorker(selectedCell.getWorker());
                            selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
                            selectedCell.setWorker(null);
                        } else return -4;//cell is occupied
                    } else return -4;//cell is occupied
                } else return -3;//cell is too high
            } else return-2;//cell is too far
        } else return -1;//cell out of board
        return 1;//1 default return value, 2 need to repeat action
    }

    /*
    -5 (Hephaestus) Not same as last built cell
    -6 (Hephaestus) Building is > 2
    -7 (Zeus) third level build forbidden
    -8 (Demeter) same as last built
    -9 (Hestia) cell is a perimeter space
    */
    public int Build(Board board, int x, int y, int typeBuild) {
        //vista la classe Multiple Action God, necessitiamo veramente della variabile a di controllo???
        int a = selectedCell.getWorker().getPlayer().getGodPower().Building(board, selectedCell, x, y, typeBuild, turnNumber);
        if (a != 0)
            return a;//1 default return value, 2 need to repeat action
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)){
            if (selectedCell.isAdjacent(x, y)) {
                if (board.getCell(x, y).getWorker() == null) {
                    if (board.getCell(x, y).getDome()) {
                        int building = board.getCell(x, y).getBuilding();
                        if (building < 3)
                            board.getCell(x, y).setBuilding(1);
                        else if (building == 3)
                            board.getCell(x, y).setDome(true);
                        board.getCell(x, y).setBuiltBy(this.selectedCell.getWorker().getPlayer());
                        board.getCell(x, y).setBuiltTurn(turnNumber);
                    }else return -4;//Cell occupied by a dome
                } else return -3;//Worker on the cell
            } else return -2; //Target cell is too far
        } else return -1;//Target cell out of board
        return 1;//1 default return value, 2 need to repeat action
    }

    public Player CheckWinCondition(Board board, Player player)
    {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getCell(i, j).getWorker() != null) {
                    if (board.getCell(i, j).getWorker().getPlayer() != null) {
                        if (board.getCell(i, j).getWorker().getPlayer().getNickname().compareTo(player.getNickname()) == 0) {
                            if (board.getCell(i, j).getBuilding() == 3 & board.getCell(i, j).getWorker().getLastMovement() != 0) {
                                return player;
                            }
                        }
                    }
                }
            }
        }
        return player.getGodPower().WinCondition(board, player);
    }
}