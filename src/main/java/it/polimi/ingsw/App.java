package it.polimi.ingsw;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("type server or client to start the server or the client");
        String let = scanner.nextLine();
        if (let.equalsIgnoreCase("server"))
            new ServerMain().run();
        else if (let.equalsIgnoreCase("client"))
            new ClientStarter().run();
        else
            System.out.println("no valid input, ending program");
    }
}
