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

    @FXML private Text nicknameMessage;

    @FXML private TextField nickname;

    @FXML private Button lobby;

    @FXML private Button confirm;

    @FXML private ChoiceBox numberPlayer;

    @FXML private Text resumeText;
    @FXML private Button yes;
    @FXML private Button no;

    public void showNumberPlayers() {
        numberPlayer.setVisible(true);
        confirm.setVisible(true);
    }

    public void showNicknames() {
        nicknameMessage.setVisible(true);
        nickname.setVisible(true);
        lobby.setVisible(true);
    }

    public void waitForStart() {
        nicknameMessage.setText("Wait for other players to connect");
        nickname.setVisible(false);
        lobby.setVisible(false);
    }

    public void setNumberPlayer() {
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
            clientInputGUI.setMyName(nickname.getText());
            return true;
        }
        else {
            error("Nickname not written", "Nickname must not be empty");
            return false;
        }
    }

    public void showResume(){
        resumeText.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);
    }

    @FXML
    public void resume(){
        clientInputGUI.Reply(1, -5, -5, -5);
        yes.setVisible(false);
        no.setVisible(false);
    }

    @FXML
    public void dontResume(){
        clientInputGUI.Reply(0, -5, -5, -5);
        yes.setVisible(false);
        no.setVisible(false);
    }



    public void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
