package it.polimi.ingsw;

import it.polimi.ingsw.view.GUI.LauncherApp;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Application;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The type Client starter.
 */
public class ClientStarter {

    /**
     * Method startClient start the gui or the cli client
     *
     * @param gui of type boolean
     */
    public void startClient(boolean gui) {
        ClientMain clientMain = new ClientMain();
        if (gui)
            Application.launch(LauncherApp.class);
        else {
            String IP;
            int port;
            do {
                clientMain.CLI = true;
                System.out.println("Insert Server IP");
                System.out.print(">");
                Scanner scanner = new Scanner(System.in);
                IP = scanner.nextLine();
                System.out.println("Insert Server port");
                System.out.print(">");
                while (true) {
                    try {
                        port = scanner.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Insert a valid port");
                        scanner = new Scanner(System.in);
                    }
                }
            } while (!clientMain.InitializeClient(IP, port));
            clientMain.run();
        }
    }
}
