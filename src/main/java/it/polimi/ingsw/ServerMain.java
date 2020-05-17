package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.view.server.ServerMultiplexer;

/**
 * Hello world!
 */
public class ServerMain implements Runnable {
    @Override
    public void run() {
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller);
        controller.setServerMultiplexer(serverMultiplexer);
        serverMultiplexer.addObserver(controller);
        serverMultiplexer.run();
    }
}
