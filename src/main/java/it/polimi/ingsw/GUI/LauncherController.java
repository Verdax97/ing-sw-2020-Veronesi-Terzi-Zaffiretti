package it.polimi.ingsw.GUI;

import it.polimi.ingsw.view.client.ClientMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
            //eventualmente si può fare un altro fxml che viene loadato dal controller per schermata di errore
            //anche se a sua volta non avrebbe behavior
            Button errorButton = new Button("Ok");
            Label errorLabel = new Label("Error: invalid port");
            BorderPane borderPaneError = new BorderPane();
            borderPaneError.setTop(errorLabel);
            borderPaneError.setCenter(errorButton);
            Scene error = new Scene(borderPaneError, 150,150);
            Stage errorStage = new Stage();
            errorStage.setScene(error);
            errorStage.show();
        }
        int portNumber = Integer.parseInt(portTry);
        boolean success;
        //0 non fa niente e caccia errore
        //1 controlla il nickname, mantenendo aperta la connessione
    }

    public void setClientMain(ClientMain clientMain){
        this.clientMain = clientMain;
    }

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        ip.setText("");
        port.setText("");
        nickname.setText("");
        resume.setSelected(false);
    }
}
