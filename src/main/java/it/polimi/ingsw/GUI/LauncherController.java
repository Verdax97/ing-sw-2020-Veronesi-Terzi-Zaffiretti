package it.polimi.ingsw.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    @FXML
    private void connection(ActionEvent event) {
        event.consume();
        System.out.println("Debug message, Button Fired");
        //TODO
        //connection should check if IP and PORT can establish connection
        //if not pop up window of error and ask of try again
        //if succeed change in nickname and number of player window
    }

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        ip.setText("");
        port.setText("");
    }
}
