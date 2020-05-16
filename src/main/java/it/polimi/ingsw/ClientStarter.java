package it.polimi.ingsw;

import it.polimi.ingsw.view.client.ClientMain;

import java.util.Scanner;

public class ClientStarter {
    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to use GUI? (y/n)");
        if (scanner.nextLine().equalsIgnoreCase("n"))
            clientMain.run();
        //at the end of the program
        System.out.println("Press enter to end the program");
        scanner.nextLine();
        System.out.println("Program ended");
    }
}
