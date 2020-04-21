package it.polimi.ingsw.view;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class ServerMain
{
    public static void main( String[] args )
    {
        EchoServer server = new EchoServer(4567);
        try
        {
            server.startServer();
        } catch(IOException e)
        {
                System.err.println(e.getMessage());
        }
    }
}
