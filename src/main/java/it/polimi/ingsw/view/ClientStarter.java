package it.polimi.ingsw.view;

import java.util.Scanner;

public class ClientStarter {
    public static void main(String[] args) {
        ClientMain clientMain = new ClientMain();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to use GUI? (y/n)");
        if (scanner.nextLine().equalsIgnoreCase("n"))
            clientMain.run();
    }
}
