package it.polimi.ingsw.view;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args){
        Scanner stdin = new Scanner(System.in);
        boolean valid = false;
        String fine = "";
        while (!valid)
        {
            System.out.println("Insert Server IP");
            String IP = stdin.nextLine();
            System.out.println("Insert Server port");
            int port = stdin.nextInt();
            LineClient client = new LineClient(IP, port);
            try{
                fine = client.startClient();
                valid = true;
            }catch (IOException e){
                System.err.println("No Server found, check inserted IP and port");
            }
        }
        System.out.println(fine);
        System.out.println("Press enter to end the program");
        stdin.nextLine();
    }

}
