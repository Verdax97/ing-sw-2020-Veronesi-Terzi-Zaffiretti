package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Observable;

public class Match extends Observable
{
    private Board board;
    private Turn turn;
    private SetupMatch setup;
    //getter for view cause it needs to access god list and playerList
    public SetupMatch getSetup() {
        return setup;
    }
    private Player winner;
    private ErrorHandler errorHandler;
    private int nPlayer = 0;
    private int lastActionSetup = 0;//0 all right !=0 some problem
    private int lastActionMove = 0;//0 all right !=0 some problem
    private int lastActionBuild = 0;//0 all right !=0 some problem
    private String msgError;

    public Match(ArrayList<String> nicks)
    {
        this.board = new Board();
        this.turn = new Turn();
        this.setup = new SetupMatch();
        this.setup.setPlayers(nicks);
        this.winner = new Player("");
        this.errorHandler = new ErrorHandler();
    }

    public void StartMatch()
    {

    }

    public void StartTurn(Player player, int x, int y, int godPower)
    {
        lastActionSetup = turn.StartTurn(setup.getPlayers(), player, board, x, y, godPower == 1);
        msgError = errorHandler.GetErrorSetup(lastActionSetup);
    }
    public void PickGod(int value)
    {
        if (value < 0 || value >= setup.getGodList().size())
        {
            msgError = "Can't pick that god, try another value";
            lastActionMove = -1;
            return;
        }
        setup.AddGodPicked(setup.getGodList().get(value));
        setup.getGodList().remove(value);
        lastActionMove = 1;
    }

    public void SelectPlayerGod(int value, Player player)
    {
        if (value < 0 || value >= setup.getGodPicked().size())
        {
            msgError = "Can't pick that god, try another value";
            lastActionMove = -1;
            return;
        }
        player.setGodPower(setup.PickGod(value));
        lastActionMove = 1;
    }

    public void Move(int x, int y, int targetX, int targetY, int godPower, Player player)
    {
        CheckMove(x, y, targetX, targetY, godPower, player);
        winner = CheckWinCondition(player);
        //notify view
        setChanged();
        notifyObservers(board.clone());
    }

    private void CheckMove(int x, int y, int targetX, int targetY, int godPower, Player player)
    {
        if (lastActionMove == 2 && godPower == 0)
        {
            lastActionMove = 1;
            msgError = errorHandler.GetErrorMove(lastActionMove);
            return;
        }
        if (!CheckSelectedCell(player, x, y))
        {
            lastActionMove = -1;
            msgError = "There is no worker on the selected cell";
            return;
        }
        turn.setSelectedCell(board.getCell(x, y));
        lastActionMove = turn.Move(board, targetX, targetY);
        msgError = errorHandler.GetErrorMove(lastActionMove);
        if (lastActionMove < 0)
            return;
        turn.setSelectedCell(board.getCell(targetX, targetY));
    }
    public void Build(int targetX, int targetY, int typeBuilding, int godPower, Player player)
    {
        if(turn.CheckLostBuild(player, board))
            {
                lastActionBuild = -10;

            } else
        {
            CheckBuild(targetX, targetY, typeBuilding, godPower);
            winner = CheckWinCondition(player);
        }
        //notify view
        setChanged();
        notifyObservers(board.clone());
    }

    private void CheckBuild(int targetX, int targetY, int typeBuilding, int godPower)
    {
        if (lastActionBuild == 2 && godPower == 0)
        {
            lastActionBuild = 1;
            msgError = errorHandler.GetErrorBuild(lastActionBuild);
            return;
        }
        lastActionBuild = turn.Build(board, targetX, targetY, typeBuilding);
        msgError = errorHandler.GetErrorBuild(lastActionBuild);
    }

    public boolean CheckSelectedCell(Player player, int x, int y)
    {
        return board.getCell(x, y).getWorker().getPlayer().getNickname().equals(player.getNickname());
    }

    public void NextTurn()
    {
        turn.setTurn(turn.getTurn() + 1);
    }

    public Player NextPlayer()
    {
        if (nPlayer == setup.getPlayers().size())
        {
            nPlayer = 0;
            turn.setTurn(turn.getTurn() + 1);
        }
        else
            nPlayer++;
        return setup.getPlayers().get(nPlayer);
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
}