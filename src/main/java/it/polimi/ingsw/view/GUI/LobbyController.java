package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class LobbyController{

    public void setClientInputGUI(ClientInputGUI clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    private ClientInputGUI clientInputGUI;

    @FXML
    private TextField nickname;

    @FXML
    private Button lobbyButton;

    @FXML
    private ChoiceBox numberPlayer;

    @FXML
    public boolean lobby() {
        if (true/*capo lobby*/) {
            int val;
            String number = numberPlayer.getValue().toString();
            if (nickname != null && number.equalsIgnoreCase("2 Players")) {
                val = 2;
                //send nickname?
                //clientInputGUI.Reply(val, -5, -5, -5);
                return true;
            }
            else if (nickname != null && number.equalsIgnoreCase("2 Players")) {
                val = 3;
                //send nickname??
                //clientInputGUI.Reply(val, -5, -5, -5);
                return true;
            }
            else if (nickname != null && number.equalsIgnoreCase("Select number:")) {
                error("Number of player not selected", "Please select number of players");
                return false;
            }
            else if (nickname == null) {
                error("Nickname not written", "Nickname must not be empty");
                return false;
            } else return false;
        }
        else{
            return true;
            //la visibilità dei bottoni dovrebbe essere settata prima
        }
    }

    private void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
