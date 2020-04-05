package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.SetupMatch;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ServerViewTest
{
    @Test
    public void PrintBoardTest()
    {
        ArrayList<String> players = new ArrayList<String>();
        players.add("pino");
        players.add("pippo");
        Match match = new Match(players);
        SetupMatch setupMatch = new SetupMatch();
        Board board = new Board();
        ServerView serverView = new ServerView();
        serverView.PrintBoard(board, match);
    }
}
