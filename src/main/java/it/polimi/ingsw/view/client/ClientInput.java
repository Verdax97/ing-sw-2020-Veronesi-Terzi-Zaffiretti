package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.MsgToClient;
import it.polimi.ingsw.model.MsgToServer;
import it.polimi.ingsw.model.SimpleBoard;

import java.util.Observable;

/**
 * Class ClientInput base class that is needed to be extended to work for cli and gui
 */
public class ClientInput extends Observable {

    /**
     * The Client main.
     */
    protected final ClientMain clientMain;

    /**
     * Constructor ClientInput creates a new ClientInput instance.
     *
     * @param clientMain of type ClientMain
     */
    public ClientInput(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    /**
     * Method ParseMsg parse the incoming message and call the appropriated function
     *
     * @param msgToClient of type MsgPacket
     */
    public void parseMsg(MsgToClient msgToClient) {
    }

    /**
     * Method Reply notify the lineClient to send the message to the server
     *
     * @param x       of type int
     * @param y       of type int
     * @param targetX of type int
     * @param targetY of type int
     */
    public void reply(int x, int y, int targetX, int targetY) {
        if (clientMain.isEnding)
            return;
        setChanged();
        notifyObservers(new MsgToServer(clientMain.getNick(), x, y, targetX, targetY));
    }

    /**
     * Method printBoard prints the board on screen
     *
     * @param board of type SimpleBoard
     */
    public void printBoard(SimpleBoard board) {
    }

    /**
     * Method updateNotYourTurn show that is not your turn
     *
     * @param msgToClient the msg to client
     */
    public void updateNotYourTurn(MsgToClient msgToClient) {

    }

    /**
     * Method updateEndGame show the ending message and the winner
     */
    public void updateEndGame() {
    }

    /**
     * Method closeGame notify the player that the game is closing
     */
    public void closeGame() {
    }
}
