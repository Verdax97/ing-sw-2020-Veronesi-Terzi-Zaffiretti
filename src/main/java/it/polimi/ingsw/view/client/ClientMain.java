package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.MsgToServer;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.view.Colors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientMain implements Runnable {
    Scanner stdin = new Scanner(System.in);

    private volatile boolean readyToReceive = false;
    private MsgPacket receivedMsg;
    private ClientInput clientInput;
    public LineClient lineClient;
    private final ArrayList<String> colors = new ArrayList<>();

    public Board board;

    private String nick = "temp";

    Thread threadInput;

    private boolean end = false;

    public ClientMain() {
        this.clientInput = new ClientInput(this);
    }

    @Override
    public void run() {
        colors.add(Colors.ANSI_RED);
        colors.add(Colors.ANSI_GREEN);
        colors.add(Colors.ANSI_BLUE);
        while (!end) {
            end = CLIStuff();
        }
        EndAll();
        System.exit(1);
    }

    public boolean InitializeClient(String IP, int port) {
        LineClient client = new LineClient(IP, port, this);
        clientInput.addObserver(client);

        try {
            client.startClient();
        } catch (IOException e) {
            System.err.println("Server not reachable"); //server not available
            stdin = new Scanner(System.in);
            return false;
        }
        //connection established message
        System.out.println("Connection established");

        //create
        client.start();
        return true;
    }

    private boolean CLIStuff() {
        //wait to have reply msg ready
        if (!isReadyToRecive()) {
            return false;
        }
        //read received message
        setReadyToReceive(false);
        if (getReceivedMsg() == null)
            return true;
        //exit if the game ends
        if (getReceivedMsg().msg.equalsIgnoreCase("end")) {
            return true;
        }

        board = receivedMsg.board;
        printBoard(receivedMsg.board, receivedMsg.players);

        if (receivedMsg.nickname.equals(nick)) {
            //start a new thread for the input receiver
            Runnable runnable = () -> clientInput.ParseMsg(receivedMsg);
            threadInput = new Thread(runnable);
            threadInput.start();
        } else {
            System.out.println(receivedMsg.nickname + "'s turn, wait");
            clientInput.Reply(-5, -5, -5, -5);
        }
        return false;
    }

    public boolean isReadyToRecive() {
        return readyToReceive;
    }

    public void setReadyToReceive(boolean readyToReceive) {
        this.readyToReceive = readyToReceive;
    }

    public synchronized MsgPacket getReceivedMsg() {
        return receivedMsg;
    }

    public synchronized void setReceivedMsg(MsgPacket receivedMsg) {
        this.receivedMsg = receivedMsg;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void printBoard(Board board, ArrayList<Player> players) {
        if (players == null)
            return;

        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("-------------------------------------------------------------------");
        for (int i = 0; i < players.size(); i++) {
            System.out.print(colors.get(i) + players.get(i).getNickname() + " ");
            if (players.get(i).getGodPower() != null) {
                System.out.print(players.get(i).getGodPower().getName() + ": " + players.get(i).getGodPower().description + Colors.ANSI_RESET);
            }
            System.out.println(Colors.ANSI_RESET);
        }
        //now print the board
        System.out.print("\n");
        if (board != null) {
            //System.out.println("Print the board (this message only for debug)");
            for (int j = 4; j >= 0; j--) {
                System.out.print(j + "|");
                for (int i = 0; i < 5; i++) {
                    if (board.getCell(i, j).getDome()) {
                        System.out.print("D" + " ");
                    } else if (board.getCell(i, j).getWorker() != null) {
                        for (int k = 0; k < players.size(); k++) {
                            if (players.get(k).getNickname().equals(board.getCell(i, j).getWorker().getPlayer().getNickname())) {
                                System.out.print(colors.get(k) + board.getCell(i, j).getBuilding() + Colors.ANSI_RESET + " ");
                                break;
                            }
                        }
                    } else {
                        System.out.print(board.getCell(i, j).getBuilding() + " ");
                    }
                }
                System.out.println();
            }
            System.out.println("Y|---------");
            System.out.println("X 0 1 2 3 4\n");
        } else {
            System.out.println("No board to print (this message only for debug)");
        }
    }

    public void EndAll() {
        //todo end the game
        System.out.println("Game is ended.\nClosing the application");
        try {
            threadInput.interrupt();
            threadInput.join(300);
        } catch (InterruptedException e) {
            System.out.println(threadInput.getName());
        }
        end = true;
    }

}
