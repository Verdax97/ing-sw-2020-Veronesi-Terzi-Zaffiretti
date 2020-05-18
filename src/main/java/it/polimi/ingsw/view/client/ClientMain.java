package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.Colors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientMain implements Runnable {
    Scanner stdin = new Scanner(System.in);

    private volatile boolean readyToReceive = false;
    private MsgPacket receivedMsg;
    private ClientInput clientInput;
    public final ArrayList<String> colors = new ArrayList<>();

    public boolean CLI = false;
    public SimpleBoard board;

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
            if (CLI)
                end = CLIStuff();
        }
        EndAll();
        System.exit(1);
    }

    public boolean InitializeClient(String IP, int port) {
        LineClient client = new LineClient(IP, port, this);
        if (CLI)
            clientInput = new ClientInputCLI(this);
        else
            clientInput = new ClientInputGUI(this);
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
        clientInput.printBoard(board);

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
