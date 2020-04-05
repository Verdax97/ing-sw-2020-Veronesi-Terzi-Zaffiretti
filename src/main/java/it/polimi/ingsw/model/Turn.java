package it.polimi.ingsw.model;

import java.util.List;

public class Turn
{
    private int turnNumber;
    private List<Worker> workers;
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

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public Cell getSelectedCell() { return selectedCell; }

    public void setSelectedCell(Cell selectedCell) { this.selectedCell = selectedCell; }

    public void StartTurn()
    {

    }

    public void Move(Board board, int x, int y) throws RuntimeException
    {
        if (this.selectedCell.getWorker().getPlayer().getGodPower().Move(board, this.selectedCell, x, y) == 1)
            return;
        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)) {
            if (this.selectedCell.isAdjacent(x, y)) {
                if ((this.selectedCell.getBuilding() == board.getCell(x, y).getBuilding() + 1) || (this.selectedCell.getBuilding() >= board.getCell(x, y).getBuilding())) {
                    if (!board.getCell(x, y).getDome() && board.getCell(x, y).getWorker() == null) {
                        board.getCell(x, y).setWorker(this.selectedCell.getWorker());
                        this.selectedCell.getWorker().setLastMovement(board.getCell(x,y).getBuilding() - this.selectedCell.getBuilding());
                        this.selectedCell.setWorker(null);
                    } else throw new RuntimeException("Target cell is occupied");
                } else throw new RuntimeException("Target cell is too high/low");
            } else throw new RuntimeException("Target cell is too far");
        } else throw new RuntimeException("Target cell out of board");
    }

    public void Build(Board board, int x, int y) throws RuntimeException {

        if ((x < 5 & x >= 0) & (y < 5 & y >= 0)){
            if (this.selectedCell.isAdjacent(x, y)) {
                if (board.getCell(x, y).getWorker() == null) {
                    if (!this.selectedCell.getDome()) {
                        int building = board.getCell(x, y).getBuilding();
                        if (building < 3)
                            board.getCell(x, y).setBuilding(1);
                        else if (building == 3)
                            board.getCell(x, y).setDome(true);
                        board.getCell(x, y).setBuiltBy(this.selectedCell.getWorker().getPlayer());
                        board.getCell(x, y).setBuiltTurn(turnNumber);
                    } else { throw new RuntimeException("Target cell has a Dome, you cannot build"); }
                } else { throw new RuntimeException("Target cell has a worker on it, Baka");}
            } else { throw new RuntimeException("Target cell is too far!");}
        } else {throw new RuntimeException("Target cell is out of the board!");}
    }

    public void CheckLostOthers()
    {

    }
    public void CheckWinCondition()
    {

    }

}