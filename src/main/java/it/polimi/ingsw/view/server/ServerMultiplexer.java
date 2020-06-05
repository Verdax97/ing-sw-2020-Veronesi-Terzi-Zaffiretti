package it.polimi.ingsw.view.server;

import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.GameSaver;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.MsgToServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class ServerMultiplexer creates the socket and performs the connection to the players
 */
public class ServerMultiplexer extends Observable implements Runnable {
    private final Controller controller;
    private ServerSocket serverSocket;
    public ArrayList<ServerThread> playersThread;
    public ServerMain serverMain;

    /**
     * Method getLobby returns the lobby of this ServerMultiplexer object.
     *
     *
     *
     * @return the lobby (type Lobby) of this ServerMultiplexer object.
     */
    public synchronized Lobby getLobby() {
        return lobby;
    }

    private Lobby lobby;
    private int nConnectionPlayer = 0;

    private volatile boolean wantToResumeGame = true;
    private volatile boolean resumeGame = false;
    private volatile boolean ending = false;
    private volatile boolean started = false;
    Integer port = null;

    /**
     * Method isEnding returns the ending of this ServerMultiplexer object.
     *
     *
     *
     * @return the ending (type boolean) of this ServerMultiplexer object.
     */
    public synchronized boolean isEnding() {
        return ending;
    }

    /**
     * Method setEnding sets the ending of this ServerMultiplexer object.
     *
     *
     *
     * @param ending the ending of this ServerMultiplexer object.
     *
     */
    public synchronized void setEnding(boolean ending) {
        this.ending = ending;
    }

    /**
     * Constructor ServerMultiplexer creates a new ServerMultiplexer instance.
     *
     * @param controller of type Controller
     */
    public ServerMultiplexer(Controller controller) {
        this.controller = controller;
    }

    /**
     * Method startServer ask the port on which the server will start
     */
    public void startServer() {
        //reset all values
        this.lobby = new Lobby();
        this.playersThread = new ArrayList<>();
        resumeGame = false;
        nConnectionPlayer = 0;
        setEnding(false);
        started = false;

        AtomicReference<Scanner> scanner = new AtomicReference<>(new Scanner(System.in));
        if (port == null) {
            System.out.println("Insert server port:");
            while (true) {
                try {
                    port = scanner.get().nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Insert a valid port");
                    scanner.set(new Scanner(System.in));
                }
            }
        }

        while (true) {
            try {
                serverSocket = new ServerSocket(port);
                break;
            } catch (IOException e) {
                System.err.println(e.getMessage()); //port not available
                System.out.println("port not available");
                try {
                    serverSocket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        System.out.println("Server ready on port " + port);
        started = false;
        waitForPlayers();
    }

    /**
     * Method waitForPlayers opens the game on port
     */
    private void waitForPlayers() {
        setEnding(false);

        while (!ending) {
            try {
                if (nConnectionPlayer == 0 && playersThread.size() == 0) {
                    connectNewPlayer();
                } else if (playersThread.size() < lobby.getnPlayer()) {
                    connectNewPlayer();
                } else if (getNConnectionPlayer() == lobby.getnPlayer() && getNConnectionPlayer() > 1) {
                    //cut the possibility to connect to the server
                    break;
                } else Thread.yield();
            } catch (IOException e) {
                System.out.println(e.toString());
                ending = true;
                break;
                //break; //In case the serverSocket gets closed
            }
        }
        if (ending) {
            closeConnections();
            return;
        }

        //check if there are other games with same players
        wantToResumeGame = true;
        try {
            if (GameSaver.checkForGames(lobby))
                while (wantToResumeGame) {
                    playersThread.get(0).askForResume();
                    synchronized (this) {
                        wait();
                    }
                }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.toString());
            closeConnections();
            return;
        }

        controller.setLobby(lobby);
        controller.CreateMatch(resumeGame);
        started = true;
    }

    /**
     * Method connectNewPlayer wait for a new player to connect
     *
     * @throws IOException when
     */
    private void connectNewPlayer() throws IOException {
        System.out.println("Wait for player " + playersThread.size());
        Socket socket = serverSocket.accept();
        playersThread.add(new ServerThread(socket, "temp", this, playersThread.size()));
        //Run Thread
        playersThread.get(playersThread.size() - 1).start();
    }

    /**
     * Method CloseConnection closes all connections and close itself
     */
    public void closeConnections() {
        if (isEnding())
            return;
        setEnding(true);
        try {
            serverSocket.close();
        } catch (IOException | NullPointerException e) {
            System.out.println("cannot close server");
        }
        for (ServerThread thread : playersThread) {
            thread.closeConnection();
            thread.interrupt();
        }
        startServer();
    }

    /**
     * Method SetNickname try to set the nickname in the lobby
     *
     * @param name of type String
     * @return boolean
     */
    public synchronized boolean setNicknameInLobby(String name) {
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

    /**
     * Method run called by the server starter
     */
    @Override
    public void run() {
        startServer();
    }

    /**
     * Method ReceiveMsg notify the controller
     *
     * @param msg of type MsgToServer
     */
    public void receiveMsg(MsgToServer msg) {
        if (wantToResumeGame) {
            resumeGame = (msg.x == 1);
            wantToResumeGame = false;
            synchronized (this) {
                notifyAll();
            }
        }
        if (!started)
            return;
        setChanged();
        notifyObservers(msg);
    }

    /**
     * Method ConnectObserver set the ServerThread as observer of the  observable object
     *
     * @param observable of type Match
     */
    public void connectObservers(Observable observable) {
        for (ServerThread observer : playersThread) {
            observable.addObserver(observer);
        }
    }
}