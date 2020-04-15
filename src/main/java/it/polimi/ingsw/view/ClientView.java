package it.polimi.ingsw.view;
import java.io.*;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientView
{
    public static void StartClient(String ip, int port) throws IOException
    {
        try {
            Socket socket = new Socket(ip, port);
            System.out.println("Connection established");
            Scanner socketIn = new Scanner(socket.getInputStream());
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            Scanner stdin = new Scanner(System.in);
            try{
                while (true){
                    String inputLine = stdin.nextLine();
                    socketOut.println(inputLine);
                    socketOut.flush();
                    String socketLine = socketIn.nextLine();
                    System.out.println(socketLine);
                }
            } catch(NoSuchElementException e){
                System.out.println("Connection closed");
            } finally {
                stdin.close();
                socketIn.close();
                socketOut.close();
                socket.close();
            }
        } catch(Exception e) {System.out.println(e);}
    }
    public static void main(String[] args)
    {
        try
        {
            StartClient("127.0.0.1", 4567);
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
}
