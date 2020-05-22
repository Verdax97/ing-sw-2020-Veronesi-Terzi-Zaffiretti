package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.view.client.ClientInputGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LobbyController{

    public void setClientInputGUI(ClientInputGUI clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    private ClientInputGUI clientInputGUI;

    @FXML
    private Text nicknameMessage;

    @FXML
    private TextField nickname;

    @FXML
    private Button lobby;

    @FXML
    private Button confirm;

    @FXML
    private ChoiceBox numberPlayer;

    @FXML
    public boolean lobby() {
        if (nickname != null) {
            //send nickname
            return true;
        }
        else {
            error("Nickname not written", "Nickname must not be empty");
            return false;
        }
    }

    public void lobbyMaster(MsgPacket msgPacket){
        String msg = msgPacket.msg;
        if(msg.equalsIgnoreCase(Messages.lobby)){
            numberPlayer.isVisible();
            confirm.isVisible();
        }
        else{
            nicknameMessage.isVisible();
            nickname.isVisible();
            lobby.isVisible();
        }
    }

    @FXML
    public void setNumberPlayer(){
        int val;
        String number = numberPlayer.getValue().toString();
        if (number.equalsIgnoreCase("2 Players")) {
            val = 2;
            //clientInputGUI.Reply(val, -5, -5, -5);
            nicknameMessage.isVisible();
            nickname.isVisible();
            lobby.isVisible();
            return;
        }
        else if (number.equalsIgnoreCase("3 Players")) {
            val = 3;
            //clientInputGUI.Reply(val, -5, -5, -5);
            nicknameMessage.isVisible();
            nickname.isVisible();
            lobby.isVisible();
            return;
        }
        else {
            error("put text in here", "put text in here");
            return;
        }
    }

    private void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
