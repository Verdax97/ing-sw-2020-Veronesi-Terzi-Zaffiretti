package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SantoriniMatchController {

    @FXML private Button confirmButton;
    @FXML private TextField messageBox;

    private ClientMain clientMain;
    private ClientInput clientInputGUI;
    private int[] reply;

    private boolean waitWorker = false;

    public ClientInput getClientInputGUI() {
        return clientInputGUI;
    }

    public void setClientInputGUI(ClientInput clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    public ClientMain getClientMain() {
        return clientMain;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void setReplyValue(CellButton cellButton){

    }

    public void waitWorker(){
        messageBox.setText("Select the worker with which you want to play");
        waitWorker = true;
    }

    public void sendReply(){
        clientInputGUI.Reply(reply[0], reply[1], reply[2], reply[3]);
        reply = new int[] {-5, -5, -5, -5};
    }

    //put info into reply
    public void confirmAction(){
        if (waitWorker == true /*&& cellButton should be an array of 5 5 of button with particular info*/){
            //contiene worker preparo il messaggio da inviare al server
        }
        if (waitWorker == true /*&& cellButton should be an array of 5 5 of button with particular info*/){
            //non contiene worker, lancio error
            error("No worker on this cell", "Please select a cell with a worker on it");
        }
    }

    private void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }

    public void updateBoard(){

    }
}
