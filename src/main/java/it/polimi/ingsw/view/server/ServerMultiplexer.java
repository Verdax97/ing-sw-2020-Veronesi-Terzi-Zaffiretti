package it.polimi.ingsw.view.server;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.MsgToServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class ServerMultiplexer extends Observable implements Runnable {
    private final Controller controller;
    private ServerSocket serverSocket;
    private ExecutorService executor;
    public ArrayList<ServerThread> playersThread;

    public synchronized Lobby getLobby() {
        return lobby;
    }

    private final Lobby lobby;
    private int nConnectionPlayer = 0;

    public ServerMultiplexer(Controller controller) {
        this.controller = controller;
        this.playersThread = new ArrayList<>();
        this.lobby = new Lobby();
    }

    public void startServer() throws IOException {
        //It creates threads when necessary, otherwise it re-uses existing one when possible
        executor = Executors.newCachedThreadPool();
        System.out.println("Insert server port:");
        AtomicReference<Scanner> scanner = new AtomicReference<>(new Scanner(System.in));
        int port;
        while (true) {
            try {
                port = scanner.get().nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Insert a valid port");
                scanner.set(new Scanner(System.in));
            }
        }

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); //port not available
            System.out.println("asdasd");
            return;
        }
        System.out.println("Server ready on port " + port);
        Runnable runnable = () ->
        {
            scanner.set(new Scanner(System.in));
            while (!scanner.get().nextLine().equalsIgnoreCase("quit")) {
            }
            System.exit(1);
        };
        Thread threadInput = new Thread(runnable);
        threadInput.start();
        boolean entered = false;
        while (true) {
            try {
                if (nConnectionPlayer == 0 && !entered) {
                    System.out.println("Waiting for first player");
                    Socket socket = serverSocket.accept();
                    playersThread.add(new ServerThread(socket, "temp", this, playersThread.size()));
                    entered = true;
                    //Run Thread
                    executor.submit(playersThread.get(playersThread.size() - 1));
                    System.out.println("First player connected");
                } else if (playersThread.size() < lobby.getnPlayer()) {
                    System.out.println("Wait for others player");
                    Socket socket = serverSocket.accept();
                    playersThread.add(new ServerThread(socket, "temp", this, playersThread.size()));
                    //Run Thread
                    executor.submit(playersThread.get(playersThread.size() - 1));
                } else if (getNConnectionPlayer() == lobby.getnPlayer() && getNConnectionPlayer() > 1) {
                    //cut the possibility to connect to the server
                    break;
                }
                //if all player are in game do nothing and wait for something to crash
            } catch (IOException e) {
                CloseConnection();
                return;
                //break; //In case the serverSocket gets closed
            }
        }
        //todo check if there are other games with same players

        //start all thread
        for (ServerThread thread : playersThread) {
            thread.waitForStart = true;
        }
        //create the game
        controller.setLobby(lobby);
        controller.CreateMatch();
    }

    public void CloseConnection() {
        for (ServerThread thread :
                playersThread) {
            thread.update(new Match(lobby.getPlayers()), new MsgPacket("", "end", "end connection from server", null));
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            //ok
        }
        System.exit(1);
    }

    public synchronized boolean SetNickname(String name) {
        for (String s : lobby.getPlayers()) {
            if (name.equalsIgnoreCase(s)) {
                System.out.println(name + "-" + s);
                return false;
            }
        }
        return lobby.AddPlayer(name);
    }

    /**
     * Method addConnected increment nConnectionPlayer
     */
    public synchronized void addConnected() {
        nConnectionPlayer++;
    }

    /**
     * Method getNConnectionPlayer returns the nConnectionPlayer of this ServerMultiplexer object.
     *
     * @return the nConnectionPlayer (type int) of this ServerMultiplexer object.
     */
    public synchronized int getNConnectionPlayer() {
        return nConnectionPlayer;
    }

    @Override
    public void run() {
        try {
            startServer();
        } catch (IOException e) {
            System.out.println("Server Ended");
        }
    }

    /**
     * Method ReceiveMsg notify the controller
     *
     * @param msg of type MsgToServer
     */
    public void ReceiveMsg(MsgToServer msg) {
        //System.out.println("Received message from " + msg.nickname + " x=" + msg.x + ", y=" + msg.y + ", targX=" + msg.targetX + ", targY=" + msg.targetY);
        setChanged();
        notifyObservers(msg);
    }

    /**
     * Method ConnectObserver set the ServerThread as observer of the  observable object
     *
     * @param observable of type Match
     */
    public void ConnectObserver(Observable observable) {
        for (ServerThread observer : playersThread) {
            observable.addObserver(observer);
        }
    }
}