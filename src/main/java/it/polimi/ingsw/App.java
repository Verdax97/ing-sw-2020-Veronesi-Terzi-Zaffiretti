package it.polimi.ingsw;

/**
 * Class App the starting class
 */
public class App {
    /**
     * Method main is the main method of the app class
     *
     * @param args of type String[]
     */
    public static void main(String[] args) {
        if (args.length == 0)
            new ClientStarter().startClient(true);
        else if (args.length == 1) {
            if (args[0].equals("-server"))
                new ServerMain().run();
            else if (args[0].equals("-cli"))
                new ClientStarter().startClient(false);
            else
                System.out.println("Not a valid input");
        } else
            System.out.println("Not a valid input");
    }
}
