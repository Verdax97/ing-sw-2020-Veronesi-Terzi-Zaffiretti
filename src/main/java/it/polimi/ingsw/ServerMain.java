package it.polimi.ingsw;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.view.server.ServerMultiplexer;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

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
        Integer port = null;
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
        System.out.println("Type quit to close the server");
        Thread thread = new Thread(() ->
        {
            Scanner scanner1 = new Scanner(System.in);
            while (!scanner1.nextLine().equalsIgnoreCase("quit")) {

            }
            System.exit(0);
        }
        );

        while (true)
            newGame(port);
    }

    public void newGame(Integer port) {
        System.out.println("Starting new game.");
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller, port);
        controller.setServerMultiplexer(serverMultiplexer);
        serverMultiplexer.addObserver(controller);
        serverMultiplexer.serverMain = this;
        serverMultiplexer.run();
    }
}
