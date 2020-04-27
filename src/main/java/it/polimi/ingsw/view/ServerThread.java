package it.polimi.ingsw.view;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread
{
    private EchoServer server;
    private String nick;
    protected Socket socket;

    public ServerThread(Socket clientSocket, String string, EchoServer server) {
        this.socket = clientSocket;
        this.server = server;
        this.nick = string;
    }

    public void run() {
        try {
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("Waiting for players");
            out.flush();
            while (!server.start)
            {
                if (this == server.playersThread.get(server.active) && server.lobby.getPlayers().size() > 1)
                {
                    out.println("Start to start close the lobby and start the game");
                    out.flush();
                    if (in.nextLine().equalsIgnoreCase("Start"))
                    {
                        server.start = true;
                    }
                }
            }
            out.println("Start");
            out.flush();
            //now the game begins
            while (true){
                if (this == server.playersThread.get(server.active)) {
                    out.println("active");
                    out.flush();
                    System.out.println("expecting cmd from: " + server.lobby.getPlayers().get(server.active));

                    String line = in.nextLine();
                    if (line.equals("quit")) {
                        break;
                    }

                    if (line.equalsIgnoreCase("1"))
                        server.active = 1;
                    if (line.equalsIgnoreCase("0"))
                        server.active = 0;

                    out.print("Now active: " + line + "\n");
                    if (this != server.playersThread.get(server.active)) {
                        out.println("Wait your turn");
                    }
                    out.flush();
                }
                else
                {
                    out.println("wait");
                    out.flush();
                }
            }
            //close connections
            in.close();
            out.close();
            socket.close();
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void CloseConnection()
    {

    }

    public String getNick()
    {return nick;}
}
