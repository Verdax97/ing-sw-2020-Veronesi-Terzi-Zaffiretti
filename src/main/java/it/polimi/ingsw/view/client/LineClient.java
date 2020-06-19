package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.MsgToServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

/**
 * Class LineClient manages the socket connection with the server.
 */
public class LineClient extends Thread implements Observer {
    private final ClientMain clientMain;
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    /**
     * Constructor LineClient creates a new LineClient instance.
     *
     * @param ip of type String
     * @param port of type int
     * @param clientMain of type ClientMain
     */
    public LineClient(String ip, int port, ClientMain clientMain) {
        this.ip = ip;
        this.port = port;
        this.clientMain = clientMain;
    }

    /**
     * Method startClient setup the connection to the server
     * @throws IOException when
     */
    public void startClient() throws IOException {
        //setup socket
        socket = new Socket(ip, port);
        //setup in/out socket
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * Method run is the main class of the thread
     */
    public void run() {
        while (true) {
            MsgPacket msg = ReceiveMsg();

            //pass the message to the main client
            if (msg != null) {
                clientMain.setReceivedMsg(msg);
                synchronized (clientMain) {
                    clientMain.notifyAll();
                }
                if (msg.msg.equalsIgnoreCase("end"))
                    break;
            } else {
                System.out.println("the game is ended");
                closeSocket();
                break;
            }
        }
    }

    /**
     * Method SendMsg sends message to the server
     *
     * @param msg of type MsgToServer
     */
    private void SendMsg(MsgToServer msg) {
        try {
            //socketOut.reset();
            socketOut.writeObject(msg);
            socketOut.flush();
        } catch (IOException e) {
            System.out.println("no more connection");
            closeSocket();
        }
    }

    /**
     * Method ReceiveMsg try to receive message from the server.
     *
     * @return MsgPacket
     */
    private MsgPacket ReceiveMsg() {
        try {
            return (MsgPacket) socketIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            if (!(e instanceof IOException))
                System.out.println("The format of the message to receive is incorrect");
            else
                closeSocket();
        }
        return null;
    }

    /**
     * Method closeSocket close the socket
     */
    void closeSocket() {
        try {
            socketIn.close();
            socketOut.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket already closed");
        }
        clientMain.EndAll();
    }

    /**
     * Method update is called when the client sends a message to the server
     *
     * @param o   of type Observable
     * @param arg of type Object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof ClientInput) || !(arg instanceof MsgToServer)) {
            throw new IllegalArgumentException();
        }
        SendMsg((MsgToServer) arg);
    }
}
