package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class LobbyController {

    public void setClientInputGUI(ClientInputGUI clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    private ClientInputGUI clientInputGUI;
    @FXML
    private TextField nickname;

    @FXML
    private Button lobbyButton;

    @FXML
    private Button lobbyJoin;

    @FXML
    private ChoiceBox numberPlayer;

    @FXML
    private void createLobby() {
        //send nickname, number of players to server
    }

    @FXML
    private void joinLobby() {
        //send nickname to server
    }

    private void setNumberPlayer(int val) {
        clientInputGUI.Reply(val, -5, -5, -5);
    }
}
