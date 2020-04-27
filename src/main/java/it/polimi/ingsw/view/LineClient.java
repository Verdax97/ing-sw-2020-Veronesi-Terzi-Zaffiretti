package it.polimi.ingsw.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LineClient {
    private String ip;
    private int port;
    private String nick;
    private Socket socket;
    private Scanner socketIn;
    private Scanner stdin;
    private PrintWriter socketOut;

    public LineClient(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    public String startClient() throws IOException
    {
        //setup socket
        socket = new Socket(ip, port);
        //connection established message
        System.out.println("Connection established");
        //setup in/out socket
        socketIn = new Scanner(socket.getInputStream());
        socketOut = new PrintWriter(socket.getOutputStream());
        //read msg from cli
        Scanner stdin = new Scanner(System.in);
        String answer = "";

        //insertion in lobby e nickname setup
        do
            {
                if (!answer.equals(""))
                {
                    System.out.println(answer);
                    if (answer.equalsIgnoreCase("Too many players"))
                    {
                        EndClient();
                        return "Too many players";
                    }
                }
                System.out.println("Insert Nickname");
                nick = stdin.nextLine();
                //send the nickname to the server
                socketOut.println(nick);
                socketOut.flush();
                //read answer from server
                answer = socketIn.nextLine();
            }
        while (!answer.equalsIgnoreCase("valid nickname"));
        //now we just wait for the start (waiting msg)
        System.out.println(socketIn.nextLine());
        while (true)
        {
            answer = socketIn.nextLine();
            if (answer.equalsIgnoreCase("Start to start close the lobby and start the game"))
            {
                answer = stdin.nextLine();
                socketOut.println(answer);
                socketOut.flush();
            }
            else
            {
                System.out.println(answer);
                if (answer.equalsIgnoreCase("Start"))
                {
                    break;
                }
            }
        }

        try{
            boolean wait = false;
            while (true){
                //receive an answer for active
                String active = socketIn.nextLine();
                if (active.equalsIgnoreCase("active"))
                {
                    wait = false;

                    System.out.println(active);
                    String inputLine = stdin.nextLine();
                    //send msg
                    socketOut.println(inputLine);
                    socketOut.flush();
                    //receive answer
                    String socketLine = socketIn.nextLine();
                    //do stuff
                    System.out.println(socketLine);
                }
                else
                {
                    if (!wait)
                    {
                        wait = true;
                        System.out.println(active);
                    }
                }
            }
        } catch(NoSuchElementException e){
            System.out.println("Connection closed");
        } finally {
            EndClient();
        }
        return "";
    }

    private void EndClient() throws IOException
    {
        stdin.close();
        socketIn.close();
        socketOut.close();
        socket.close();
    }

}
