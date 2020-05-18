package it.polimi.ingsw.GUI;

import it.polimi.ingsw.view.client.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LauncherController implements Initializable {

    private ClientMain clientMain;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;


    @FXML
    private void connection(ActionEvent event) {
        event.consume();
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
        //lobby creation
    }

    private void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        ip.setText("127.0.0.1");
        port.setText("4567");
    }
}
