package it.polimi.ingsw.view.server;

import it.polimi.ingsw.ServerMain;
import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Colors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Class ServerMultiplexer creates the socket and performs the connection to the players
 */
public class ServerMultiplexer extends Observable implements Runnable {
    private final Controller controller;
    private ServerSocket serverSocket;
    /**
     * The Players thread.
     */
    public ArrayList<ServerThread> playersThread;
    /**
     * The Server main.
     */
    public ServerMain serverMain;
    private ServerAuxiliaryThread serverAuxiliaryThread = null;

    /**
     * Method setServerAuxiliaryThread sets the serverAuxiliaryThread of this ServerMultiplexer object.
     *
     * @param serverAuxiliaryThread the serverAuxiliaryThread of this ServerMultiplexer object.
     */
    public void setServerAuxiliaryThread(ServerAuxiliaryThread serverAuxiliaryThread) {
        this.serverAuxiliaryThread = serverAuxiliaryThread;
    }

    /**
     * Method getLobby returns the lobby of this ServerMultiplexer object.
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
    private final Integer port;
    private volatile boolean ended = false;

    /**
     * Method isEnding returns the ending of this ServerMultiplexer object.
     *
     * @return the ending (type boolean) of this ServerMultiplexer object.
     */
    public synchronized boolean isEnding() {
        return ending;
    }

    /**
     * Method setEnding sets the ending of this ServerMultiplexer object.
     *
     * @param ending the ending of this ServerMultiplexer object.
     */
    public synchronized void setEnding(boolean ending) {
        this.ending = ending;
    }

    /**
     * Constructor ServerMultiplexer creates a new ServerMultiplexer instance.
     *
     * @param controller of type Controller
     * @param port       the port
     */
    public ServerMultiplexer(Controller controller, Integer port) {
        this.controller = controller;
        this.port = port;
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

        while (true) {
            try {
                serverSocket = new ServerSocket(port);
                break;
            } catch (IOException e) {
                System.out.println("port not available");
                try {
                    serverSocket.close();
                } catch (IOException ioException) {
                    //ioException.printStackTrace();
                }
            }
        }

        System.out.println(Colors.ANSI_YELLOW + "Server ready on port " + Colors.ANSI_GREEN + port + Colors.ANSI_RESET);
        started = false;
        //serverAuxiliaryThread.printInfo();
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
                System.out.println("Closing connection before connecting to all players");
                closeConnections();
                return;
                //break; //In case the serverSocket gets closed
            }
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
            System.out.println("Cannot communicate if the player wants to resume");
            closeConnections();
            return;
        }
        if (lobby.getPlayers().size() != lobby.getnPlayer() || lobby.getPlayers().size() < 2) {
            closeConnections();
            return;
        }
        controller.setLobby(lobby);
        controller.createMatch(resumeGame);
        started = true;
    }

    /**
     * Method connectNewPlayer wait for a new player to connect
     *
     * @throws IOException when
     */
    private void connectNewPlayer() throws IOException {
        int val = playersThread.size() + 1;
        System.out.println("Waiting for player " + val);
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
        serverAuxiliaryThread.setMatch(null);
        for (ServerThread thread : playersThread) {
            thread.closeConnection();
            thread.interrupt();
        }
        try {
            while (nConnectionPlayer > 1) {
                synchronized (this) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            serverSocket.close();
        } catch (IOException | NullPointerException e) {
            System.out.println("cannot close server");
        }

        ended = true;
    }

    /**
     * Method SetNickname try to set the nickname in the lobby
     *
     * @param name of type String
     * @return boolean nickname in lobby
     */
    public synchronized boolean setNicknameInLobby(String name) {
        if (name.split(":")[0].equalsIgnoreCase("Chat"))
            return false;
        for (String s : lobby.getPlayers()) {
            if (name.equalsIgnoreCase(s)) {
                System.out.println(name + "-" + s);
                return false;
            }
        }
        return lobby.addPlayer(name);
    }

    /**
     * Method addConnected increment nConnectionPlayer
     */
    public synchronized void addConnected() {
        nConnectionPlayer++;
    }

    /**
     * Method removeConnected decrement nConnectionPlayer
     */
    public synchronized void removeConnected() {
        nConnectionPlayer--;
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
        while (!ended) {
            try {
                synchronized (this) {
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        if (msg.nickname.split(":")[0].equalsIgnoreCase("Chat")) {
            String chatMsg = msg.nickname.split("\n", 2)[0];
            for (ServerThread thread : playersThread) {
                try {
                    thread.sendMsg(new MsgToClient(thread.getNick(), chatMsg, chatMsg, null));
                } catch (IOException e) {
                    System.out.println("cannot send ");
                }
            }
        }
        setChanged();
        notifyObservers(msg);
    }

    /**
     * Method ConnectObserver set the ServerThread as observer of the  observable object
     *
     * @param observable of type Match
     */
    public void connectObservers(Observable observable) {
        if (serverAuxiliaryThread != null)
            serverAuxiliaryThread.setMatch((Match) observable);
        for (ServerThread observer : playersThread) {
            observable.addObserver(observer);
        }
    }
}