package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;

public class MatchController {

    public void setClientInputGUI(ClientInputGUI clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    ClientMain clientMain = null;
    ClientInputGUI clientInputGUI = null;


}
