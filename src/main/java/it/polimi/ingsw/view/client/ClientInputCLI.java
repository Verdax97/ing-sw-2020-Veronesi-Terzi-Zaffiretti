package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.model.SimpleGod;
import it.polimi.ingsw.view.Colors;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class ClientInputCLI extends the ClientInput class for the cli
 */
public class ClientInputCLI extends ClientInput {

    protected Scanner scanner = new Scanner(System.in);

    /** @see ClientInput#ClientInput(ClientMain) */
    public ClientInputCLI(ClientMain clientMain) {
        super(clientMain);
    }

    /** @see ClientInput#ParseMsg(MsgPacket) */
    @Override
    public void ParseMsg(MsgPacket msgPacket) {
        String msg = msgPacket.msg;
        int[] arr = {-5, -5, -5, -5};
        scanner = new Scanner(System.in);
        //wait for input only in particular cases

        if (msg.split(" ")[0].equalsIgnoreCase(Messages.error)) {
            System.out.println(Colors.ANSI_RED + msg.split("\n", 2)[0] + Colors.ANSI_RESET);
            msg = msg.split("\n", 2)[1];
        }

        if (msg.equalsIgnoreCase(Messages.lobby)) {
            System.out.println("Select number of players (2/3)");
            arr[0] = ReadIntInput();
        }

        if (msg.equalsIgnoreCase(Messages.nickname)) {
            System.out.println("Insert your nickname");
            clientMain.setNick(ReadStringInput());
        }

        if (msg.equalsIgnoreCase(Messages.start)) {
            System.out.println("Starting the game");
        }

        if (msg.equalsIgnoreCase(Messages.choseGods)) {
            System.out.println("Chose gods for all players by inserting corresponding value (one at the time)");
            System.out.println(msgPacket.altMsg);
            arr[0] = ReadIntInput();
            //arr[0] = SelectGod(msgPacket, "Chose gods for all players by inserting corresponding value (one at the time)");
        }

        if (msg.equalsIgnoreCase(Messages.choseYourGod)) {
            System.out.println("Chose your god by inserting corresponding value");
            System.out.println(msgPacket.altMsg);
            arr[0] = ReadIntInput();
            //arr[0] = SelectGod(msgPacket, "Chose your god by inserting corresponding value");
        }

        if (msg.equalsIgnoreCase(Messages.placeWorkers)) {
            System.out.println("Place your workers.");
            for (int i = 0; i < 2; i++) {
                System.out.println("Place worker " + i + ":");
                while (true) {
                    int[] worker = SelectCell();
                    if (worker[0] >= 0 && worker[0] < 5 && worker[1] >= 0 && worker[1] < 5) {
                        arr[2 * i] = worker[0];
                        arr[2 * i + 1] = worker[1];
                        break;
                    } else {
                        System.out.println("Selected cell is not valid");
                    }
                }
            }
        }

        if (msg.equalsIgnoreCase(Messages.startTurn)) {
            System.out.println("Your Turn");
            Reply(-5, -5, -5, -5);
            return;
        }

        if (msg.equalsIgnoreCase(Messages.selectWorker)) {
            System.out.println("Select your worker by inserting corresponding value");
            System.out.println(msgPacket.altMsg);
            arr[0] = ReadIntInput();
        }

        if (msg.equalsIgnoreCase(Messages.beforeMove)) {
            System.out.println("You have the possibility to make an action before the move phase.\nAll the possible actions:");
            System.out.println(msgPacket.altMsg);
            if (Confirm("Do you want to do it? (y/n)")) {
                arr[1] = 1;
                System.out.println("Insert value");
                arr[0] = ReadIntInput();
            } else {
                arr[1] = 0;
            }
            System.out.println("Insert a valid input");
        }

        if (msg.equalsIgnoreCase(Messages.moveAgain)) {
            System.out.println("You have the possibility to make another move phase.");
            if (Confirm("Do you want to do it? (y/n)")) {
                arr[1] = 1;
                msg = Messages.move;//to make another move action
            } else {
                arr[1] = 0;
            }
        }

        if (msg.equalsIgnoreCase(Messages.move)) {
            System.out.println("You must move.\nAll the possible moves your worker can do");
            System.out.println(msgPacket.altMsg);
            arr[0] = ReadIntInput();
        }

        if (msg.equalsIgnoreCase(Messages.buildAgain)) {
            System.out.println("You have the possibility to make another build phase.");
            if (Confirm("Do you want to do it? (y/n)")) {
                arr[1] = 1;
                msg = Messages.build;//to make another move action
            } else {
                arr[1] = 0;
            }
        }

        if (msg.equalsIgnoreCase(Messages.build)) {
            System.out.println("You must build.\nAll the possible build your worker can do");
            System.out.println(msgPacket.altMsg);
            arr[0] = ReadIntInput();

            int i;
            for (i = 0; i < msgPacket.board.players.size(); i++) {
                if (clientMain.getNick().equals(msgPacket.board.players.get(i)))
                    break;
            }
            if (msgPacket.board.gods.get(i).getName().equalsIgnoreCase("Atlas")) {
                System.out.println("You have the power to build a dome here");
                if (Confirm("Do you want to do it? (y/n)")) {
                    arr[2] = 1;
                }
            }
        }

        if (msg.equalsIgnoreCase(Messages.waitTurn)) {
            System.out.println(msgPacket.altMsg);
        }
/*
        try {
            Thread.sleep(50);//dunno why but with this it works
        } catch (InterruptedException e) {
            //close all
            clientMain.EndAll();
        }*/
        Reply(arr[0], arr[1], arr[2], arr[3]);
    }


