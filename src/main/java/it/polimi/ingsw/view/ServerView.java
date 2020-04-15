package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
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
    private State state;

    public ServerView ()
    {
        this.scanner = new Scanner(System.in);
        this.outputStream = new PrintStream(System.out);
    }

    public void PrintBoard(Board board, Match match)
    {
        for(int j = 4; j >= 0; j--){
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
        outputStream.println();
    }

    public void ReceiveMsg()
    {

    }


    public void startServer() throws IOException
    {
        int port = 4567;
        //open TCP port
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server socket ready on port: " + port);
        //wait for connection
        Socket socket = serverSocket.accept();
        System.out.println("Received client connection");
        // open input and output streams to read and write
        Scanner in = new Scanner(socket.getInputStream());
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        //read from and write to the connection until I receive "quit"
        while(true){
            String line = in.nextLine();
            if(line.equals("quit")){
                break;
            } else {
                out.println("Received: " + line);
                out.flush();
            }
        }
        //close streams and socket
        System.out.println("Closing sockets");
        in.close();
        out.close();
        socket.close();
        serverSocket.close();
    }

    public void main(String[] args)
    {
        try
        {
            startServer();
        } catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
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
