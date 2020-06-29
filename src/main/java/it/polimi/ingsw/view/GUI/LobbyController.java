package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * The type Lobby controller.
 */
public class LobbyController{

    /**
     * Sets client input gui.
     *
     * @param clientInputGUI the client input gui
     */
    public void setClientInputGUI(ClientInputGUI clientInputGUI) { this.clientInputGUI = clientInputGUI; }

    /**
     * Sets client main.
     *
     * @param clientMain the client main
     */
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

    /**
     * Show number players.
     */
    public void showNumberPlayers() {
        numberPlayer.setVisible(true);
        confirm.setVisible(true);
    }

    /**
     * Show nicknames.
     */
    public void showNicknames() {
        nicknameMessage.setVisible(true);
        nickname.setVisible(true);
        lobby.setVisible(true);
    }

    /**
     * Wait for start.
     */
    public void waitForStart() {
        nicknameMessage.setText("Wait for other players to connect");
        nickname.setVisible(false);
        lobby.setVisible(false);
    }

    /**
     * Sets number player.
     */
    public void setNumberPlayer() {
        int val;
        String number = numberPlayer.getValue().toString();
        if (number.equalsIgnoreCase("2 Players")) {
            val = 2;
            clientInputGUI.reply(val, -5, -5, -5);
            numberPlayer.setVisible(false);
            confirm.setVisible(false);
        }
        else if (number.equalsIgnoreCase("3 Players")) {
            val = 3;
            clientInputGUI.reply(val, -5, -5, -5);
            numberPlayer.setVisible(false);
            confirm.setVisible(false);
        }
        else {
            error("Number of players not selected", "Please select number of players");
        }
    }

    /**
     * Lobby boolean.
     *
     * @return the boolean
     */
    public boolean lobby() {
        if (nickname != null) {
            clientMain.setNick(nickname.getText());
            clientInputGUI.reply(-5, -5, -5, -5);
            return true;
        }
        else {
            error("Nickname not written", "Nickname must not be empty");
            return false;
        }
    }

    /**
     * Show resume.
     */
    public void showResume(){
        nicknameMessage.setVisible(false);
        resumeText.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);
    }

    /**
     * Resume.
     */
    @FXML
    public void resume(){
        clientInputGUI.reply(1, -5, -5, -5);
        yes.setVisible(false);
        no.setVisible(false);
    }

    /**
     * Dont resume.
     */
    @FXML
    public void dontResume(){
        clientInputGUI.reply(0, -5, -5, -5);
        yes.setVisible(false);
        no.setVisible(false);
    }


    /**
     * Error.
     *
     * @param header  the header
     * @param content the content
     */
    public void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
