package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class ServerMain
{
    public static void main( String[] args )
    {
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller);
        controller.setServerMultiplexer(serverMultiplexer);
        serverMultiplexer.addObserver(controller);
        serverMultiplexer.run();
    }
}
