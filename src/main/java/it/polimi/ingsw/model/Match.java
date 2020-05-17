package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Observable;

public class Match extends Observable {
    private Board board;
    private final Turn turn;
    private final SetupMatch setup;

    public Player getPlayerTurn() {
        return playerTurn;
    }

    private Player playerTurn;
    private Player winner;
    private final ErrorHandler errorHandler;
    private int nPlayer = 0;
    private int lastAction = 0;//0 all right < 0 some problem

    public String getMsgError() {
        return msgError;
    }

    private String msgError;

    public Match(ArrayList<String> nicks) {
        this.board = new Board();
        this.turn = new Turn();
        this.setup = new SetupMatch();
        this.setup.setPlayers(nicks);
        this.winner = new Player("");
        this.errorHandler = new ErrorHandler();
    }

    public void StartGame() {
        nPlayer = setup.getPlayers().size() - 1;
        playerTurn = setup.getPlayers().get(nPlayer);
        CreateMsgPacket("choseGods", PrintGods(setup.getGodList()));
    }

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

    public int getLastAction() {
        return lastAction;
    }

    public void PickGod(MsgToServer msgPacket)
    {
        int value = msgPacket.x;
        if (value < 0 || value >= setup.getGodList().size()) {
            msgError = "Error Can't pick that god, try another value\n";
            lastAction = -1;
            CreateMsgPacket(msgError + "choseGods", PrintGods(setup.getGodList()));
            return;
        }
        setup.AddGodPicked(setup.getGodList().get(value));
        //setup.getGodList().remove(value);
        lastAction = 1;
        if (getSetup().getGodPicked().size() == setup.getPlayers().size())
        {
            NextPlayer();

            CreateMsgPacket("choseYourGod", PrintGods(setup.getGodPicked()));
            return;
        }
        CreateMsgPacket("choseGods" , PrintGods(setup.getGodList()));
    }

    public void SelectPlayerGod(MsgToServer msgPacket)
    {
        int value = msgPacket.x;
        if (value < 0 || value >= setup.getGodPicked().size()) {
            msgError = "Error Can't pick that god, try another value\n";
            lastAction = -1;
            CreateMsgPacket(msgError + "choseYourGod", PrintGods(setup.getGodPicked()));
            return;
        }
        playerTurn.setGodPower(setup.PickGod(value));
        lastAction = 1;
        NextPlayer();
        if (getSetup().getGodPicked().size() == 0)
            CreateMsgPacket("Place", "Wait");
        else
            CreateMsgPacket("choseYourGod", PrintGods(setup.getGodPicked()));
    }

    public void PlaceWorker(MsgToServer msgPacket)
    {
        int x = msgPacket.x, y = msgPacket.y;
        int x2 = msgPacket.targetX, y2 = msgPacket.targetY;
        if ((x < 0 || x > 4 || y < 0 || y > 4) || board.getCell(x,y).getWorker() != null
                || (x2 < 0 || x2 > 4 || y2 < 0 || y2 > 4) || board.getCell(x2, y2).getWorker() != null
                || (x == x2 && y == y2))
        {
            msgError = "Error Can't place a worker here, try another value\nPlace";
            lastAction = -1;
        }
        else
        {
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
            if (found == getSetup().getPlayers().size() * 2)
            {
                lastAction = 2;
                msgError = "StartTurn";
            }
            else
            {
                msgError = "Place";
                lastAction = 1;
            }
        }
        CreateMsgPacket(msgError, "Wait");
    }

    public void StartTurn()
    {
        String alt = "Wait";
        lastAction = turn.StartTurn(setup.getPlayers(), playerTurn, board);
        if (lastAction == 0)//the game must go on
            msgError = "BeforeMove";
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
            msgError = errorHandler.GetErrorSetup(lastAction) + "\nStartTurn";
        CreateMsgPacket(msgError, alt);
    }

    /*
    -1 lost
    0 did nothing
    1 ok
    */
    public void BeforeMove(MsgToServer msgPacket)
    {
        String alt = "Wait";
        int x = msgPacket.x, y = msgPacket.y;
        if(CheckSelectedCell(playerTurn, x, y))
        {
            turn.setSelectedCell(board.getCell(x, y));
            lastAction = turn.BeforeMove(board, msgPacket.targetX, msgPacket.targetY);
            if (lastAction == 1)//the game must go on
                msgError = "Move";
            if (lastAction == -1)//you lose
            {
                PlayerLost("Error You Lost (can't move worker)", playerTurn.getNickname() + "" +
                        " lost because he can't move his workers");
                return;
            }
            if (lastAction < -1)
                msgError = errorHandler.GetErrorSetup(lastAction) + "\nBeforeMove";
        }
        else
        {
            lastAction = -2;
            msgError = "Error Can't select that cell, try another one\nBeforeMove";
        }
        CreateMsgPacket(msgError, alt);
    }

