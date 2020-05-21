package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Class Match controls the received message and try to make the action, modifying the lastAction variable
 */
public class Match extends Observable {
    private Board board;
    private final Turn turn;
    private final SetupMatch setup;

    /**
     * Method getPlayerTurn returns the playerTurn of this Match object.
     *
     *
     *
     * @return the playerTurn (type Player) of this Match object.
     */
    public Player getPlayerTurn() {
        return playerTurn;
    }

    private Player playerTurn;
    private Player winner;
    private final ErrorHandler errorHandler;
    private int nPlayer = 0;
    private int lastAction = 0;//0 all right < 0 some problem

    /**
     * Method getMsgError returns the msgError of this Match object.
     *
     *
     *
     * @return the msgError (type String) of this Match object.
     */
    public String getMsgError() {
        return msgError;
    }

    private String msgError;

    /**
     * Constructor Match creates a new Match instance.
     *
     * @param nicks of type ArrayList<String>
     */
    public Match(ArrayList<String> nicks) {
        this.board = new Board();
        this.turn = new Turn();
        this.setup = new SetupMatch();
        this.setup.CreatePlayersFromNickname(nicks);
        this.winner = new Player("");
        this.errorHandler = new ErrorHandler();
    }

    /**
     * Method StartGame initialize the variables for starting the game and send the start message to the players
     */
    public void StartGame() {
        nPlayer = setup.getPlayers().size() - 1;
        playerTurn = setup.getPlayers().get(nPlayer);
        CreateMsgPacket(Messages.choseGods, PrintGods(setup.getGodList()));
    }

    /**
     * Method PrintGods easily converts an arrayList of Gods into a String to be sent to players
     *
     * @param gods of type ArrayList<God>
     * @return String
     */
    private String PrintGods(ArrayList<God> gods) {
        StringBuilder printable = new StringBuilder();
        for (int i = 0; i < gods.size(); i++) {
            printable.append(i).append(") ");
            printable.append(gods.get(i).name).append(": ");
            printable.append(gods.get(i).description);
            printable.append("\n");
        }
        //printable.append("\n");
        return printable.toString();
    }

    /**
     * Method getLastAction returns the lastAction of this Match object.
     *
     *
     *
     * @return the lastAction (type int) of this Match object.
     */
    public int getLastAction() {
        return lastAction;
    }

    /**
     * Method PickGod add the selected god from the list to the picked list
     *
     * @param msgPacket of type MsgToServer
     */
    public void PickGod(MsgToServer msgPacket) {
        int value = msgPacket.x;
        if (value < 0 || value >= setup.getGodList().size()) {
            msgError = "Error Can't pick that god, try another value\n";
            lastAction = -1;
            CreateMsgPacket(msgError + Messages.choseGods, PrintGods(setup.getGodList()));
            return;
        }
        setup.AddGodPicked(setup.getGodList().get(value));
        //setup.getGodList().remove(value);
        lastAction = 1;
        if (getSetup().getGodPicked().size() == setup.getPlayers().size()) {
            NextPlayer();

            CreateMsgPacket(Messages.choseYourGod, PrintGods(setup.getGodPicked()));
            return;
        }
        CreateMsgPacket(Messages.choseGods, PrintGods(setup.getGodList()));
    }

    /**
     * Method SelectPlayerGod select the player god from the list
     *
     * @param msgPacket of type MsgToServer
     */
    public void SelectPlayerGod(MsgToServer msgPacket) {
        int value = msgPacket.x;
        if (value < 0 || value >= setup.getGodPicked().size()) {
            msgError = "Error Can't pick that god, try another value\n";
            lastAction = -1;
            CreateMsgPacket(msgError + Messages.choseYourGod, PrintGods(setup.getGodPicked()));
            return;
        }
        playerTurn.setGodPower(setup.PickGod(value));
        lastAction = 1;
        NextPlayer();
        if (getSetup().getGodPicked().size() == 0)
            CreateMsgPacket(Messages.placeWorkers, "Wait");
        else
            CreateMsgPacket(Messages.choseYourGod, PrintGods(setup.getGodPicked()));
    }

