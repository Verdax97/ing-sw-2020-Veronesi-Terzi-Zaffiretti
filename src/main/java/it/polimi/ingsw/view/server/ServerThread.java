package it.polimi.ingsw.view.server;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgToClient;
import it.polimi.ingsw.model.MsgToServer;
import it.polimi.ingsw.view.Colors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * Class ServerThread is thread that read incoming messages from client and send replies
 */
public class ServerThread extends Thread implements Observer {

    private final ServerMultiplexer server;
    private String nick;
    /**
     * The Socket.
     */
    protected Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    private boolean isEnding = false;

    private final int pos;
    private volatile boolean going = true;

    /**
     * Constructor ServerThread creates a new ServerThread instance.
     *
     * @param clientSocket of type Socket
     * @param string       of type String
     * @param server       of type ServerMultiplexer
     * @param pos          of type int
     */
    public ServerThread(Socket clientSocket, String string, ServerMultiplexer server, int pos) {
        this.socket = clientSocket;
        this.server = server;
        this.nick = string;
        this.pos = pos;
        this.nick = "temp";
    }


    /**
     * Method getNick returns the nick of this ServerThread object.
     *
     * @return the nick (type String) of this ServerThread object.
     */
    public String getNick() {
        return nick;
    }


    /**
     * Method run for invoking thread
     */
    public void run() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            socketOut = objectOutputStream;
            socketIn = objectInputStream;

            setup();
            while (going) {
                MsgToServer msgToServer = receiveMsg();
                if (msgToServer == null)
                    throw new IOException("Received message was null");

                server.receiveMsg(msgToServer);
            }
        } catch (IOException e) {
            System.out.println("Connection ended with " + Colors.ANSI_YELLOW + nick + Colors.ANSI_RESET);
        }
        closeConnection();
    }

    /**
     * Method Setup initializes lobby size and nickname
     *
     * @throws IOException when cannot connect to the client
     */
    private void setup() throws IOException {
        setupLobbySize();

        setupNickname();

        //send confirm message to the player
        sendMsg(new MsgToClient(nick, Messages.WAIT_TURN, "Waiting for players", null));

        //add user to the number successfully connected
        System.out.println("Player " + Colors.ANSI_YELLOW + nick + Colors.ANSI_RESET + " has joined the game");
        server.addConnected();
    }

    /**
     * Method SetupLobbySize for setting the number of players
     * @throws IOException when cannot connect to the client
     */
    private void setupLobbySize() throws IOException {
        if (pos == 0) {
            String mess = Messages.LOBBY;
            String err = "";
            while (true) {
                //insert player number
                sendMsg(new MsgToClient(nick, err + mess, "", null));

                //read response
                MsgToServer msgToServer = receiveMsg();
                if (msgToServer == null)
                    throw new IOException("Received message was null");
                int n = msgToServer.x;
                if (n == 2 || n == 3) {
                    server.getLobby().setnPlayer(n);
                    return;
                } else {
                    err = "Error Input not valid\n";
                }
            }
        }
    }

    /**
     * Method SetupNickname check if the nickname is available on the server
     * @throws IOException when cannot connect to the client
     */
    private void setupNickname() throws IOException {
        int v = pos + 1;
        System.out.println("Waiting for player " + v + " nickname");
        String mess = Messages.INSERT_NICKNAME;
        String err = "";
        while (true) {
            //insert player nickname
            sendMsg(new MsgToClient(nick, err + mess, "", null));
            //read response
            MsgToServer msgToServer = receiveMsg();
            if (msgToServer == null)
                throw new IOException("Received message was null");
            nick = msgToServer.nickname;
            if (server.setNicknameInLobby(nick)) {
                break;
            } else {
                err = "Error Nickname not valid\n";
                System.out.println(err);
            }
        }
    }

    /**
     * Method AskForResume ask the player who created the lobby if he wants to resume an old game
     *
     * @throws IOException the io exception
     */
    public void askForResume() throws IOException {
        String mess = Messages.RESUME;
        String err = "";
        //send msg asking for resume
        sendMsg(new MsgToClient(nick, err + mess, "", null));
    }

    /**
     * Method SendMsg for sending objects via socket
     *
     * @param msg of type MsgPacket
     * @throws IOException the io exception
     */
    public void sendMsg(MsgToClient msg) throws IOException {
        try {
            socketOut.writeObject(msg);
            socketOut.flush();
        } catch (IOException e) {
            System.out.println("Can't send message");
            going = false;
            throw e;
        }
    }

    /**
     * Method ReceiveMsg for receiving objects via socket
     * @throws IOException when cannot connect to the client
     *
     * @return MsgToServer
     */
    private MsgToServer receiveMsg() throws IOException {
        try {
            return (MsgToServer) socketIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("No more received packets");
            //CloseConnection();
            going = false;
            if (e instanceof IOException)
                throw (IOException) e;
            else throw new IOException();
        }
        //return null;
    }

    /**
     * Method CloseConnection close the connection to the client
     */
    public void closeConnection() {
        if (isEnding)
            return;
        isEnding = true;
        going = false;
        try {
            socketIn.close();
            socketOut.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.toString() + " error in closing the connection with " + Colors.ANSI_YELLOW + nick + Colors.ANSI_RESET);
        }
        server.removeConnected();
        server.closeConnections();
        synchronized (server) {
            server.notifyAll();
        }
    }


    /**
     * Method update when receives an update send the message to the client
     *
     * @param o   of type Observable
     * @param arg of type Object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Match) || !(arg instanceof MsgToClient)) {
            throw new IllegalArgumentException();
        }

        //send the msg packet
        if (((MsgToClient) arg).msg.equalsIgnoreCase("end"))
            going = false;

        //send the message
        try {
            sendMsg((MsgToClient) arg);
        } catch (IOException e) {
            going = false;
        }
    }
}
