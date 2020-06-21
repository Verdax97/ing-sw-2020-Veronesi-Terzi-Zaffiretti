package it.polimi.ingsw.view.server;

import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.view.Colors;
import it.polimi.ingsw.view.ServerView;

import java.util.Scanner;

/**
 * Class ServerAuxiliaryThread is for using commands on the server
 */
public class ServerAuxiliaryThread extends Thread {

    private Match match;
    private boolean close = false;

    /**
     * Method setMatch sets the match of this ServerAuxiliaryThread object.
     *
     * @param match the match of this ServerAuxiliaryThread object.
     */
    public void setMatch(Match match) {
        this.match = match;
    }

    /**
     * Method setClose sets the close of this ServerAuxiliaryThread object.
     *
     * @param close the close of this ServerAuxiliaryThread object.
     */
    public void setClose(boolean close) {
        this.close = close;
    }

    ServerView serverView = new ServerView();
    Scanner scanner = new Scanner(System.in);

    /**
     * Method run main method of the thread
     */
    @Override
    public void run() {
        while (!close) {
            printInfo();
            String s = scanner.nextLine();
            if (s.equalsIgnoreCase("quit")) {
                printColor("Closing the server", Colors.ANSI_RED);
                System.exit(1);
            } else if (s.equalsIgnoreCase("print")) {
                if (match == null)
                    printColor("Match is not started yet", Colors.ANSI_RED);
                else
                    serverView.printBoard(match.getBoard(), match);
            } else {
                printColor("Invalid command", Colors.ANSI_RED);
            }
        }
    }

    /**
     * Method printColor prints a message with a specified color
     *
     * @param msg   of type String
     * @param color of type String
     */
    private void printColor(String msg, String color) {
        System.out.println(color + msg + Colors.ANSI_RESET);
    }

    /**
     * Method printInfo prints the server commands
     */
    public void printInfo() {
        System.out.println("Type " + Colors.ANSI_RED + "quit" + Colors.ANSI_RESET + " to close the server.\nType " + Colors.ANSI_GREEN + "print" + Colors.ANSI_RESET + " to print the board");
    }
}
