package it.polimi.ingsw.view;

import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.Player;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientInput extends Thread
{
    private final ClientMain clientMain;
    private final Scanner scanner;
    private final PrintWriter printWriter;
    private boolean CLI;
    private String previousMsg;

    public ClientInput(ClientMain clientMain, boolean CLI)
    {
        this.clientMain = clientMain;
        this.scanner = new Scanner(System.in);
        this.printWriter = new PrintWriter(System.out);
        this.CLI = CLI;
    }

    public void run()
    {

    }

    public void ParseMsg(MsgPacket msgPacket)
    {
        String msg = msgPacket.msg;
        //String alt = msgPacket.altMsg;
        //wait for input only in particular cases
        StringBuilder answ = new StringBuilder();
        String toPrint = "";

        if (msg.split(" ")[0].equalsIgnoreCase("Error"))
        {
            //TODO scrivere in rosso l'errore
            System.out.println(Colors.ANSI_RED + msg.split("\n", 2)[0] + Colors.ANSI_RESET);
            msg = msg.split("\n", 2)[1];
        }

        if (msg.equalsIgnoreCase("Place your worker:"))
        {
            boolean check = false;
            //TODO print board
            for (int i = 0; i < 2; i++)
            {
                int x = 0, y = 0;
                while (!check)
                {
                    System.out.println("Place worker " + i + ":");
                    boolean valid = false;
                    while (!valid)
                    {
                        System.out.print("Insert x coordinate (0/4): ");
                        x = scanner.nextInt();
                        if (x < 5 && x >= 0)
                        {
                            System.out.println("Are you sure? (y/n)");
                            if (scanner.nextLine().equalsIgnoreCase("y"))
                                valid = true;
                        } else {
                            System.out.println("coordinate x not valid");
                        }
                    }
                    valid = false;
                    while (!valid)
                    {
                        System.out.print("Insert y coordinate (0/4): ");
                        y = scanner.nextInt();
                        if (y < 5 && y >= 0) {
                            System.out.println("Are you sure? (y/n)");
                            if (scanner.nextLine().equalsIgnoreCase("y"))
                                valid = true;
                        } else {
                            System.out.println("coordinate x not valid");
                        }
                    }
                    if (msgPacket.board.getCell(x,y).getWorker() == null)
                        check = true;
                    else
                    {
                        System.out.println("Cannot place worker here, is already occupied, try another cell");
                    }
                }
                answ.append(x).append(",").append(y).append(" ");
            }
            Reply(answ.toString());
        }

        if (msg.equalsIgnoreCase("StartTurn"))
        {
            int power = 0;
            int x = 0,y = 0,targx = 0,targy = 0;
            for (Player player:msgPacket.players)
            {
                if (player.getNickname().equals(clientMain.getNick()))
                {
                    String name = player.getGodPower().getName();
                    if (name.equalsIgnoreCase("Prometheus") ||
                            name.equalsIgnoreCase("Charon"))
                    {
                        System.out.println("Do you want to use your god power?(y/n)");
                        String re = scanner.nextLine();
                        if (re.equalsIgnoreCase("y"))
                            power = 1;
                    }

                }
            }
            boolean check = false;
            while (!check)
            {
                System.out.println("Select the cell with your worker on it that you want to use");
                System.out.print("X coordinate between 0 and 4: ");
                x = scanner.nextInt();
                System.out.print("Y coordinate between 0 and 4: ");
                y = scanner.nextInt();
                if (x >=0 && x < 5 && y >=0 && y < 5)
                {
                    System.out.println("You selected cell " + x + " " + y);
                    System.out.println("Are you sure?(y/n)");
                    String re = scanner.nextLine();
                    if (re.equalsIgnoreCase("y"))
                        check = true;
                }
                else {
                    System.out.println("Selected cell is not valid");
                }
            }
            check = false;
            if (power == 1)//only if you have a god with startTurn power
            {
                while (!check)
                {
                    System.out.println("Select the target cell");
                    System.out.print("X coordinate between 0 and 4: ");
                    targx = scanner.nextInt();
                    System.out.print("Y coordinate between 0 and 4: ");
                    targy = scanner.nextInt();
                    if (targx >= 0 && targx < 5 && targy >= 0 && targy < 5) {
                        System.out.println("You selected cell " + targx + " " + targy);
                        System.out.println("Are you sure?(y/n)");
                        String re = scanner.nextLine();
                        if (re.equalsIgnoreCase("y"))
                            check = true;
                    } else {
                        System.out.println("Selected cell is not valid");
                    }
                }
            }
            Reply( x + " " + y + " " + targx + " " + targy + " " + power);
        }

        if (msg.equalsIgnoreCase("Move") ||
                msg.equalsIgnoreCase("Move Again"))
        {
            int power = 0;
            int x = 0,y = 0;
            if (msg.equalsIgnoreCase("Move Again"))
            {
                System.out.println("You the possibility to make another move tanks to your " +
                        "god power.");
                System.out.println("Do you want to use your god power?(y/n)");
                String re = scanner.nextLine();
                if (re.equalsIgnoreCase("y"))
                    power = 1;
            }
            if (msg.equalsIgnoreCase("Move") || power == 1)
            {
                boolean check = false;
                while (!check)
                {
                    System.out.println("Select the cell to move the worker:");
                    System.out.print("X coordinate between 0 and 4: ");
                    x = scanner.nextInt();
                    System.out.print("Y coordinate between 0 and 4: ");
                    y = scanner.nextInt();
                    if (x >=0 && x < 5 && y >=0 && y < 5)
                    {
                        System.out.println("You selected cell " + x + " " + y);
                        System.out.println("Are you sure?(y/n)");
                        String re = scanner.nextLine();
                        if (re.equalsIgnoreCase("y"))
                            check = true;
                    }
                    else {
                        System.out.println("Selected cell is not valid");
                    }
                }
            }
            Reply( x + " " + y + " " + power);
        }

        if (msg.equalsIgnoreCase("Insert Nickname")
                ||
                msg.equalsIgnoreCase("Error nickname not valid," +
                        " try another nickname"))
        {
            printWriter.println(msg+"\n>");
            answ = new StringBuilder(scanner.nextLine());

            clientMain.setNick(answ.toString());
        }
    }

    public void Reply(String msg)
    {
        clientMain.setReplyMsg(new MsgPacket(clientMain.getNick(), msg, "", null, null));
        clientMain.setReadyToSend(true);
        System.out.println("now we wait for the server (debug only)");
    }
}
