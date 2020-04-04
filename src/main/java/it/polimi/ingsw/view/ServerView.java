package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.SetupMatch;
import it.polimi.ingsw.model.State;

import java.util.Observable;
import java.util.Observer;

public class ServerView extends Observable implements Runnable, Observer
{
    private Board board;
    private State state;
    private SetupMatch setupMatch;

    public void PrintBoard()
    {

    }

    public void ReciveMsg()
    {

    }

    @Override
    public void run() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
