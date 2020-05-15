package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.MsgToServer;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain implements Runnable {
    Scanner stdin = new Scanner(System.in);

    private volatile boolean readyToReceive = false;
    private volatile boolean readyToSend = false;
    private MsgToServer replyMsg;
    private MsgPacket receivedMsg;
    private ClientInput clientInput;
    public LineClient lineClient;
    private final ArrayList<String> colors = new ArrayList<>();

    public Board board;

    private String nick = "temp";


    private boolean end = false;

    private ExecutorService executor;

    @Override
    public void run() {
        clientInput = new ClientInput(this);
        colors.add(Colors.ANSI_RED);
        colors.add(Colors.ANSI_GREEN);
        colors.add(Colors.ANSI_BLUE);
        while (true) {
            if (InitializeClient()) break;
        }
        while (!end) {
            end = CLIStuff();
        }
        System.out.println("Press enter to end the program");
        stdin.nextLine();
    }

    boolean InitializeClient() {
        System.out.println("Insert Server IP");
        String IP = stdin.nextLine();
        System.out.println("Insert Server port");

        int port;
        while (true) {
            try {
                port = stdin.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Insert a valid port");
                stdin = new Scanner(System.in);
            }
        }
        executor = Executors.newCachedThreadPool();
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
        executor.submit(client);
        return true;
    }

    private boolean CLIStuff() {
        //wait to have reply msg ready
        if (!isReadyToRecive()) {
            return false;
        }
        //read received message
        setReadyToReceive(false);

        //exit if the game ends
        if (getReceivedMsg().msg.equalsIgnoreCase("end")) {
            return true;
        }

        board = receivedMsg.board;
        printBoard(receivedMsg.board, receivedMsg.players);

        if (receivedMsg.nickname.equals(nick)) {
            clientInput.ParseMsg(receivedMsg);
        } else {
            System.out.println(receivedMsg.nickname + "'s turn, wait");
            setReplyMsg(new MsgToServer(getNick(), -5, -5, -5, -5));
            setReadyToSend(true);
        }
        return false;
    }

    public boolean isReadyToRecive() {
        return readyToReceive;
    }

    public void setReadyToReceive(boolean readyToReceive) {
        this.readyToReceive = readyToReceive;
    }

    public boolean isReadyToSend() {
        return readyToSend;
    }

    public void setReadyToSend(boolean readyToSend) {
        this.readyToSend = readyToSend;
    }

    public synchronized MsgToServer getReplyMsg() {
        return replyMsg;
    }

    public synchronized void setReplyMsg(MsgToServer replyMsg) {
        this.replyMsg = replyMsg;
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
        for (int i = 0; i < players.size(); i++) {
            System.out.print(colors.get(i) + players.get(i).getNickname() + " ");
            if (players.get(i).getGodPower() != null) {
                System.out.println(players.get(i).getGodPower().getName() + ": " + players.get(i).getGodPower().description + Colors.ANSI_RESET);
            }
            System.out.println(Colors.ANSI_RESET);
        }
        //now print the board
        if (board != null) {
            //TODO print the actual board
            System.out.println("Print the board (this message only for debug)");
        } else {
            System.out.println("No board to print (this message only for debug)");
        }
    }

    public void EndAll() {
        //todo end the game
        executor.shutdownNow();//todo fix
        System.out.println("quitting");
        end = true;
    }

}
