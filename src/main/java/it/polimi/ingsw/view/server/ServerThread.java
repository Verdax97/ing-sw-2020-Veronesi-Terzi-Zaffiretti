package it.polimi.ingsw.view.server;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.MsgToServer;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

public class ServerThread extends Thread implements Observer {

    private final ServerMultiplexer server;
    private String nick;
    protected Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    private final int pos;
    public volatile boolean waitForStart;
    private volatile boolean going = true;

    /*
    public void setFired(boolean fired) {
        this.fired = fired;
    }*/

    private volatile boolean fired;

    public ServerThread(Socket clientSocket, String string, ServerMultiplexer server, int pos) {
        this.socket = clientSocket;
        this.server = server;
        this.nick = string;
        this.pos = pos;
        this.nick = "temp";
    }


    public void run() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            socketOut = objectOutputStream;
            socketIn = objectInputStream;

            Setup();
            while (!waitForStart) {
                Thread.yield();
            }
            while (going) {
                MsgToServer msgToServer = ReceiveMsg();
                if (msgToServer == null)
                    break;
                server.ReceiveMsg(msgToServer);
                fired = false;
                while (fired)
                    Thread.yield();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    private void Setup() {
        SetupLobbySize();

        SetupNickname();

        //
        SendMsg(new MsgPacket(nick, Messages.wait, "Waiting for players", null, null));
        ReceiveMsg();

        //add user to the number successfully connected
        System.out.println("Player " + nick + " has joined the game");
        server.addConnected();
    }

    private void SetupLobbySize() {
        if (pos == 0) {
            String mess = "Lobby";
            String err = "";
            while (true) {
                //insert player number
                SendMsg(new MsgPacket(nick, err + mess, "", null, null));

                //read response
                MsgToServer msgToServer = ReceiveMsg();
                assert msgToServer != null;
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

    private void SetupNickname() {
        System.out.println("Waiting for player " + pos + " nickname");
        String mess = "Insert nickname";
        String err = "";
        while (true) {
            //insert player nickname
            SendMsg(new MsgPacket(nick, err + mess, "", null, null));
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

    private void SendMsg(MsgPacket msg) {
        try {
            //System.out.println(nick + " receiving message directed to " + msg.nickname + " msg= " + msg.msg);
            socketOut.reset();
            socketOut.writeObject(msg);
            socketOut.flush();
        } catch (IOException e) {
            System.out.println(e.toString());
            going = false;
        }
    }

    private MsgToServer ReceiveMsg() {
        try {
            return (MsgToServer) socketIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            if (e instanceof IOException) {
                System.out.println("connection crashed");
                //todo
                server.CloseConnection();
                Thread.currentThread().interrupt();
            } else {
                System.out.println("The message received from " + nick + " is wrong type");
                going = false;
            }
        }
        return null;
    }


    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof Match) || !(arg instanceof MsgPacket)) {
            throw new IllegalArgumentException();
        }

        //send the msg packet
        if (((MsgPacket) arg).msg.equalsIgnoreCase("end"))
            going = false;

        SendMsg((MsgPacket) arg);
        fired = true;
    }
}
