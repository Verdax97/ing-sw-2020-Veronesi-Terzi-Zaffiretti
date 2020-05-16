package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Colors;

import java.util.*;

public class ClientInput extends Observable {
    private final ClientMain clientMain;
    private Scanner scanner;
    private int selectedX = -5, selectedY = -5;
    private int precSelectedX = -5, precSelectedY = -5;

    public ClientInput(ClientMain clientMain) {
        this.clientMain = clientMain;
        this.scanner = new Scanner(System.in);
    }

    public void ParseMsg(MsgPacket msgPacket) {
        String msg = msgPacket.msg;
        int[] arr = {-5, -5, -5, -5};
        //String alt = msgPacket.altMsg;
        //wait for input only in particular cases
        //StringBuilder answ = new StringBuilder();

        if (msg.split(" ")[0].equalsIgnoreCase("Error")) {
            System.out.println(Colors.ANSI_RED + msg.split("\n", 2)[0] + Colors.ANSI_RESET);
            msg = msg.split("\n", 2)[1];
            selectedX = precSelectedX;
            selectedY = precSelectedY;
        }
        precSelectedX = selectedX;
        precSelectedY = selectedY;

        if (msg.equalsIgnoreCase("Lobby")) {
            System.out.println("Select number of players (2/3)");
            arr[0] = ReadIntInput();
        }

        if (msg.equalsIgnoreCase("Insert Nickname")) {
            System.out.println(msg);
            clientMain.setNick(ReadStringInput());
        }
        if (msg.equalsIgnoreCase("Start")) {
            System.out.println("Starting the game");
        }

        if (msg.equalsIgnoreCase("choseGods")) {
            arr[0] = SelectGod(msgPacket, "Chose gods for all players by inserting corresponding value (one at the time)");
        }

        if (msg.equalsIgnoreCase("choseYourGod")) {
            arr[0] = SelectGod(msgPacket, "Chose your god by inserting corresponding value");
        }

        if (msg.equalsIgnoreCase("Place")) {
            for (int i = 0; i < 2; i++) {
                System.out.println("Place worker " + i + ":");
                while (true) {
                    int[] worker = SelectCell();
                    if (worker[0] >= 0 && worker[0] < 5 && worker[1] >= 0 && worker[1] < 5) {
                        if (msgPacket.board.getCell(worker[0], worker[1]).getWorker() == null) {
                            arr[2 * i] = worker[0];
                            arr[2 * i + 1] = worker[1];
                            break;
                        } else {
                            System.out.println("This cell is already occupied, try another one");
                        }
                    } else {
                        System.out.println("Selected cell is not valid");
                    }
                }
            }
        }

        if (msg.equalsIgnoreCase("StartTurn")) {
            System.out.println("Your Turn");
            Reply(-5, -5, -5, -5);
            return;
        }
        if (msg.equalsIgnoreCase("BeforeMove")) {
            int[] coord;
            ArrayList<int[]> movePossibilities;
            ArrayList<int[]> beforeMovePossibilities;
            do {
                while (true)//the worker can make a move?
                {
                    while (true)//the selected cell is correct?
                    {
                        System.out.println("Select the cell with your worker that you want to use");
                        coord = SelectCell();
                        if (msgPacket.board.getCell(coord[0], coord[1]).getWorker() != null) {
                            if (msgPacket.board.getCell(coord[0], coord[1]).getWorker().getPlayer().getNickname().equals(clientMain.getNick()))
                                break;
                            else
                                System.out.println("This is not your worker");
                        } else
                            System.out.println("There are no worker on this cell");
                    }

                    movePossibilities = CheckAround(msgPacket.board, coord[0], coord[1], msgPacket.board.getCell(coord[0], coord[1]).getWorker().getPlayer().getGodPower(), 1);
                    beforeMovePossibilities = CheckAround(msgPacket.board, coord[0], coord[1], msgPacket.board.getCell(coord[0], coord[1]).getWorker().getPlayer().getGodPower(), 0);
                    if (movePossibilities.size() > 0 || beforeMovePossibilities.size() > 0)
                        break;
                    else
                        System.out.println("This worker has no moves available, i can't let you lose like an uncivilized monkey, retry.");
                }
                if (!(beforeMovePossibilities.size() == 0)) {
                    //print all possibilities
                    System.out.println("All the possible coordinates that actions that your worker can do before he moves:");
                    PrintPossibilities(beforeMovePossibilities);

                    System.out.println("Do you want to make an action before the move phase?");
                    if (!Confirm())
                        break;

                    arr = SelectPossibility(beforeMovePossibilities);
                }
                /*
                System.out.println("Recap:\n" +
                                    "   cell selected: "+coord[0] + ", " + coord[1] + "\n" +
                                    "   "+ possibilityString.toString());*/
            } while (!Confirm());//are you sure about your choice?
            arr[0] = coord[0];
            arr[1] = coord[1];
            selectedX = arr[0];
            selectedY = arr[1];
        }

        if (msg.equalsIgnoreCase("Move Again")) {
            System.out.println("You the possibility to make another move.");
            System.out.println("Do you want to use your god power?(y/n)");
            if (ReadStringInput().equalsIgnoreCase("y")) {
                arr[0] = 1;
                msg = "Move";//to make another move action
            } else
                arr[0] = 0;
        }

        if (msg.equalsIgnoreCase("Move")) {
            int x = 0, y = 0;

            System.out.println("You must move");
            do {
                //print all possibilities
                System.out.println("All the possible coordinates where your worker can move:");
                ArrayList<int[]> movePossibilities = CheckAround(msgPacket.board, selectedX, selectedY, msgPacket.board.getCell(selectedX, selectedY).getWorker().getPlayer().getGodPower(), 1);
                PrintPossibilities(movePossibilities);
                SelectPossibility(movePossibilities);
            } while (!Confirm());
            arr[2] = x;
            arr[3] = y;
            selectedY = y;
            selectedX = x;
        }

        if (msg.equalsIgnoreCase("Build")) {
            int x = 0, y = 0;
            System.out.println("You must move");
            do {
                //print all possibilities
                System.out.println("All the possible coordinates where your worker can build:");
                ArrayList<int[]> buildPossibilities = CheckAround(msgPacket.board, selectedX, selectedY, msgPacket.board.getCell(selectedX, selectedY).getWorker().getPlayer().getGodPower(), 2);
                PrintPossibilities(buildPossibilities);
                SelectPossibility(buildPossibilities);
            } while (!Confirm());
            arr[2] = x;
            arr[3] = y;
            selectedY = y;
            selectedX = x;
        }
        if (msg.equalsIgnoreCase("Waiting")) {
            System.out.println(msgPacket.altMsg);
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {//
        }
        Reply(arr[0], arr[1], arr[2], arr[3]);
    }

    public void Reply(int x, int y, int targetX, int targetY) {
        //clientMain.setReplyMsg(new MsgToServer(clientMain.getNick(), x,y,targetX, targetY));
        clientMain.setReadyToSend(true);

        setChanged();
        notifyObservers(new MsgToServer(clientMain.getNick(), x, y, targetX, targetY));
    }

    public String ReadStringInput() {
        System.out.print(">");
        String a = scanner.nextLine();
        scanner = new Scanner(System.in);
        return a;
    }

    public int ReadIntInput() {
        int a;
        while (true) {

            try {
                System.out.print(">");
                a = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Insert a valid value");
                scanner = new Scanner(System.in);
            }
        }
        scanner = new Scanner(System.in);
        return a;
    }

    private int SelectGod(MsgPacket msgPacket, String msg) {
        System.out.println(msg);
        System.out.println(msgPacket.altMsg);
        while (true) {
            int val = ReadIntInput();
            System.out.println("You selected the god with number " + val);
            if (Confirm())
                return val;
            System.out.println(msg);
            System.out.println(msgPacket.altMsg);
        }
    }

    private int[] SelectCell() {
        while (true) {
            int x, y;
            System.out.print("Insert x coordinate (0/4): ");
            x = ReadIntInput();
            System.out.print("Insert y coordinate (0/4): ");
            y = ReadIntInput();
            if (y < 5 && y >= 0 && x < 5 && x >= 0) {
                System.out.println("You selected the cell (" + x + "," + y + ")");
                if (Confirm()) {
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

    private boolean Confirm() {
        System.out.println("Are you sure? (y/n)");
        return ReadStringInput().equalsIgnoreCase("y");
    }

    private ArrayList<int[]> CheckAround(Board board, int tempx, int tempy, God god, int phase) {
        ArrayList<int[]> arr = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int x = tempx + i;
                int y = tempy + j;
                if ((x >= 0) && (x < 5) && (y >= 0) && (y < 5)) {
                    int ret = -1;
                    if (phase == 0)
                        ret = god.CheckPlayerTurn(board, board.getCell(tempx, tempy), x, y);
                    if (phase == 1)
                        ret = god.CheckMove(board, board.getCell(tempx, tempy), x, y);
                    if (phase == 2)
                        ret = god.CheckBuild(board, board.getCell(tempx, tempy), x, y);
                    if (ret >= 0) {
                        arr.add(new int[]{x, y});//add to the list of possible moves
                    }
                }
            }
        }
        return arr;
    }

    private void PrintPossibilities(ArrayList<int[]> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println(i + ") (" + arrayList.get(i)[0] + ", " + arrayList.get(i)[1] + ")");
        }
    }

    private int[] SelectPossibility(ArrayList<int[]> arrayList) {
        int[] arr = {-5, -5, -5, -5};
        StringBuilder possibilityString;
        System.out.println("Insert corresponding value:");
        while (true)//select the action you want to make
        {
            int in = ReadIntInput();
            if (in >= 0 && in < arrayList.size()) {
                possibilityString = new StringBuilder();
                possibilityString.append(in).append("action selected: ");
                arr[2] = arrayList.get(in)[0];
                arr[3] = arrayList.get(in)[1];
                possibilityString.append(" (").append(arr[2]).append(", ").append(arr[3]).append(")");
                break;
            } else
                System.out.println("This value is not valid, try another one.");
        }
        System.out.println("Recap:\n" +
                "   cell selected: " + selectedX + ", " + selectedY + "\n" +
                "   " + possibilityString.toString());
        return arr;
    }
}
