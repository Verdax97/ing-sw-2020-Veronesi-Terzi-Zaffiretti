package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.State;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain
{
    Scanner stdin = new Scanner(System.in);

    private boolean readyToReceive = false;
    private boolean readyToSend = false;
    private MsgPacket replyMsg;
    private MsgPacket receivedMsg;
    public State state;
    public String precPlayer;
    private ClientInput clientInput;
    private ArrayList<StringBuilder> players= new ArrayList<>();
    private ArrayList<String> colors = new ArrayList<>();

    public Board board;

    private String nick;
    //usefull to controll if need to send msg on socket
    private String playerTurn;

    public void main(String[] args)
    {
        System.out.println("Insert Server IP");
        String IP = stdin.nextLine();
        System.out.println("Insert Server port");
        colors.add(Colors.ANSI_RED);
        colors.add(Colors.ANSI_GREEN);
        colors.add(Colors.ANSI_BLUE);
        int port = stdin.nextInt();
        LineClient client = new LineClient(IP, port, this);
        try
        {
            InitializeClient();
            boolean end = false;
            while (!end)
            {
                end = CLIStuff();
            }
        }catch (IOException e)
        {
            System.err.println("No Server found, check inserted IP and port");
        }

        System.out.println("Press enter to end the program");
        stdin.nextLine();
    }

    void InitializeClient() throws IOException
    {
        System.out.println("Insert Server IP");
        String IP = stdin.nextLine();
        System.out.println("Insert Server port");
        int port = stdin.nextInt();
        ExecutorService executor = Executors.newCachedThreadPool();
        LineClient client = new LineClient(IP, port, this);

        try {
            client.startClient();
        } catch (IOException e) {
            System.err.println("Server not reachable"); //server not available
            return;
        }
        //connection established message
        System.out.println("Connection established");

        //create
        executor.submit(client);
    }

    private boolean CLIStuff()
    {
        //wait to have reply msg ready
        if (!isReadyToRecive()) { return false; }
        //read received message
        setReadyToReceive(false);

        //exit if the game ends
        if(getReceivedMsg().msg.equalsIgnoreCase("end")) { return true; }

        if(getReceivedMsg().msg.equalsIgnoreCase("Ping"))
        {
            clientInput.Reply("Ping");
            return false;
        }
        printBoard(receivedMsg);

        if (receivedMsg.nickname.equals(nick))
        {
            clientInput.ParseMsg(receivedMsg);
        }
        else
        {
            setReplyMsg(new MsgPacket(getNick(), "ok", "", null, null));
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

    public synchronized MsgPacket getReplyMsg() {
        return replyMsg;
    }

    public synchronized void setReplyMsg(MsgPacket replyMsg) {
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

    public String getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(String playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void printBoard(MsgPacket msgPacket)
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < msgPacket.players.size(); i++)
        {
            System.out.print(colors.get(i) + msgPacket.players.get(i).getNickname() + " ");
            if (msgPacket.players.get(i).getGodPower() != null)
            {
                System.out.println(msgPacket.players.get(i).getGodPower().getName() + ": " +msgPacket.players.get(i).getGodPower().description + Colors.ANSI_RESET);
            }
        }
        //now print the board
        if (msgPacket.board != null)
        {
            //TODO print the actual board
        }
    }

}