    /**
     * Method PlaceWorker place player's workers on the board
     *
     * @param msgPacket of type MsgToServer
     */
    public void PlaceWorker(MsgToServer msgPacket) {
        int x = msgPacket.x, y = msgPacket.y;
        int x2 = msgPacket.targetX, y2 = msgPacket.targetY;
        if ((x < 0 || x > 4 || y < 0 || y > 4) || board.getCell(x, y).getWorker() != null
                || (x2 < 0 || x2 > 4 || y2 < 0 || y2 > 4) || board.getCell(x2, y2).getWorker() != null
                || (x == x2 && y == y2)) {
            msgError = "Error Can't place a worker here, try another value\n";
            msgError += Messages.placeWorkers;
            lastAction = -1;
        } else {
            board.getCell(x, y).setWorker(new Worker());
            board.getCell(x, y).getWorker().setPlayer(playerTurn);
            board.getCell(x2, y2).setWorker(new Worker());
            board.getCell(x2, y2).getWorker().setPlayer(playerTurn);
            int found = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (getBoard().getCell(i,j).getWorker() != null)
                        found++;
                }
            }
            NextPlayer();
            if (found == getSetup().getPlayers().size() * 2) {
                lastAction = 2;
                msgError = Messages.startTurn;
            } else {
                msgError = Messages.placeWorkers;
                lastAction = 1;
            }
        }
        CreateMsgPacket(msgError, "Wait");
    }

    /**
     * Method StartTurn checks win and lose conditions for players
     */
    public void StartTurn() {
        lastAction = 1;
        String alt = "Wait";
        lastAction = turn.StartTurn(setup.getPlayers(), playerTurn, board);
        if (lastAction == 0)//the game must go on
        {
            ArrayList<int[]> workers = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board.getCell(i, j).getWorker() != null) {
                        if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(playerTurn.getNickname()))
                            workers.add(new int[]{i, j});
                    }
                }
            }
            alt = PrintPossibilities(workers);
            msgError = Messages.selectWorker;
        }
        if (lastAction == 1)//you won
        {
            msgError = "EndGame Winner winner chicken dinner!";
            PlayerWin(playerTurn.getNickname());
            return;
        }
        if (lastAction == -1)//you lose
        {
            PlayerLost("Error You Lost (can't move worker)", playerTurn.getNickname() + "" +
                    " lost because he can't move his workers");
            return;
        }
        if (lastAction < -1)
            msgError = errorHandler.GetErrorSetup(lastAction) + "\n" + Messages.startTurn;
        CreateMsgPacket(msgError, alt);
    }


    /**
     * Method SelectWorker selects player worker for this turn
     *
     * @param msgPacket of type MsgToServer
     */
    public void SelectWorker(MsgToServer msgPacket) {
        lastAction = 1;
        String alt = "Wait";
        ArrayList<int[]> workers = new ArrayList<>();
        if (msgPacket.x >= 0 & msgPacket.x < 2) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board.getCell(i, j).getWorker() != null) {
                        if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(playerTurn.getNickname()))
                            workers.add(new int[]{i, j});
                    }
                }
            }
            int x = workers.get(msgPacket.x)[0], y = workers.get(msgPacket.x)[1];
            if (CheckSelectedCell(playerTurn, x, y)) {
                ArrayList<int[]> beforeMovePossibilities = turn.CheckAround(board, x, y, playerTurn.getGodPower(), 0);
                ArrayList<int[]> movePossibilities = turn.CheckAround(board, x, y, playerTurn.getGodPower(), 1);
                if (beforeMovePossibilities.size() == 0) {
                    if (movePossibilities.size() == 0) {
                        lastAction = -1;
                        msgError = "Error no moves available for this worker, select the other one\n" + Messages.selectWorker;
                    } else {
                        lastAction = 2;
                        alt = PrintPossibilities(movePossibilities);
                        turn.setSelectedCell(board.getCell(x, y));
                        msgError = Messages.move;
                    }
                } else {
                    lastAction = 1;
                    alt = PrintPossibilities(beforeMovePossibilities);
                    turn.setSelectedCell(board.getCell(x, y));
                    msgError = Messages.beforeMove;
                }
            } else {
                lastAction = -2;
                msgError = "Error Can't select that cell, try another one\n" + Messages.selectWorker;
            }
        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board.getCell(i, j).getWorker() != null) {
                        if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(playerTurn.getNickname()))
                            workers.add(new int[]{i, j});
                    }
                }
            }
            lastAction = -1;
            alt = PrintPossibilities(workers);
            msgError = "Error the inserted value is not valid\n" + Messages.selectWorker;
        }
        CreateMsgPacket(msgError, alt);
    }

    /**
     * Method BeforeMove executes the action selected by the player
     *
     * @param msgPacket of type MsgToServer
     */
 /*
    -1 lost
    0 did nothing
    1 ok
    */
    public void BeforeMove(MsgToServer msgPacket) {
        lastAction = 1;
        String alt = "Wait";
        int sel = msgPacket.x;
        int pow = msgPacket.y;
        ArrayList<int[]> beforeMovePossibilities = turn.CheckAround(board, turn.getSelectedCell().getPos()[0], turn.getSelectedCell().getPos()[1], playerTurn.getGodPower(), 0);

        if (pow == 1)
            lastAction = turn.BeforeMove(board, beforeMovePossibilities.get(sel)[0], beforeMovePossibilities.get(sel)[1]);
        else
            lastAction = 1;

        if (lastAction == 1)//the game must go on
        {
            ArrayList<int[]> movePossibilities = turn.CheckAround(board, turn.getSelectedCell().getPos()[0], turn.getSelectedCell().getPos()[1], playerTurn.getGodPower(), 1);
            msgError = Messages.move;
            alt = PrintPossibilities(movePossibilities);
        } else if (lastAction < 0) {
            msgError = errorHandler.GetErrorSetup(lastAction) + "\n" + Messages.beforeMove;
            alt = PrintPossibilities(beforeMovePossibilities);
        }
        CreateMsgPacket(msgError, alt);
    }

    /**
     * Method Move executes the move selected by the player
     *
     * @param msgPacket of type MsgToServer
     */
    public void Move(MsgToServer msgPacket) {
        String alt;
        int sel = msgPacket.x;
        ArrayList<int[]> movePossibilities = turn.CheckAround(board, turn.getSelectedCell().getPos()[0], turn.getSelectedCell().getPos()[1], playerTurn.getGodPower(), 1);
        if (msgPacket.y == 0)
            lastAction = 1;
        else if (sel < 0 || sel >= movePossibilities.size())
            lastAction = -1;
        else
            CheckMove(movePossibilities.get(sel)[0], movePossibilities.get(sel)[1], msgPacket.y);

        winner = CheckWinCondition(playerTurn);
        if (winner != null) {
            lastAction = 10;
            PlayerWin(playerTurn.getNickname());
        } else {
            if (lastAction < 0) {
                msgError = errorHandler.GetErrorMove(lastAction);
                alt = PrintPossibilities(movePossibilities);
                if (msgPacket.y == 0)
                    msgError += "\n" + Messages.move;
                else
                    msgError += "\n" + Messages.moveAgain;
            } else {
                ArrayList<int[]> buildPossibilities = turn.CheckAround(board, turn.getSelectedCell().getPos()[0], turn.getSelectedCell().getPos()[1], playerTurn.getGodPower(), 2);
                alt = PrintPossibilities(buildPossibilities);
                if (lastAction == 1) {
                    msgError = Messages.build;
                }
                if (lastAction == 2)
                    msgError = Messages.moveAgain;
            }
            //notify view
            CreateMsgPacket(msgError, alt);
        }
    }

    /**
     * Method CheckMove support move function
     *
     * @param targetX  of type int
     * @param targetY  of type int
     * @param godPower of type int
     */
    private void CheckMove(int targetX, int targetY, int godPower) {
        if (lastAction == 2 && godPower == 0)//don't use the godPower
        {
            lastAction = 1;
            return;
        }
        lastAction = turn.Move(board, targetX, targetY);
        msgError = errorHandler.GetErrorMove(lastAction);
    }

    /**
     * Method Build executes the action selected by the player
     *
     * @param msgPacket of type MsgToServer
     */
    public void Build(MsgToServer msgPacket) {
        String alt;
        int sel = msgPacket.x;
        ArrayList<int[]> buildPossibilities = turn.CheckAround(board, turn.getSelectedCell().getPos()[0], turn.getSelectedCell().getPos()[1], playerTurn.getGodPower(), 2);
        int godPower = msgPacket.y, typeBuilding = msgPacket.targetX;

        if (turn.CheckLostBuild(board)) {
            lastAction = -10;
            msgError = errorHandler.GetErrorBuild(lastAction);
            PlayerLost("Error You Lost (can't build)", playerTurn.getNickname() + "" +
                    " lost because he can't build with his workers");
        } else {
            if (godPower == 0)
                lastAction = 1;
            else if (sel < 0 || sel >= buildPossibilities.size())
                lastAction = -1;
            else
                CheckBuild(buildPossibilities.get(sel)[0], buildPossibilities.get(sel)[1], typeBuilding, godPower);
            winner = CheckWinCondition(playerTurn);
            if (winner != null) {
                lastAction = 10;
                PlayerWin(playerTurn.getNickname());
            } else {
                if (lastAction < 0) {
                    if (godPower != 1)
                        msgError += "\n" + Messages.build;
                    else
                        msgError += "\n" + Messages.buildAgain;
                    alt = PrintPossibilities(buildPossibilities);
                } else {
                    if (lastAction == 1) {
                        NextPlayer();
                        msgError = Messages.startTurn;
                    }
                    if (lastAction == 2)
                        msgError = Messages.buildAgain;
                    alt = "";
                }
                //notify view
                CreateMsgPacket(msgError, alt);
            }
        }
    }

    /**
     * Method CheckBuild support build function
     *
     * @param targetX      of type int
     * @param targetY      of type int
     * @param typeBuilding of type int
     * @param godPower     of type int
     */
    private void CheckBuild(int targetX, int targetY, int typeBuilding, int godPower) {
        if (lastAction == 2 && godPower != 1) {
            lastAction = 1;
            msgError = errorHandler.GetErrorBuild(lastAction);
            return;
        }
        lastAction = turn.Build(board, targetX, targetY, typeBuilding);
        msgError = errorHandler.GetErrorBuild(lastAction);
    }

    /**
     * Method CheckSelectedCell checks if the cell has one of the player's workers
     *
     * @param player of type Player
     * @param x      of type int
     * @param y      of type int
     * @return boolean
     */
    public boolean CheckSelectedCell(Player player, int x, int y) {
        if (board.getCell(x, y).getWorker() != null)
            return board.getCell(x, y).getWorker().getPlayer().getNickname().equals(player.getNickname());
        else return false;
    }

    /**
     * Method NextTurn increments the value of turn
     */
    public void NextTurn() {
        turn.setTurn(turn.getTurn() + 1);
    }

    /**
     * Method NextPlayer passes the turn to the next player
     */
    public void NextPlayer() {
        if (board != null) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board.getCell(i, j).getWorker() != null) {
                        if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(playerTurn.getNickname())) {
                            board.getCell(i, j).getWorker().setDebuff(false);
                            board.getCell(i, j).getWorker().getPlayer().getGodPower().ResetGod();
                        }
                    }
                }
            }
        }
        nPlayer++;
        if (nPlayer >= setup.getPlayers().size()) {
            nPlayer = 0;
            NextTurn();
        }
        playerTurn = setup.getPlayers().get(nPlayer);
        //TODO aggiungere salvataggio per partita
    }

    /**
     * Method CheckWinCondition checks the win condition for the player
     *
     * @param player of type Player
     * @return Player
     */
    public Player CheckWinCondition(Player player) {
        return turn.CheckWinCondition(board, player);
    }


    /**
     * Method getPlayers returns the players of this Match object.
     *
     * @return the players (type ArrayList<Player>) of this Match object.
     */
    public ArrayList<Player> getPlayers() {
        return setup.getPlayers();
    }

    /**
     * Method getBoard returns the board of this Match object.
     *
     * @return the board (type Board) of this Match object.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Method setBoard sets the board of this Match object.
     *
     * @param board the board of this Match object.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Method getSetup returns the setup of this Match object.
     *
     * @return the setup (type SetupMatch) of this Match object.
     */
    //getter for view cause it needs to access god list and playerList
    public SetupMatch getSetup() {
        return setup;
    }

    /**
     * Method CreateMsgPacket creates msgPacket only whit the message and the alternative message
     *
     * @param player of type String
     * @param other  of type String
     */
    //create and notify only with messages for players
    public void CreateMsgPacket(String player, String other) {
        SendPacket(playerTurn.getNickname(), player, other, board);
    }

    /**
     * Method SendPacket creates the packet and notify the observers
     *
     * @param nickname of type String
     * @param msg      of type String
     * @param alt      of type String
     * @param board    of type Board
     */
    //notify with all thing
    public void SendPacket(String nickname, String msg, String alt, Board board) {
        ArrayList<String> players = new ArrayList<>();
        ArrayList<SimpleGod> gods = new ArrayList<>();
        ArrayList<int[]> workers = new ArrayList<>();

        for (Player pl : setup.getPlayers()) {
            players.add(pl.getNickname());
            SimpleGod tempGod = new SimpleGod();
            if (pl.getGodPower() != null) {
                tempGod.setSimpleGod(pl.getGodPower().getName(), pl.getGodPower().description, pl.getGodPower().img);
                gods.add(tempGod);
            }
            ArrayList<int[]> temp = FindWorkers(pl.getNickname());
            if (temp.size() == 2) {
                workers.add(temp.get(0));
                workers.add(temp.get(1));
            }
        }
        setChanged();
        notifyObservers(new MsgPacket(nickname, msg, alt, new SimpleBoard(board, gods, players, workers)));
    }

    /**
     * Method FindWorkers finds the player's workers
     *
     * @param player of type String
     * @return ArrayList<int [ ]>
     */
    public ArrayList<int[]> FindWorkers(String player) {
        ArrayList<int[]> workers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.getCell(i, j).getWorker() != null) {
                    if (board.getCell(i, j).getWorker().getPlayer().getNickname().equals(player)) {
                        workers.add(new int[]{i, j});
                    }
                }
            }
        }
        return workers;
    }

    /**
     * Method PlayerWin notify all that the player wins
     *
     * @param player of type String
     */
    public void PlayerWin(String player) {
        SendPacket(player, "EndGame Winner winner chicken dinner!", "EndGame You get" +
                " nothing, you lose!\n" +
                player + "Won", null);
    }

    /**
     * Method PlayerLost notify all that the player losts
     *
     * @param msg of type String
     * @param alt of type String
     */
    public void PlayerLost(String msg, String alt) {
        Player loser = playerTurn;
        CreateMsgPacket(msg, alt);//send packet to the player
        killPlayer(loser);
        if (nPlayer > 0)
            nPlayer--;
        NextPlayer();
        lastAction = -10;
        CreateMsgPacket(Messages.startTurn, alt);
    }

    /**
     * Method killPlayer removes player from the game
     *
     * @param player of type Player
     */
    //remove player from players list and worker from the board
    public void killPlayer(Player player) {
        // ArrayList<Player> players = getSetup().getPlayers();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (getBoard().getCell(i, j).getWorker() != null) {
                    if (getBoard().getCell(i, j).getWorker().getPlayer().getNickname().equals(player.getNickname())) {
                        getBoard().getCell(i, j).setWorker(null);
                    }
                }
            }
        }
        getSetup().getPlayers().remove(player);
    }


    /**
     * Method PrintPossibilities converts an arrayList of coordinates into a string
     *
     * @param arrayList of type ArrayList<int[]>
     * @return String
     */
    public String PrintPossibilities(ArrayList<int[]> arrayList) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++)
            s.append(i).append(") (").append(arrayList.get(i)[0]).append(", ").append(arrayList.get(i)[1]).append(")\n");
        return s.toString();
    }
}