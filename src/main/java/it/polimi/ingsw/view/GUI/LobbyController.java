package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LobbyController{

    public void setClientInputGUI(ClientInputGUI clientInputGUI) { this.clientInputGUI = clientInputGUI; }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    private ClientMain clientMain = null;
    private ClientInputGUI clientInputGUI = null;

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

    public void showNumberPlayers(){
        numberPlayer.isVisible();
        confirm.isVisible();
    }

    public void showNicknames(){
        nicknameMessage.isVisible();
        nickname.isVisible();
        lobby.isVisible();
    }

    @FXML
    public void setNumberPlayer(){
        int val;
        String number = numberPlayer.getValue().toString();
        if (number.equalsIgnoreCase("2 Players")) {
            val = 2;
            clientInputGUI.Reply(val, -5, -5, -5);
            numberPlayer.setVisible(false);
            confirm.setVisible(false);
            return;
        }
        else if (number.equalsIgnoreCase("3 Players")) {
            val = 3;
            clientInputGUI.Reply(val, -5, -5, -5);
            numberPlayer.setVisible(false);
            confirm.setVisible(false);
            return;
        }
        else {
            error("Number of players not selected", "Please select number of players");
            return;
        }
    }

    @FXML
    public boolean lobby() {
        if (nickname != null) {
            clientMain.setNick(nickname.getText());
            clientInputGUI.Reply(-5,-5, -5, -5);
            return true;
        }
        else {
            error("Nickname not written", "Nickname must not be empty");
            return false;
        }
    }

    private void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
