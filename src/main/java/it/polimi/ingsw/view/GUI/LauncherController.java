package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LauncherController {

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    private ClientMain clientMain = null;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    @FXML
    public boolean connection() {
        //controller should create a GuiClient since this is the first gui process running
        String ipTry = ip.getText();
        String portTry = port.getText();
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(portTry);
        if (!matcher.matches()) {
            error("Input is wrong", "Please insert a valid Port Number");
            return false;
        } else {
            int portNumber = Integer.parseInt(portTry);
            if (!clientMain.InitializeClient(ipTry, portNumber)) {
                error("Connection Failed", "Not able to reach the Server");
                return false;
            }
        }
        Thread main = new Thread(clientMain);
        main.start();
        return true;
    }

    private void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
