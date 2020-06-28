package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgToClient;
import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.Colors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class ClientMain manage all the client logic
 */
public class ClientMain implements Runnable {
    /**
     * The Stdin.
     */
    Scanner stdin = new Scanner(System.in);

    private MsgToClient receivedMsg;
    private ClientInput clientInput;
    /**
     * The Colors.
     */
    public final ArrayList<String> colors = new ArrayList<>();

    /**
     * The Cli.
     */
    public boolean CLI = false;
    /**
     * The Board.
     */
    public SimpleBoard board;

    private String nick = "temp";

    /**
     * The Thread input.
     */
    Thread threadInput;

    private boolean end = false;

    /**
     * Method getReceivedMsg returns the receivedMsg of this ClientMain object.
     *
     * @return the receivedMsg (type MsgPacket) of this ClientMain object.
     */
    public synchronized MsgToClient getReceivedMsg() {
        return receivedMsg;
    }

    /**
     * Method setReceivedMsg sets the receivedMsg of this ClientMain object.
     *
     * @param receivedMsg the receivedMsg of this ClientMain object.
     */
    public synchronized void setReceivedMsg(MsgToClient receivedMsg) {
        this.receivedMsg = receivedMsg;
    }

    /**
     * Method getNick returns the nick of this ClientMain object.
     *
     * @return the nick (type String) of this ClientMain object.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Method setNick sets the nick of this ClientMain object.
     *
     * @param nick the nick of this ClientMain object.
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Method setClientInput sets the clientInput of this ClientMain object.
     *
     * @param clientInput the clientInput of this ClientMain object.
     */
    public void setClientInput(ClientInput clientInput) {
        this.clientInput = clientInput;
    }

    /**
     * Method getClientInput returns the clientInput of this ClientMain object.
     *
     * @return the clientInput (type ClientInput) of this ClientMain object.
     */
    public ClientInput getClientInput() {
        return clientInput;
    }

    /**
     * Constructor ClientMain creates a new ClientMain instance.
     */
    public ClientMain() {
        this.clientInput = new ClientInput(this);
        colors.add(Colors.ANSI_RED);
        colors.add(Colors.ANSI_GREEN);
        colors.add(Colors.ANSI_BLUE);
    }

    /**
     * Method run wait for a message from the lineClient class and update the client view
     */
    @Override
    public void run() {
        while (!end) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
            end = clientLogic();
        }
        EndAll();
    }

    /**
     * Method InitializeClient ...
     *
     * @param IP   of type String
     * @param port of type int
     * @return boolean boolean
     */
    public boolean InitializeClient(String IP, int port) {
        LineClient client = new LineClient(IP, port, this);
        if (CLI)
            clientInput = new ClientInputCLI(this);
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

    /**
     * Method clientLogic ...
     *
     * @return boolean
     */
    private boolean clientLogic() {
        if (getReceivedMsg() == null)
            return true;

        board = receivedMsg.board;
        clientInput.printBoard(board);
        //clientInput.printBoard(board);

        //exit if the game ends
        if (getReceivedMsg().msg.equalsIgnoreCase(Messages.END)) {
            clientInput.updateEndGame();
            return true;
        }

        if (receivedMsg.nickname.equals(nick)) {
            //start a new thread for the input receiver
            Runnable runnable = () -> clientInput.ParseMsg(receivedMsg);
            threadInput = new Thread(runnable);
            threadInput.start();
        } else {
            //update the view for all other players
            clientInput.updateNotYourTurn(getReceivedMsg());
        }
        return false;
    }

    /**
     * Method EndAll close all thread
     */
    public synchronized void EndAll() {
        end = true;
        if (isEnding)
            return;
        isEnding = true;
        clientInput.closeGame();
        try {
            threadInput.interrupt();
            threadInput.join(300);
        } catch (InterruptedException e) {
            System.out.println(threadInput.getName());
        }
        //close all
    }

    /**
     * The Is ending.
     */
    public boolean isEnding = false;
}
