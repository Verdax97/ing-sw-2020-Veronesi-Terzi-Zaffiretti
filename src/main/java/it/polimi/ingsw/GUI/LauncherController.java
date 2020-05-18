package it.polimi.ingsw.GUI;

import it.polimi.ingsw.view.client.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LauncherController implements Initializable {

    private ClientMain clientMain;

    @FXML
    private TextField nickname;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    @FXML
    private CheckBox resume;

    @FXML
    private void connection(ActionEvent event) {
        event.consume();
        clientMain = new ClientMain();
        //debug
        System.out.println("Debug message, Button Fired");
        if (resume.isSelected()){
            System.out.println("Checkbox selected");
        }
        //end debug
        String nicknameTry = nickname.getText();
        String ipTry = ip.getText();
        String portTry = port.getText();
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher matcher = pattern.matcher(portTry);
        if(!matcher.matches()){
            error();
            return;
        }
        else {
            int portNumber = Integer.parseInt(portTry);
            if (!clientMain.InitializeClient(ipTry, portNumber)) {
                errorFail();
                return;
            }
        }
        clientMain.run();
        //lobby creation
    }

    private void error(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText("Insert a valid Port number");
        errorAlert.showAndWait();
    }

    private void errorFail(){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Connection failed");
        errorAlert.setContentText("Not able to connect with the current server");
        errorAlert.showAndWait();
    }

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        ip.setText("");
        port.setText("");
        nickname.setText("");
        resume.setSelected(false);
    }
}
