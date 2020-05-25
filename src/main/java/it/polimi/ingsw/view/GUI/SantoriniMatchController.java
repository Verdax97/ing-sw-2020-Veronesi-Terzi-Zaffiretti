package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SantoriniMatchController {

    @FXML
    private Button confirmButton;

    private int[] reply;

    public ClientInput getClientInputGUI() {
        return clientInputGUI;
    }

    public void setClientInputGUI(ClientInput clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    public ClientMain getClientMain() {
        return clientMain;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    private ClientMain clientMain;
    private ClientInput clientInputGUI;

    public void setReplyValue(CellButton cellButton){

    }

    public void sendReply(){
        clientInputGUI.Reply(reply[0], reply[1], reply[2], reply[3]);
        reply = new int[] {-5, -5, -5, -5};
    }

    public void updateBoard(){

    }
}
