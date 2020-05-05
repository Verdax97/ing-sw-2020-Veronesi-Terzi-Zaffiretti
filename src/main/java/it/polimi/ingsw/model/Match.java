package it.polimi.ingsw.model;
import it.polimi.ingsw.controller.Controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.regex.Pattern;

public class Match extends Observable
{
    private Board board;
    private Turn turn;
    private SetupMatch setup;

    public Player getPlayerTurn() {
        return playerTurn;
    }

    private Player playerTurn;
    private Player winner;
    private final ErrorHandler errorHandler;
    private int nPlayer = 0;
    private int lastAction = 0;//0 all right < 0 some problem
    private String msgError;

    private MsgPacket msgPacket;

    public Match(ArrayList<String> nicks)
    {
        this.board = new Board();
        this.turn = new Turn();
        this.setup = new SetupMatch();
        this.setup.setPlayers(nicks);
        this.winner = new Player("");
        this.errorHandler = new ErrorHandler();
        nPlayer = setup.getPlayers().size()-1;
        playerTurn = setup.getPlayers().get(nPlayer);
        msgPacket = new MsgPacket(playerTurn.getNickname(), "" +
                "Chose gods for all players by inserting corresponding value (one at the time)" +
                "\n"+PrintGods(setup.getGodList()), "Wait", board.Clone(), setup.getPlayers());
        setChanged();
        notifyObservers(msgPacket);
    }

    private String PrintGods(ArrayList<God> gods)
    {
        StringBuilder printable = new StringBuilder();
        for (int i = 0; i < gods.size(); i++)
        {
            printable.append(i).append(") ");
            printable.append(gods.get(i).name).append(": ");
            printable.append(gods.get(i).description);
            printable.append("\n");
        }
        printable.append("\n");
        return printable.toString();
    }

    public int getLastAction() {
        return lastAction;
    }

    public void PickGod(MsgPacket msgPacket)
    {
        String msgView = msgPacket.msg;
            int value = Integer.parseInt(msgView);
            if (value < 0 || value >= setup.getGodList().size()) {
                msgError = "Error Can't pick that god, try another value\n";
                lastAction = -1;
                CreateMsgPacket(msgError + "Chose gods for all players by inserting corresponding value" +
                        " (one at the time)\n" + PrintGods(setup.getGodList()), "Wait");
                return;
            }
            setup.AddGodPicked(setup.getGodList().get(value));
            setup.getGodList().remove(value);
            lastAction = 1;
            if (getSetup().getGodPicked().size() == setup.getPlayers().size())
            {
                NextPlayer();

                CreateMsgPacket("Chose your god by inserting corresponding value" +
                        ":\n" + PrintGods(setup.getGodPicked()), "Wait");
                return;
            }
            CreateMsgPacket("Chose gods for all players by inserting corresponding value" +
                    " (one at the time)\n" + PrintGods(setup.getGodList()), "Wait");
    }

    public void SelectPlayerGod(MsgPacket msgPacket)
    {
        String msgView = msgPacket.msg;
        if (!Pattern.matches("[1-3]", msgView))
        {
            int value = Integer.parseInt(msgView) - 1;
            if (value < 0 || value >= setup.getGodPicked().size()) {
                msgError = "Error Can't pick that god, try another value\n";
                lastAction = -1;
                CreateMsgPacket(msgError + "Chose your god by inserting corresponding value" +
                        ":\n" + PrintGods(setup.getGodPicked()), "Wait");
                return;
            }
            NextPlayer();
            if (getSetup().getGodPicked().size() == 0)
            {
                CreateMsgPacket("Place your worker:\n", "Wait");
                return;
            }
            playerTurn.setGodPower(setup.PickGod(value));
            lastAction = 1;
            CreateMsgPacket("Chose your god by inserting corresponding value" +
                    ":\n" + PrintGods(setup.getGodPicked()), "Wait");
        }
    }

