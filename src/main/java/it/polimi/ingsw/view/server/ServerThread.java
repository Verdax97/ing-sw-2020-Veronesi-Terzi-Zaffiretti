package it.polimi.ingsw.view.server;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.MsgToServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * Class ServerThread is thread that read incoming messages from client and send replies
 */
public class ServerThread extends Thread implements Observer {

    private final ServerMultiplexer server;
    private String nick;
    protected Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    private final int pos;
    public volatile boolean waitForStart;
    private volatile boolean going = true;

    private volatile boolean fired;

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
     * Method run for invoking thread
     */
    public void run() {
        try {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())
            ) {
                socketOut = objectOutputStream;
                socketIn = objectInputStream;

                Setup();
                while (!waitForStart) {
                    synchronized (this) {
                        wait();
                    }
                }
                while (going) {
                    MsgToServer msgToServer = ReceiveMsg();
                    if (msgToServer == null)
                        break;
                    server.ReceiveMsg(msgToServer);
                    fired = false;
                }
            } catch (IOException e) {
                //System.out.println(e.toString());
                System.out.println("Connection ended with " + this.nick);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted thread " + this.getName());
        }
        CloseConnection();
    }

    /**
     * Method Setup initializes lobby size and nickname
     */
    private void Setup() throws IOException {
        SetupLobbySize();

        SetupNickname();

        //
        SendMsg(new MsgPacket(nick, Messages.waitTurn, "Waiting for players", null));
        ReceiveMsg();

        //add user to the number successfully connected
        System.out.println("Player " + nick + " has joined the game");
        server.addConnected();
    }

    /**
     * Method SetupLobbySize for setting the number of players
     */
    private void SetupLobbySize() throws IOException {
        if (pos == 0) {
            String mess = Messages.lobby;
            String err = "";
            while (true) {
                //insert player number
                SendMsg(new MsgPacket(nick, err + mess, "", null));

                //read response
                MsgToServer msgToServer = ReceiveMsg();
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
     */
    private void SetupNickname() throws IOException {
        System.out.println("Waiting for player " + pos + " nickname");
        String mess = Messages.nickname;
        String err = "";
        while (true) {
            //insert player nickname
            SendMsg(new MsgPacket(nick, err + mess, "", null));
            //read response
            nick = Objects.requireNonNull(ReceiveMsg()).nickname;
            if (server.SetNickname(nick)) {
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
     * @return boolean
     */
    public boolean AskForResume() throws IOException {
        String mess = Messages.resume;
        String err = "";
        //send msg asking for resume
        SendMsg(new MsgPacket(nick, err + mess, "", null));

        //read response
        MsgToServer msgToServer = ReceiveMsg();
        return (msgToServer.x == 1);
    }

    /**
     * Method SendMsg for sending objects via socket
     *
     * @param msg of type MsgPacket
     */
    private void SendMsg(MsgPacket msg) throws IOException {
        try {
            //System.out.println(nick + " receiving message directed to " + msg.nickname + " msg= " + msg.msg);
            //socketOut.reset();
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
     *
     * @return MsgToServer
     */
    private MsgToServer ReceiveMsg() throws IOException {
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
    public void CloseConnection() {
        try {
            socketIn.close();
            socketOut.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        server.CloseConnection();
    }


    /**
     * Method update when receives an update send the message to the client
     *
     * @param o   of type Observable
     * @param arg of type Object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Match) || !(arg instanceof MsgPacket)) {
            throw new IllegalArgumentException();
        }

        //send the msg packet
        if (((MsgPacket) arg).msg.equalsIgnoreCase("end"))
            going = false;

        //send the message
        try {
            SendMsg((MsgPacket) arg);
            fired = true;
        } catch (IOException e) {
            CloseConnection();
        }
    }
}
