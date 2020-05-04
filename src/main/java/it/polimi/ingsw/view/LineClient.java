package it.polimi.ingsw.view;

import it.polimi.ingsw.model.MsgPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.invoke.MethodHandles.Lookup;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LineClient extends Thread {
    private final ClientMain clientMain;
    private final String ip;
    private final int port;
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;

    public LineClient(String ip, int port, ClientMain clientMain)
    {
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
        while (true)
        {
            MsgPacket msg;
            try
            {
                msg = ReceiveMsg();
            }catch (ClassNotFoundException | IOException e)
            {
                System.out.println("problem");
                break;
            }

            //pass the message to the main client
            clientMain.setReceivedMsg(msg);
            clientMain.setReadyToReceive(true);

            //exit if the game ends
            if(msg.msg.equalsIgnoreCase("end"))
                break;

            //wait to have reply msg ready
            while(!clientMain.isReadyToSend())
            {
                //wait until response is ready
            }

            clientMain.setReadyToSend(false);
            //send response message to the server
            try {
                SendMsg(clientMain.getReplyMsg());
            } catch (IOException e) {
                System.out.println("Connection to the server interrupted");
                break;
            }
        }

        System.out.println("the game is ended");
        try {
            EndClient();
        } catch (IOException e) {
            System.err.println("wtf is happening, socket throws an IOException");
        }
    }

    private MsgPacket ReceiveMsg() throws IOException, ClassNotFoundException {
        return (MsgPacket)socketIn.readObject();
    }

    private void SendMsg(MsgPacket msg) throws IOException
    {
        socketOut.writeObject(msg);
    }

    private void EndClient() throws IOException
    {
        socketIn.close();
        socketOut.close();
        socket.close();
    }

}
