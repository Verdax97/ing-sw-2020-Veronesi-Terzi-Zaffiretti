package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MsgPacket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMultiplexer extends Observable implements Runnable, Observer {
    private int port;
    private final Controller controller;
    private ServerSocket serverSocket;
    private ExecutorService executor;
    public int active = 0;
    public ArrayList<ServerThread> playersThread;
    public Lobby lobby;

    private boolean start = false;
    private int nConnectionPlayer = 0;

    private String playerTurn;

    public ServerMultiplexer(Controller controller)
    {
        this.controller = controller;
        this.playersThread = new ArrayList<>();
        this.lobby = new Lobby();
    }

    public void startServer() throws IOException
    {
        //It creates threads when necessary, otherwise it re-uses existing one when possible
        executor = Executors.newCachedThreadPool();
        System.out.println("Insert server port:");
        Scanner scanner = new Scanner(System.in);
        port = scanner.nextInt();
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); //port not available
            return;
        }
        System.out.println("Server ready on port " + port);
        while (true)
        {
            try
            {
                if (playersThread.size() == 0) {
                    Socket socket = serverSocket.accept();
                    playersThread.add(new ServerThread(socket, "temp", this, playersThread.size()));
                    //Run Thread
                    executor.submit(playersThread.get(playersThread.size() - 1));
                }else if (playersThread.size() < lobby.getnPlayer())
                {
                    Socket socket = serverSocket.accept();
                    playersThread.add(new ServerThread(socket, "temp", this, playersThread.size()));
                    //Run Thread
                    executor.submit(playersThread.get(playersThread.size() - 1));
                }
                //if all player are in game do nothing and wait for something to crash
            } catch (IOException e)
            {
                break; //In case the serverSocket gets closed
            } finally {
                controller.setLobby(lobby);
                controller.CreateMatch();
            }

        }
    }

    public void CloseConnection() throws IOException
    {
        executor.shutdown();
        serverSocket.close();
    }

    public synchronized boolean SetNickname(String name)
    {
        for (String s:lobby.getPlayers())
        {
            if (name.equalsIgnoreCase(s))
                return false;
        }
        return lobby.AddPlayer(name);
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

    public void addConnected()
    {
        nConnectionPlayer++;
    }

    public int getnConnectionPlayer()
    {
        return nConnectionPlayer;
    }

    public synchronized boolean isStart() {
        return start;
    }

    public synchronized void setStart(boolean start) {
        this.start = start;
    }

    @Override
    public void run()
    {
        try
        {
            startServer();
        } catch (IOException e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(!(o instanceof Match) || !(arg instanceof Board)){
            throw new IllegalArgumentException();
        }
    }

    public void ReceiveMsg(MsgPacket msg)
    {
        if (!msg.nickname.equals(playerTurn))
        {
            //TODO rispondere "fregancazzo"
        }
        setChanged();
        notifyObservers(msg);
    }
}