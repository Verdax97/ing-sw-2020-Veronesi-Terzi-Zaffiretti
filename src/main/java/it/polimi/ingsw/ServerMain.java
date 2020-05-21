package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.view.server.ServerMultiplexer;
/**
 * Class ServerMain starts the server
 *
 * @author Davide
 * Created on 21/05/2020
 */
public class ServerMain implements Runnable {
    /**
     * Method run
     */
    @Override
    public void run() {
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller);
        controller.setServerMultiplexer(serverMultiplexer);
        serverMultiplexer.addObserver(controller);
        serverMultiplexer.run();
    }
}
