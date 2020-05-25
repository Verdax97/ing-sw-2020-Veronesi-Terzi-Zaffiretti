package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.Colors;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class ClientMain manage all the client logic
 */
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

    public ClientInput getClientInput(){
        return clientInput;
    }

    @Override
    public void run() {
        colors.add(Colors.ANSI_RED);
        colors.add(Colors.ANSI_GREEN);
        colors.add(Colors.ANSI_BLUE);
        while (!end) {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            end = clientLogic();
        }
        EndAll();
    }

    public void setClientInput(ClientInput clientInput) {
        this.clientInput = clientInput;
    }

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
        //wait to have reply msg ready
        if (!isReadyToRecive()) {
            return false;
        }
        //read received message
        setReadyToReceive(false);
        if (getReceivedMsg() == null)
            return true;

        board = receivedMsg.board;
        clientInput.printBoard(board);

        //exit if the game ends
        if (getReceivedMsg().msg.equalsIgnoreCase(Messages.End)) {
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
            clientInput.updateNotYourTurn();
        }
        return false;
    }

    /**
     * Method isReadyToRecive returns the readyToRecive of this ClientMain object.
     *
     * @return the readyToRecive (type boolean) of this ClientMain object.
     */
    public boolean isReadyToRecive() {
        return readyToReceive;
    }

    /**
     * Method setReadyToReceive sets the readyToReceive of this ClientMain object.
     *
     * @param readyToReceive the readyToReceive of this ClientMain object.
     */
    public void setReadyToReceive(boolean readyToReceive) {
        this.readyToReceive = readyToReceive;
    }

    /**
     * Method getReceivedMsg returns the receivedMsg of this ClientMain object.
     *
     * @return the receivedMsg (type MsgPacket) of this ClientMain object.
     */
    public synchronized MsgPacket getReceivedMsg() {
        return receivedMsg;
    }

    /**
     * Method setReceivedMsg sets the receivedMsg of this ClientMain object.
     *
     * @param receivedMsg the receivedMsg of this ClientMain object.
     */
    public synchronized void setReceivedMsg(MsgPacket receivedMsg) {
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
     * Method EndAll close all thread
     */
    public void EndAll() {
        clientInput.closeGame();
        try {
            threadInput.interrupt();
            threadInput.join(300);
        } catch (InterruptedException e) {
            System.out.println(threadInput.getName());
        }
        //close all
        System.exit(1);
        end = true;
    }

}
