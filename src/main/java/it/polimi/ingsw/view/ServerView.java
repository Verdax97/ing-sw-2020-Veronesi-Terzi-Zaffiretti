package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.SetupMatch;
import it.polimi.ingsw.model.State;

import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ServerView extends Observable implements Observer, Runnable
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private Scanner scanner;
    private PrintStream outputStream;
    private boolean done = false;
    //private Board board;
    private State state;
    //private SetupMatch setupMatch;

    public ServerView ()
    {
        scanner = new Scanner(System.in);
        outputStream = new PrintStream(System.out);
    }

    public void PrintBoard(Board board, Match match)
    {
        for(int i = 4; i >= 0; i--){
            for(int j = 0; j < 5 ; j++){
                if (board.getCell(i, j).getDome())
                {
                    outputStream.print("D" + " ");
                }

                else
                    if (board.getCell(i, j).getWorker() != null)
                    {
                        if (match.getPlayers().get(0).getNickname().equals(board.getCell(i, j).getWorker().getPlayer().getNickname()))
                        {
                            System.out.print(ANSI_RED + board.getCell(i, j).getBuilding()+ANSI_RESET + " ");
                        }
                        else if (match.getPlayers().get(1).getNickname().equals(board.getCell(i, j).getWorker().getPlayer().getNickname()))
                        {
                            System.out.print(ANSI_BLUE + board.getCell(i, j).getBuilding() + ANSI_RESET + " ");
                        }
                        else if (match.getPlayers().get(2).getNickname().equals(board.getCell(i, j).getWorker().getPlayer().getNickname()))
                        {
                            System.out.print(ANSI_GREEN + board.getCell(i, j).getBuilding() + ANSI_RESET + " ");
                        }
                    }
                    else
                    {
                        System.out.print(board.getCell(i, j).getBuilding() + " ");
                    }
            }
            outputStream.println();
        }
        outputStream.println();
    }

    public void ReceiveMsg()
    {

    }

    @Override
    public void run()
    {

    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(!(o instanceof Match) || !(arg instanceof Board)){
            throw new IllegalArgumentException();
        }
        PrintBoard((Board)arg, (Match)o);
    }
}
