package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.view.server.ServerAuxiliaryThread;
import it.polimi.ingsw.view.server.ServerMultiplexer;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Class ServerMain starts the server
 */
public class ServerMain {

    boolean go = true;

    /**
     * Method startServer ask for the port and start the server
     */
    public void startServer() {
        int port;
        AtomicReference<Scanner> scanner = new AtomicReference<>(new Scanner(System.in));

        System.out.println("Insert server port:");
        while (true) {
            try {
                port = scanner.get().nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Insert a valid port");
                scanner.set(new Scanner(System.in));
            }
        }
        ServerAuxiliaryThread serverAuxiliaryThread = new ServerAuxiliaryThread();
        serverAuxiliaryThread.start();
        while (go)
            newGame(port, serverAuxiliaryThread);
    }

    /**
     * Method newGame starts a new game on the server
     *
     * @param port                  of type Integer
     * @param serverAuxiliaryThread of type ServerAuxiliaryThread
     */
    public void newGame(Integer port, ServerAuxiliaryThread serverAuxiliaryThread) {
        System.out.println("Starting new game.");
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller, port);
        serverMultiplexer.setServerAuxiliaryThread(serverAuxiliaryThread);
        controller.setServerMultiplexer(serverMultiplexer);
        serverMultiplexer.addObserver(controller);
        serverMultiplexer.serverMain = this;
        serverMultiplexer.run();
    }
}
