package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class LauncherController is the window users use to connect to the server
 */
public class LauncherController {

    private ClientMain clientMain = null;
    @FXML private TextField ip;
    @FXML private TextField port;

    /**
     * Method setClientMain sets the clientMain of this SantoriniMatchController object.
     *
     * @param clientMain of type ClientMain.
     */
    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    /**
     * Method connection tries to establish a connection with Server using ip and port values inserted.
     */
    @FXML
    public void connection() {
        String ipTry = ip.getText();
        String portTry = port.getText();
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(portTry);
        if (!matcher.matches()) {
            error("Input is wrong", "Please insert a valid Port Number");
        } else {
            int portNumber = Integer.parseInt(portTry);
            if (!clientMain.initializeClient(ipTry, portNumber)) {
                error("Connection Failed", "Not able to reach the Server");
            }
        }
        Thread main = new Thread(clientMain);
        main.start();
    }

    /**
     * Method error popups an error with given header and message
     *
     * @param header  of type String
     * @param content of type String
     */
    private void error(String header, String content) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
