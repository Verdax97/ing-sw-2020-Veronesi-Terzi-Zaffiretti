package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MsgPacket;

import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class ServerThread extends Thread implements Observer {
    private ServerMultiplexer server;
    private String nick;
    protected Socket socket;
    private Scanner socketIn;
    private PrintWriter socketOut;
    private String msg;
    private boolean msgReady;
    private boolean sent;
    private final int pos;

    public ServerThread(Socket clientSocket, String string, ServerMultiplexer server, int pos) {
        this.socket = clientSocket;
        this.server = server;
        this.nick = string;
        this.pos = pos;
    }

    public void run()
    {
        try
        {
            socketOut = new PrintWriter(socket.getOutputStream(), true);
            socketIn = new Scanner(new InputStreamReader(socket.getInputStream()));

            Setup();
            while (true)
            {
                if (msgReady)
                {
                    socketOut.println(msg);
                    socketOut.flush();
                }
                String message = socketIn.nextLine();
                if (message.equalsIgnoreCase("Ping"))
                {

                }
                else
                {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void Setup()
    {
        boolean valid = false;
        if (pos == 0)
        {
            while (valid)
            {
                //insert player number
                socketOut.println("Insert number of players");
                socketOut.flush();
                //read response
                String response = socketIn.nextLine();
                int n = Integer.parseInt(response);
                if (n == 2 || n == 3)
                {
                    valid = true;
                    server.lobby.setnPlayer(n);
                }
            }
        }

        valid = false;
        while (valid)
        {
            //insert nickname
            socketOut.println("Insert nickname");
            socketOut.flush();
            //read response
            String response = socketIn.nextLine();
            if(server.SetNickname(response))
            {
                valid = true;
            }
        }
        socketOut.println("Waiting for players");
        socketOut.flush();
        socketIn.nextLine();
        //add user to the number successfully connected
        server.addConnected();

        while (server.getnConnectionPlayer() < server.lobby.getnPlayer())
        {
            //wait for all players to be connected
        }

    }

    private void CloseConnection()
    {

    }

    public void SendMsg()
    {

    }

    public String getNick()
    {return nick;}

    @Override
    public void update(Observable o, Object arg)
    {
        if(!(o instanceof Match) || !(arg instanceof MsgPacket)){
            throw new IllegalArgumentException();
        }
        Match match = (Match)o;

    }
}
