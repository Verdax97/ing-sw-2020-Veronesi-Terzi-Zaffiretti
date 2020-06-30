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
 * Class LobbyController is the window which shows the lobby creation to users.
 */
public class LobbyController{

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
     * Method setClientInputGUI sets the clientInputGUI of this SantoriniMatchController object.
     *
     * @param clientInputGUI of type ClientInputGUI
     */
    public void setClientInputGUI(ClientInputGUI clientInputGUI) { this.clientInputGUI = clientInputGUI; }

    /**
     * Method setClientMain sets the clientMain of this SantoriniMatchController object.
     *
     * @param clientMain of type ClientMain.
     */
    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    /**
     * Method showNumberPlayers asks the user to select number of players.
     */
    public void showNumberPlayers() {
        numberPlayer.setVisible(true);
        confirm.setVisible(true);
    }

    /**
     * Method showNicknames asks the user to set own nickname.
     */
    public void showNicknames() {
        numberPlayer.setVisible(false);
        confirm.setVisible(false);
        nicknameMessage.setVisible(true);
        nickname.setVisible(true);
        lobby.setVisible(true);
    }

    /**
     * Method waitForStar asks the user to wait.
     */
    public void waitForStart() {
        nicknameMessage.setText("Wait for other players to connect");
        nickname.setVisible(false);
        lobby.setVisible(false);
    }

    /**
     * Method setNumberPlayer asks the user to set the number of players.
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
     * Method setNickname sends the nickname to the server if is not empty.
     *
     * @return boolean value
     */
    public boolean setNickname() {
        if (nickname != null) {
            clientMain.setNick(nickname.getText());
            clientInputGUI.reply(-5, -5, -5, -5);
            return true;
        } else {
            error("Nickname not written", "Nickname must not be empty");
            return false;
        }
    }

    /**
     * Method showResume shows options to user.
     */
    public void showResume(){
        nicknameMessage.setVisible(false);
        resumeText.setVisible(true);
        yes.setVisible(true);
        no.setVisible(true);
    }

    /**
     * Method resume the game sends message to the Server asking to resume.
     */
    @FXML
    public void resume(){
        clientInputGUI.reply(1, -5, -5, -5);
        yes.setVisible(false);
        no.setVisible(false);
    }

    /**
     * Method dontResume the game sends message to the Server asking not to resume.
     */
    @FXML
    public void dontResume(){
        clientInputGUI.reply(0, -5, -5, -5);
        yes.setVisible(false);
        no.setVisible(false);
    }


    /**
     * Method error shows an error popup.
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