    /**
     * Method ReadStringInput read the next string inserted
     *
     * @return String
     */
    public String ReadStringInput() {
        scanner = new Scanner(System.in);
        System.out.print(">");
        return scanner.nextLine();
    }

    /**
     * Method ReadIntInput read the next int inserted
     *
     * @return int
     */
    public int ReadIntInput() {
        int a;
        while (true) {
            try {
                scanner = new Scanner(System.in);
                System.out.print(">");
                a = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Insert a valid value");
            }
        }
        scanner = new Scanner(System.in);
        return a;
    }

    /**
     * Method SelectCell let the player select a cell on the board
     *
     * @return int[]
     */
    private int[] SelectCell() {
        while (true) {
            int x, y;
            System.out.print("Insert x coordinate (0/4): ");
            x = ReadIntInput();
            System.out.print("Insert y coordinate (0/4): ");
            y = ReadIntInput();
            if (y < 5 && y >= 0 && x < 5 && x >= 0) {
                System.out.println("You selected the cell (" + x + "," + y + ")");
                if (Confirm("Are you sure?(y/n)")) {
                    int[] arr = new int[2];
                    arr[0] = x;
                    arr[1] = y;
                    return arr;
                }
            } else {
                System.out.println("Coordinates not valid");
            }
        }
    }

    /**
     * Method Confirm asks for confirm to the player before doing something
     *
     * @param message of type String
     * @return boolean
     */
    private boolean Confirm(String message) {
        System.out.println(message);
        while (true) {
            String s = ReadStringInput();
            if (s.equalsIgnoreCase("y")) {
                return true;
            } else if (s.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Insert a valid input");
        }
    }

    /** @see ClientInput#printBoard(SimpleBoard) */
    @Override
    public void printBoard(SimpleBoard board) {
        if (board == null)
            return;
        if (board.players.size() == 0)
            return;

        ArrayList<String> players = board.players;
        ArrayList<SimpleGod> gods = board.gods;
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("-------------------------------------------------------------------");
        for (int i = 0; i < players.size(); i++) {
            System.out.print(clientMain.colors.get(i) + players.get(i) + " ");
            if (gods.size() > i) {
                if (gods.get(i) != null) {
                    System.out.print(gods.get(i).getName() + ": " + gods.get(i).getDescription() + Colors.ANSI_RESET);
                }
            }
            System.out.println(Colors.ANSI_RESET);
        }
        //now print the board
        System.out.print("\n");
        //System.out.println("Print the board (this message only for debug)");
        if (board.board == null)
            return;
        for (int j = 4; j >= 0; j--) {
            System.out.print(j + "|");
            for (int i = 0; i < 5; i++) {
                if (board.board[i][j] == 4) {
                    System.out.print("D" + " ");
                } else //search for worker on that cell
                {
                    int index;
                    boolean found = false;
                    for (index = 0; index < board.workers.size(); index++) {
                        if (board.workers.get(index)[0] == i && board.workers.get(index)[1] == j) {
                            int val = 0;

                            if (index >= 2 && index < 4)
                                val = 1;
                            else if (index >= 4 && index < 6)
                                val = 2;
                            System.out.print(clientMain.colors.get(val) + board.board[i][j] + Colors.ANSI_RESET + " ");
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                        System.out.print(board.board[i][j] + " ");//no worker and no dome
                }
            }
            System.out.println();
        }
        System.out.println("Y|---------");
        System.out.println("X 0 1 2 3 4\n");
    }

    /**
     * @see ClientInput#updateNotYourTurn()
     */
    @Override
    public void updateNotYourTurn() {
        System.out.println(Colors.ANSI_YELLOW + clientMain.getReceivedMsg().nickname + "'s turn, wait" + Colors.ANSI_RESET);
        System.out.println(clientMain.getReceivedMsg().altMsg);
        Reply(-5, -5, -5, -5);
    }

    /**
     * @see ClientInput#updateEndGame()
     */
    @Override
    public void updateEndGame() {
        System.out.println(clientMain.getReceivedMsg().altMsg);
    }

    /**
     * @see ClientInput#closeGame()
     */
    @Override
    public void closeGame() {
        System.out.println("Game is ended.\nClosing the application");
    }
}
