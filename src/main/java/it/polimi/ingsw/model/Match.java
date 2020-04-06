package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Match extends Observable
{
    private Board board;
    private Turn turn;
    private SetupMatch setup;
    private Player winner;
    private int nPlayer = 0;

    public Match(ArrayList<String> nicks)
    {
        this.board = new Board();
        this.turn = new Turn();
        this.setup = new SetupMatch();
        this.setup.setPlayers(nicks);
        this.winner = new Player("");
    }

    public void StartMatch()
    {

    }

    public boolean CheckSelectedCell(Player player, int x, int y)
    {
        if (board.getCell(x,y).getWorker().getPlayer().getNickname().equals(player.getNickname()))
            return true;
        else
            return false;
    }

    public void NextTurn(Player player)
    {

    }

    public Player NextPlayer()
    {
        if (nPlayer == setup.getPlayers().size())
            nPlayer = 0;
        else
            nPlayer++;
        return setup.getPlayers().get(0);
    }

    public void CheckWinCondition(Player player)
    {
        turn.CheckWinCondition(board, player);
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