    public void PlaceWorker(MsgPacket msgPacket)
    {
        String[] values = msgPacket.msg.split(" ");
        int x = Integer.parseInt(values[0]), y =Integer.parseInt(values[1]);
        int x2 = Integer.parseInt(values[2]), y2 =Integer.parseInt(values[3]);
        if ((x < 0 || x > 4 || y < 0 || y > 4) || board.getCell(x,y).getWorker() != null
                || (x2 < 0 || x2 > 4 || y2 < 0 || y2 > 4) || board.getCell(x2, y2).getWorker() != null
                || !(x != x2 && y != y2))
        {
            msgError = "Error Can't place a worker here, try another value\nPlace your worker:";
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
                    {
                        found++;
                    }
                }
            }
            NextPlayer();
            if (found == getSetup().getPlayers().size() * 2)
            {
                String name = playerTurn.getGodPower().getName();
                lastAction = 2;
                msgError = "StartTurn";
            }
            else
            {
                msgError = "Place your worker:";
                lastAction = 1;
            }
        }
        CreateMsgPacket(msgError, "Wait");
    }

    public void StartTurn(MsgPacket msgPacket)
    {
        String[] values = msgPacket.msg.split(" ");
        String alt = "Wait";
        int x = Integer.parseInt(values[0]), y = Integer.parseInt(values[1]);
        int x2 = Integer.parseInt(values[2]), y2 = Integer.parseInt(values[3]);
        int b = Integer.parseInt(values[4]);
        if(CheckSelectedCell(playerTurn, x, y))
        {
            turn.setSelectedCell(board.getCell(x,y));
            lastAction = turn.StartTurn(setup.getPlayers(), playerTurn, board, x2, y2, b == 1);
            if (lastAction == 0)//the game must go on
            {
                msgError = "Move";
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
                msgError = errorHandler.GetErrorSetup(lastAction) + "\nStartTurn";
        }
        else
        {
            lastAction = -2;
            msgError = "Error Can't select that cell, try another one\nStartTurn";
        }
        CreateMsgPacket(msgError, alt);
    }

    public void Move(MsgPacket msgPacket)
    {
        String[] values = msgPacket.msg.split(" ");
        int targetX = Integer.parseInt(values[0]), targetY = Integer.parseInt(values[1]);
        int godPower = Integer.parseInt(values[2]);
        CheckMove(targetX, targetY, godPower);
        winner = CheckWinCondition(playerTurn);
        if (winner != null)
        {
            lastAction = 10;
            PlayerWin(playerTurn.getNickname());
        }
        else {
            if (lastAction < 0)
            {
                if (godPower == 0)
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
        if (lastAction == 2 && godPower == 0)//don't use the godPower
        {
            lastAction = 1;
            return;
        }
        lastAction = turn.Move(board, targetX, targetY);
        msgError = errorHandler.GetErrorMove(lastAction);
    }
    public void Build(MsgPacket msgPacket)
    {
        String[] values = msgPacket.msg.split(" ");
        int targetX = Integer.parseInt(values[0]), targetY = Integer.parseInt(values[1]);
        int godPower = Integer.parseInt(values[2]), typeBuilding = Integer.parseInt(values[3]);
        if(turn.CheckLostBuild(playerTurn, board))
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
                    if (godPower == 0)
                        msgError += "\nBuild";
                    else
                        msgError += "\nBuild Again";
                }
                else
                {
                    if (lastAction == 1)
                    {
                        msgError = "Build";
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
        if (lastAction == 2 && godPower == 0)
        {
            lastAction = 1;
            msgError = errorHandler.GetErrorBuild(lastAction);
            return;
        }
        lastAction = turn.Build(board, targetX, targetY, typeBuilding);
        msgError = errorHandler.GetErrorBuild(lastAction);
    }

    public boolean CheckSelectedCell(Player player, int x, int y)
    {
        return board.getCell(x, y).getWorker().getPlayer().getNickname().equals(player.getNickname());
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
                            //TODO aggiungere salvataggio per undo
                            board.getCell(i, j).getWorker().setDebuff(false);
                            board.getCell(i, j).getWorker().getPlayer().getGodPower().ResetGod();
                        }
                    }
                }
            }
        }
        nPlayer++;
        if (nPlayer == setup.getPlayers().size()) {
            nPlayer = 0;
            NextTurn();
        }
        playerTurn = setup.getPlayers().get(nPlayer);
        //controller.setPlayerTurn(playerTurn);
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
        //CreateMsgPacket("EndGame Winner winner chicken dinner!", "EndGame You get nothing, you lose!\n" +
        //        player + "Won");//send packet to the player
        SendPacket(player, "EndGame Winner winner chicken dinner!", "EndGame You get nothing, you lose!\n" +
                player + "Won", null);
    }

    public void PlayerLost(String msg, String alt)
    {
        Player loser = playerTurn;
        CreateMsgPacket(msg, alt);//send packet to the player
        killPlayer(loser);
        NextPlayer();
        lastAction = -10;
        CreateMsgPacket("StartTurn", alt);
    }
    //remove player from players list and worker from the board
    public void killPlayer(Player player)
    {
        ArrayList<Player> players = getSetup().getPlayers();
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
        for (int i = 0; i < players.size(); i++)
        {
            if (players.get(i).getNickname().equals(player.getNickname()))
            {
                players.remove(i);
            }
        }
        getSetup().SetPlayers(players);
    }
}