package it.polimi.ingsw.view.client;


import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;

public class ClientInputGUI extends ClientInput {

    public ClientInputGUI(ClientMain clientMain) {
        super(clientMain);
    }

    @Override
    public void ParseMsg(MsgPacket msgPacket) {
        String msg = msgPacket.msg;
        int[] arr = {-5, -5, -5, -5};

        if (msg.equalsIgnoreCase(Messages.lobby)) {
            //lobbyController.lobby();
        }

        if (msg.equalsIgnoreCase(Messages.nickname)) {
            //lobbyController.lobbyOthers();
        }

    }

    @Override
    public void Reply(int x, int y, int targetX, int targetY) {

        super.Reply(x, y, targetX, targetY);
    }
}
