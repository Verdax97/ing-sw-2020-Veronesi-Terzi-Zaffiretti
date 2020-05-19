package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LauncherController{

    private ClientMain clientMain;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    @FXML
    private void connection() {
        //controller should create a GuiClient since this is the first gui process running
        clientMain = new ClientMain();
        String ipTry = ip.getText();
        String portTry = port.getText();
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(portTry);
        if(!matcher.matches()){
            error("Input is wrong", "Please insert a valid Port Number");
            return;
        }
        else {
            int portNumber = Integer.parseInt(portTry);
            if (!clientMain.InitializeClient(ipTry, portNumber)) {
                error("Connection Failed", "Not able to reach the Server");
                return;
            }
        }
        //clientMain.run();
    }

    private void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
