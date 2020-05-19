package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class LobbyController extends StackPane {

    @FXML
    private TextField nickname;

    @FXML
    private Button lobbyButton;

    @FXML
    private Button lobbyJoin;

    @FXML
    private ChoiceBox numberPlayer;

    LobbyController(boolean owner) {
        FXMLLoader fxmlLoader;
        if(owner){
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
        }
        else {
            fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/LobbyOthers.fxml"));
        }
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            //boh
        }
    }

    @FXML
    private void createLobby(){
        //send nickname, number of players to server
    }

    @FXML
    private void joinLobby(){
        //send nickname to server
    }
}