    public void Move(MsgToServer msgPacket)
    {
        CheckMove(msgPacket.targetX, msgPacket.targetY, msgPacket.x);
        winner = CheckWinCondition(playerTurn);
        if (winner != null)
        {
            lastAction = 10;
            PlayerWin(playerTurn.getNickname());
        }
        else {
            if (lastAction < 0)
            {
                if (msgPacket.x == 0)
                    msgError += "\nMove";
                else
                    msgError += "\nMove Again";
            }
            else
            {
                if (lastAction == 1)
                {
                    msgError = "Build";
                }
                if (lastAction == 2)
                    msgError = "Move Again";
            }
            //notify view
            CreateMsgPacket(msgError, "Wait");
        }
    }

    private void CheckMove(int targetX, int targetY, int godPower)
    {
        if (lastAction == 2 && godPower != 1)//don't use the godPower
        {
            lastAction = 1;
            return;
        }
        lastAction = turn.Move(board, targetX, targetY);
        msgError = errorHandler.GetErrorMove(lastAction);
    }

    public void Build(MsgToServer msgPacket)
    {
        int targetX = msgPacket.targetX, targetY = msgPacket.targetY;
        int godPower = msgPacket.x, typeBuilding = msgPacket.y;
        if(turn.CheckLostBuild(board))
        {
            lastAction = -10;
            msgError = errorHandler.GetErrorBuild(lastAction);
            PlayerLost("Error You Lost (can't build)", playerTurn.getNickname() + "" +
                    " lost because he can't build with his workers");
        }
        else
        {

            CheckBuild(targetX, targetY, typeBuilding, godPower);
            winner = CheckWinCondition(playerTurn);
            if (winner != null)
            {
                lastAction = 10;
                PlayerWin(playerTurn.getNickname());
            }
            else {
                if (lastAction < 0)
                {
                    if (godPower != 1)
                        msgError += "\nBuild";
                    else
                        msgError += "\nBuild Again";
                }
                else {
                    if (lastAction == 1) {
                        NextPlayer();
                        msgError = "StartTurn";
                    }
                    if (lastAction == 2)
                        msgError = "Build Again";
                }
                //notify view
                CreateMsgPacket(msgError, "Wait");
            }
        }
    }

    private void CheckBuild(int targetX, int targetY, int typeBuilding, int godPower)
    {
        if (lastAction == 2 && godPower != 1) {
            lastAction = 1;
            msgError = errorHandler.GetErrorBuild(lastAction);
            return;
        }
        lastAction = turn.Build(board, targetX, targetY, typeBuilding);
        msgError = errorHandler.GetErrorBuild(lastAction);
    }

    public boolean CheckSelectedCell(Player player, int x, int y)
    {
        if (board.getCell(x, y).getWorker() != null)
            return board.getCell(x, y).getWorker().getPlayer().getNickname().equals(player.getNickname());
        else return false;
    }

    public void NextTurn()
    {
        turn.setTurn(turn.getTurn() + 1);
    }

    public void NextPlayer()
    {
        if (board != null)
        {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board.getCell(i, j).getWorker() != null){
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

    public Player CheckWinCondition(Player player)
    {
        return turn.CheckWinCondition(board, player);
    }


    public ArrayList<Player> getPlayers()
    {
        return setup.getPlayers();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    //getter for view cause it needs to access god list and playerList
    public SetupMatch getSetup() {
        return setup;
    }

    //create and notify only with messages for players
    public void CreateMsgPacket(String player, String other)
    {
        setChanged();
        notifyObservers(new MsgPacket(playerTurn.getNickname(),
                player, other, board.Clone(), setup.getPlayers()));
    }

    //notify with all thing
    public void SendPacket(String nickname, String msg, String alt, Board board)
    {
        setChanged();
        Board temp;
        temp = null;
        if (board != null)
            temp = board.Clone();
        notifyObservers(new MsgPacket(nickname, msg, alt, temp, setup.getPlayers()));
    }

    public void PlayerWin(String player)
    {
        SendPacket(player, "EndGame Winner winner chicken dinner!", "EndGame You get" +
                " nothing, you lose!\n" +
                player + "Won", null);
    }

    public void PlayerLost(String msg, String alt)
    {
        Player loser = playerTurn;
        CreateMsgPacket(msg, alt);//send packet to the player
        killPlayer(loser);
        if (nPlayer > 0)
            nPlayer--;
        NextPlayer();
        lastAction = -10;
        CreateMsgPacket("StartTurn", alt);
    }

    //remove player from players list and worker from the board
    public void killPlayer(Player player)
    {
       // ArrayList<Player> players = getSetup().getPlayers();
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (getBoard().getCell(i,j).getWorker() != null)
                {
                    if (getBoard().getCell(i,j).getWorker().getPlayer().getNickname().equals(player.getNickname()))
                    {
                        getBoard().getCell(i,j).setWorker(null);
                    }
                }
            }
        }
        getSetup().getPlayers().remove(player);
    }
}