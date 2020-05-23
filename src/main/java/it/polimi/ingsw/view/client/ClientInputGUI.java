package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.view.GUI.LobbyController;

public class ClientInputGUI extends ClientInput {

    LobbyController lobbyController;

    public ClientInputGUI(ClientMain clientMain) {
        super(clientMain);
    }

    @Override
    public void ParseMsg(MsgPacket msgPacket) {
        String msg = msgPacket.msg;

        if (msg.split(" ")[0].equalsIgnoreCase(Messages.error)) {
            //(Colors.ANSI_RED + msg.split("\n", 2)[0] + Colors.ANSI_RESET);
            //msg = msg.split("\n", 2)[1];
        }

        if (msg.equalsIgnoreCase(Messages.lobby)) {
            lobbyController.showNumberPlayers();
        }

        if (msg.equalsIgnoreCase(Messages.nickname)) {
            lobbyController.showNicknames();
        }

    }
}
