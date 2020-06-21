package it.polimi.ingsw;

import java.util.Scanner;

/**
 * Class App the starting class
 */
public class App {
    /**
     * Method main is the main method of the app class
     *
     * @param args of type String[]
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an option:\n0)Server\n1)Client");
        System.out.print(">");
        int let = scanner.nextInt();
        if (let == 0)
            new ServerMain().run();
        else if (let == 1)
            new ClientStarter().run();
        else
            System.out.println("Not a valid input");
    }
}
