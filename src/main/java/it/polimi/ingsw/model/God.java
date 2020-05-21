package it.polimi.ingsw.model;

/**
 * Class God creates base methods for all gods
 */
public class God extends SimpleGod {
    /**
     * Method PlayerTurn to do at the beginning of the turn when is called BeforeMove in Match
     *
     * @param board of type Board
     * @param selectedCell of type Cell
     * @param x of type int
     * @param y of type int
     * @return int
     */
    public int PlayerTurn(Board board, Cell selectedCell, int x, int y) {
        return 1;
    }

    /**
     * Method CheckPlayerTurn check if is possible to do PlayerTurn()
     *
     * @param board of type Board
     * @param selectedCell of type Cell
     * @param x of type int
     * @param y of type int
     * @return int
     */
    public int CheckPlayerTurn(Board board, Cell selectedCell, int x, int y) {
        return 0;
    }

    /**
     * Method Move implements the basic move of the game
     *
     * @param board        of type Board
     * @param selectedCell of type Cell
     * @param x            of type int
     * @param y            of type int
     * @return int
     */
    public int Move(Board board, Cell selectedCell, int x, int y) {
        int moved = CheckMove(board, selectedCell, x, y);
        if (moved > 0) {
            board.getCell(x, y).setWorker(selectedCell.getWorker());
            selectedCell.getWorker().setLastMovement(board.getCell(x, y).getBuilding() - selectedCell.getBuilding());
            selectedCell.setWorker(null);
        }
        return moved;
    }

    /**
     * Method Building implements the basic build of the game
     *
     * @param board        of type Board
     * @param selectedCell of type Cell
     * @param x            of type int
     * @param y            of type int
     * @param typeBuild    of type int
     * @param turnNumber   of type int
     * @return int
     */
    public int Building(Board board, Cell selectedCell, int x, int y, int typeBuild, int turnNumber) {
        int built = CheckBuild(board, selectedCell, x, y);
        if (built > 0) {
            int building = board.getCell(x, y).getBuilding();
            if (building < 3)
                board.getCell(x, y).setBuilding(1);
            else if (building == 3)
                board.getCell(x, y).setDome(true);
            board.getCell(x, y).setBuiltBy(selectedCell.getWorker().getPlayer());
            board.getCell(x, y).setBuiltTurn(turnNumber);
        }
        return built;
    }

    /**
     * Method EnemyTurn called at the beginning of the enemy turn
     *
     * @param board      of type Board
     * @param turnPlayer of type Player
     * @param player     of type Player
     * @return int
     */
    public int EnemyTurn(Board board, Player turnPlayer, Player player) {
        return 0;
    }


    /**
     * Method WinCondition win condition extra to the base game
     *
     * @param board  of type Board
     * @param player of type Player
     * @return Player
     */
    public Player WinCondition(Board board, Player player) {
        return null;
    }

    /**
     * Method ResetGod reset god's values (only used in some gods)
     */
    public void ResetGod() {
        //no need to reset for all gods just need to implement in force move, multiple action, and debuff
    }

    /**
     * Method CheckMove checks if Move is possible
     *
     * @param board        of type Board
     * @param selectedCell of type Cell
     * @param x            of type int
     * @param y            of type int
     * @return int
     */
    public int CheckMove(Board board, Cell selectedCell, int x, int y) {
        if ((x < 5 && x >= 0) && (y < 5 && y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (selectedCell.IsNotHigh(board, x, y)) {
                    if (selectedCell.IsFreeDome(board, x, y)) {
                        if (!selectedCell.IsFreeWorker(board, x, y)) return -4;//cell is occupied
                    } else return -4;//cell is occupied
                } else return -3;//cell is too high
            } else return -2;//cell is too far
        } else return -1;//cell out of board
        return 1;//1 default return value, 2 need to repeat action
    }

    /**
     * Method CheckBuild checks if build is available
     *
     * @param board        of type Board
     * @param selectedCell of type Cell
     * @param x            of type int
     * @param y            of type int
     * @return int
     */
    public int CheckBuild(Board board, Cell selectedCell, int x, int y) {
        if ((x < 5 && x >= 0) && (y < 5 && y >= 0)) {
            if (selectedCell.isAdjacent(x, y)) {
                if (board.getCell(x, y).getWorker() == null) {
                    if (board.getCell(x, y).getDome()) return -4;//Cell occupied by a dome
                } else return -3;//Worker on the cell
            } else return -2; //Target cell is too far
        } else return -1;//Target cell out of board
        return 1;
    }

}
