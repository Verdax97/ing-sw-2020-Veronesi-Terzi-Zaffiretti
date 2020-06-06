package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.MsgToServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class LineClient extends Thread implements Observer {
    private final ClientMain clientMain;
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    public LineClient(String ip, int port, ClientMain clientMain) {
        this.ip = ip;
        this.port = port;
        this.clientMain = clientMain;
    }

    public void startClient() throws IOException
    {
        //setup socket
        socket = new Socket(ip, port);
        //setup in/out socket
        socketIn = new ObjectInputStream(socket.getInputStream());
        socketOut = new ObjectOutputStream(socket.getOutputStream());
    }

    public void run()
    {
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
                clientMain.EndAll();
                break;
            }
        }
    }

    private void SendMsg(MsgToServer msg) {
        try {
            //socketOut.reset();
            socketOut.writeObject(msg);
            socketOut.flush();
        } catch (IOException e) {
            System.out.println("no more connection");
            clientMain.EndAll();
        }
    }

    private MsgPacket ReceiveMsg() {
        try {
            return (MsgPacket) socketIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            if (!(e instanceof IOException))
                System.out.println("The format of the message to receive is incorrect");
            else
                clientMain.EndAll();
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof ClientInput) || !(arg instanceof MsgToServer)) {
            throw new IllegalArgumentException();
        }
        SendMsg((MsgToServer) arg);
    }
}
