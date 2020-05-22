package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.*;

import java.util.*;

public class ClientInput extends Observable {

    protected final ClientMain clientMain;

    public ClientInput(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void ParseMsg(MsgPacket msgPacket) {
        int[] arr = {-5, -5, -5, -5};
        try {
            Thread.sleep(200);//dunno why but with this it works
            Reply(arr[0], arr[1], arr[2], arr[3]);
        } catch (InterruptedException e) {
            //close all
            clientMain.EndAll();
        }
    }

    public void Reply(int x, int y, int targetX, int targetY) {
        //clientMain.setReplyMsg(new MsgToServer(clientMain.getNick(), x,y,targetX, targetY));
        setChanged();
        notifyObservers(new MsgToServer(clientMain.getNick(), x, y, targetX, targetY));
    }

    public void printBoard(SimpleBoard board) {
    }
}
