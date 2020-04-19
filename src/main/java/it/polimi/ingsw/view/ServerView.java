package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.State;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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

    private Lobby lobby;
    private State state;
    private String msgIn;
    private String msgOut;

    public Lobby getLobby() { return lobby; }

    public void setLobby(Lobby lobby) { this.lobby = lobby; }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getMsgIn() {
        return msgIn;
    }

    public void setMsgIn(String msgIn) {
        this.msgIn = msgIn;
    }

    public String getMsgOut() {
        return msgOut;
    }

    public void setMsgOut(String msgOut) {
        this.msgOut = msgOut;
    }


    public ServerView ()
    {
        this.scanner = new Scanner(System.in);
        this.outputStream = new PrintStream(System.out);
    }

    public void PrintBoard(Board board, Match match)
    {
        for(int j = 4; j >= 0; j--){
            outputStream.print( j + "|");
            for(int i = 0; i < 5 ; i++){
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
        outputStream.println("Y|---------");
        outputStream.println("X 0 1 2 3 4\n\n");
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
