package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Lobby;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {

    private int port;
    private ServerSocket serverSocket;
    public int active = 0;
    public ArrayList<ServerThread> playersThread;
    public Lobby lobby;
    public boolean start = false;

    public EchoServer(int port){
        this.port = port;
        this.playersThread = new ArrayList<>();
        this.lobby = new Lobby();
    }

    public void startServer() throws IOException {
        //It creates threads when necessary, otherwise it re-uses existing one when possible
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try{
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.err.println(e.getMessage()); //port not available
            return;
        }
        System.out.println("Server ready");
        while (true){
            try{
                if (lobby.getPlayers().size() >= lobby.getnPlayer())
                {
                    Socket socket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println("Too many players");
                    out.flush();
                }
                else {
                    Socket socket = serverSocket.accept();
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    //Get Player Nickname
                    Scanner in = new Scanner(socket.getInputStream());

                    //Set Player nickname in lobby and in playersThread
                    while (!SetNickname(in.nextLine(), socket))
                    {
                        out.println("nickname not valid, try another nickname");
                        out.flush();
                    }
                    out.println("valid nickname");
                    out.flush();

                    //Run Thread
                    executor.submit(playersThread.get(playersThread.size()-1));

                    for (String player:lobby.getPlayers())
                    {
                        System.out.println(player);
                    }
                }
            }catch(IOException e){
                break; //In case the serverSocket gets closed
            }
        }
        executor.shutdown();
        serverSocket.close();
    }

    public boolean SetNickname(String name, Socket socket)
    {
        for (String s:lobby.getPlayers())
        {
            if (name.equalsIgnoreCase(s))
                return false;
        }
        lobby.AddPlayer(name);
        playersThread.add(new ServerThread(socket, name, this));
        return true;
    }

    public void setActive(String player)
    {
        for (int i=0; i < playersThread.size(); i++)
        {
            if (player.equalsIgnoreCase(playersThread.get(i).getNick()))
            {
                active = i;
            }
        }
    }
}
