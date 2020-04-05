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
    public Worker[] getActiveWorker(Player player)
    {
        Worker[] result = new Worker[2];
        int k = 0;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (board.getCell(i, j).getWorker().getPlayer().getNickname().compareTo(player.getNickname()) == 0)
                {
                    result[k] = board.getCell(i, j).getWorker();
                    k++;
                }
                if (k == 2)
                    return result;
            }
        }
        return result;
    }

    public void NextTurn(Player player) {

    }

    public ArrayList<Player> getPlayers()
    {
        return setup.getPlayers();
    }
}