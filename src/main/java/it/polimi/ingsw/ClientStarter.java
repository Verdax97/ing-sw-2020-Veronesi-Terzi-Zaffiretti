package it.polimi.ingsw;

import it.polimi.ingsw.view.GUI.LauncherApp;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Application;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The type Client starter.
 */
public class ClientStarter implements Runnable {

    @Override
    public void run() {
        ClientMain clientMain = new ClientMain();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to use GUI? (y/n)");
        System.out.print(">");
        if (scanner.nextLine().equalsIgnoreCase("y"))
            Application.launch(LauncherApp.class);
        else {
            String IP;
            int port;
            do {
                clientMain.CLI = true;
                System.out.println("Insert Server IP");
                System.out.print(">");
                scanner = new Scanner(System.in);
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
        /*
        //at the end of the program
        System.out.println("Press enter to end the program");
        scanner.nextLine();
        System.out.println("Program ended");*/
    }
}
