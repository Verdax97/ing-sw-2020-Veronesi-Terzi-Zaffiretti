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

        //wait for input only in particular cases

        if (msg.split(" ")[0].equalsIgnoreCase(Messages.error)) {
            System.out.println(Colors.ANSI_RED + msg.split("\n", 2)[0] + Colors.ANSI_RESET);
            msg = msg.split("\n", 2)[1];
            selectedX = precSelectedX;
            selectedY = precSelectedY;
        }
        precSelectedX = selectedX;
        precSelectedY = selectedY;

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
            arr[0] = SelectGod(msgPacket, "Chose gods for all players by inserting corresponding value (one at the time)");
        }

        if (msg.equalsIgnoreCase(Messages.choseYourGod)) {
            arr[0] = SelectGod(msgPacket, "Chose your god by inserting corresponding value");
        }

        if (msg.equalsIgnoreCase(Messages.placeWorkers)) {
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

        if (msg.equalsIgnoreCase(Messages.startTurn)) {
            System.out.println("Your Turn");
            Reply(-5, -5, -5, -5);
            return;
        }

        if (msg.equalsIgnoreCase(Messages.beforeMove)) {
            arr = BeforeMove(msgPacket);
        }

        if (msg.equalsIgnoreCase(Messages.moveAgain)) {
            System.out.println("You have the possibility to make another move phase.");
            System.out.println("Do you want to make it?(y/n)");
            if (ReadStringInput().equalsIgnoreCase("y")) {
                arr[0] = 1;
                msg = "Move";//to make another move action
            } else
                arr[0] = 0;
        }

        if (msg.equalsIgnoreCase(Messages.move)) {
            int[] temp = Move(msgPacket);

            arr[2] = temp[2];
            arr[3] = temp[3];

            selectedX = arr[2];
            selectedY = arr[3];
        }

        if (msg.equalsIgnoreCase(Messages.buildAgain)) {
            System.out.println("You have the possibility to make another build phase.");
            System.out.println("Do you want to make it?(y/n)");
            if (ReadStringInput().equalsIgnoreCase("y")) {
                arr[0] = 1;
                msg = "Build";//to make another move action
            } else
                arr[0] = 0;
        }

        if (msg.equalsIgnoreCase(Messages.build)) {
            int[] temp = Build(msgPacket);
            if (arr[0] != 1) {
                arr[2] = temp[2];
                arr[3] = temp[3];
            } else
                arr = temp;
            try {
                if (msgPacket.board.getCell(selectedX, selectedY).getWorker().getPlayer().getGodPower().getName().equalsIgnoreCase("Atlas")) {
                    System.out.println("You have the power to build a dome here");
                    System.out.println("Do you want to do it? (y/n)");
                    if (ReadStringInput().equalsIgnoreCase("y")) {
                        arr[1] = 1;
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("eeeeeeeeeeeeee se fai cagare");
            }
        }

        if (msg.equalsIgnoreCase(Messages.wait)) {
            System.out.println(msgPacket.altMsg);
        }

        try {
            Thread.sleep(200);//dunno why but with this it works
            Reply(arr[0], arr[1], arr[2], arr[3]);
        } catch (InterruptedException e) {
            //close all
            clientMain.EndAll();
        }
    }

    private int[] SelectWorker(Board board, String nickname) {
        ArrayList<int[]> possibilities = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Worker worker = board.getCell(i, j).getWorker();
                if (worker != null) {
                    if (worker.getPlayer().getNickname().equals(nickname)) {
                        possibilities.add(new int[]{i, j});
                    }
                }
            }
        }
        PrintPossibilities(possibilities);
        int sel = ReadIntInput();
        while (sel < 0 || sel >= possibilities.size()) {
            sel = ReadIntInput();
        }
        return possibilities.get(ReadIntInput());
    }

    private int[] BeforeMove(MsgPacket msgPacket) {
        System.out.println("parsing message");
        int[] coord;
        int[] arr = {-5, -5, -5, -5};
        ArrayList<int[]> movePossibilities;
        ArrayList<int[]> beforeMovePossibilities;
        while (true)//the worker can make a move?
        {
            while (true)//the selected cell is correct?
            {
                System.out.println("Select the cell with your worker that you want to use");
                coord = SelectWorker(msgPacket.board, clientMain.getNick());
                if (msgPacket.board.getCell(coord[0], coord[1]).getWorker() != null) {
                    if (msgPacket.board.getCell(coord[0], coord[1]).getWorker().getPlayer().getNickname().equals(clientMain.getNick()))
                        break;
                    else
                        System.out.println("This is not your worker");
                } else
                    System.out.println("There is no worker on this cell");
            }
            selectedX = coord[0];
            selectedY = coord[1];
            movePossibilities = CheckAround(msgPacket.board, coord[0], coord[1], msgPacket.board.getCell(coord[0], coord[1]).getWorker().getPlayer().getGodPower(), 1);
            beforeMovePossibilities = CheckAround(msgPacket.board, coord[0], coord[1], msgPacket.board.getCell(coord[0], coord[1]).getWorker().getPlayer().getGodPower(), 0);
            if (movePossibilities.size() > 0 || beforeMovePossibilities.size() > 0)
                break;
            else
                System.out.println("This worker has no moves left, I can't let you lose like an uncivilized monkey, retry.");
        }
        if (!(beforeMovePossibilities.size() == 0)) {
            //print all possibilities
            System.out.println("All the possible coordinates that actions that your worker can do before he moves:");
            PrintPossibilities(beforeMovePossibilities);

            System.out.println("Do you want to make an action before the move phase?");
            if (Confirm())
                arr = SelectPossibility(beforeMovePossibilities);
        }
        arr[0] = coord[0];
        arr[1] = coord[1];
        selectedX = arr[0];
        selectedY = arr[1];
        return arr;
    }

    private int[] Move(MsgPacket msgPacket) {
        int[] temp;
        System.out.println("You must move");
        //print all possibilities
        System.out.println("All the possible coordinates where your worker can move:");

        ArrayList<int[]> movePossibilities = CheckAround(msgPacket.board, selectedX, selectedY, msgPacket.board.getCell(selectedX, selectedY).getWorker().getPlayer().getGodPower(), 1);
        PrintPossibilities(movePossibilities);
        temp = SelectPossibility(movePossibilities);
        return new int[]{-5, -5, temp[2], temp[3]};
    }

    private int[] Build(MsgPacket msgPacket) {
        int[] temp;
        System.out.println("You must build");
        //print all possibilities
        System.out.println("All the possible coordinates where your worker can build:");
        if (msgPacket.board.getCell(selectedX, selectedY).getWorker() == null)
            return new int[]{-5, -5, -5, -5};
        ArrayList<int[]> buildPossibilities = CheckAround(msgPacket.board, selectedX, selectedY, msgPacket.board.getCell(selectedX, selectedY).getWorker().getPlayer().getGodPower(), 2);
        PrintPossibilities(buildPossibilities);
        temp = SelectPossibility(buildPossibilities);
        return temp;
    }

    public void Reply(int x, int y, int targetX, int targetY) {
        //clientMain.setReplyMsg(new MsgToServer(clientMain.getNick(), x,y,targetX, targetY));
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
                    if (ret > 0) {
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
        do//select the action you want to make
        {
            while (true) {
                System.out.println("Insert corresponding value:");
                possibilityString = new StringBuilder();
                int in = ReadIntInput();
                if (in >= 0 && in < arrayList.size()) {
                    possibilityString.append(in).append(") action selected: ");
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
        } while (!Confirm());
        return arr;
    }
}
