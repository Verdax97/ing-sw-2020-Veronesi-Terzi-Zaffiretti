package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SantoriniMatchController {

    @FXML private VBox playersInfo;
    @FXML private GridPane board;
    @FXML private AnchorPane firstPlayerPane;
    @FXML private AnchorPane secondPlayerPane;
    @FXML private AnchorPane thirdPlayerPane;
    @FXML private Button confirmButton;
    @FXML private Label whosTurn;
    @FXML private Label godMessageBox;
    @FXML private CheckBox powerGodUse;
    @FXML private Label messageBox;

    private ClientMain clientMain;
    private ClientInput clientInputGUI;
    private int[] reply = {-5, -5, -5, -5};

    private ArrayList<CellButton> cellButtonBoard = new ArrayList<>();
    private int nPlayers;

    private String myName;

    private ArrayList<String> players = new ArrayList<>();

    private int waitWorker;
    private boolean placeWorkersPhase = false;
    private ArrayList<Integer> startWorkerPos = new ArrayList<>();

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

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public void refreshPlayers(SimpleBoard simpleBoard){
        for (int i = 0; i < simpleBoard.players.size(); i++) {
            for (int j = 0; j < simpleBoard.players.size(); j++) {
                AnchorPane temp = (AnchorPane) playersInfo.getChildren().get(i);
                ((ImageView) temp.getChildren().get(1)).setImage(new Image("Images/godCards/" + simpleBoard.gods.get(i).getName() + ".png"));
                ((Label) temp.getChildren().get(2)).setText(simpleBoard.players.get(i));
                ((Label) temp.getChildren().get(3)).setText(simpleBoard.gods.get(i).getDescription());
            }
        }
        if (simpleBoard.players.size() == 2) {
            thirdPlayerPane.setVisible(false);
        }
    }

    public void enlightenPlayer(int val){
        for (int i= 0; i<nPlayers; i++){
            if (i == val){
                playersInfo.getChildren().get(i).setStyle("-fx-background-color: #6495ed");
            } else playersInfo.getChildren().get(i).setStyle("-fx-background-color: Black");
        }
    }

    public void initializeAll(SimpleBoard simpleBoard) {
        for (int i = 0; i < simpleBoard.players.size(); i++) {
            for (int j = 0; j < simpleBoard.players.size(); j++) {
                AnchorPane temp = (AnchorPane) playersInfo.getChildren().get(i);
                ((ImageView) temp.getChildren().get(1)).setImage(new Image("Images/godCards/" + simpleBoard.gods.get(i).getName() + ".png"));
                ((Label) temp.getChildren().get(2)).setText(simpleBoard.players.get(i));
                ((Label) temp.getChildren().get(3)).setText(simpleBoard.gods.get(i).getDescription());
            }
        }
        if (simpleBoard.players.size() == 2) {
            thirdPlayerPane.setVisible(false);
        }
        initializeBoard(simpleBoard);
    }

    private void initializeBoard(SimpleBoard simpleBoard) {
        int k = 0, z = 0;
        for (int j = 4; j >= 0; j--) {
            for (int i = 0; i < 5; i++) {
                CellButton cellButton = new CellButton(i , j);
                cellButton.setOnAction(e -> selectedCell(cellButton));
                board.add(cellButton, k, z);
                cellButtonBoard.add(cellButton);
                if (k==4){
                    k=0;
                    z++;
                } else k++;
            }
        }
        updateBoard(simpleBoard);
    }

    public void hideConfirmButton() {
        whosTurn.setText("Please wait your turn");
        godMessageBox.setText("");
        confirmButton.setVisible(false);
    }

    public void showConfirmButton() {
        whosTurn.setText("It is Your Turn");
        confirmButton.setVisible(true);
    }

    public void lightUpBoard(String msg){
        //should scroll array of buttons, if value corresponds, the button should light up
        int number = 0;
        for (int i = 0; i < cellButtonBoard.size(); i++){
            if (/*logic to check if cellButton is in the altMsg*/true){
                cellButtonBoard.get(i).setIdFromList(number);
                cellButtonBoard.get(i).lighten();
                number++;
            }
        }
    }

    public void placeWorkers(){
        messageBox.setText("Select two different cell where you want to put your workers");
        waitWorker = 2;
        placeWorkersPhase = true;
    }

    public void selectWorker(String msg) {
        messageBox.setText("Select worker you want to perform your turn");
        lightUpBoard(msg);
    }

    public void beforeMovePower(String msg) {
        godMessageBox.setText("You can perform an action before your move");
        powerGodUse.setVisible(true);
        lightUpBoard(msg);
    }

    public void moveAgain() {
        godMessageBox.setText("You can move again");
        powerGodUse.setVisible(true);
    }

    public void buildAgain() {
        godMessageBox.setText("You could build again");
        powerGodUse.setVisible(true);
    }

    public void powerGodUsed(){

    }

    public void move(String msg) {
        messageBox.setText("Select cell you want to move to");
        lightUpBoard(msg);
    }

    public void build(String msg, Boolean atlas) {
        messageBox.setText("Select cell you want to build on");
        lightUpBoard(msg);
    }

    public void sendReply() {
        clientInputGUI.Reply(reply[0], reply[1], reply[2], reply[3]);
        reply = new int[]{-5, -5, -5, -5};
    }

    public void confirmAction() {
        if (placeWorkersPhase){
            //0 -> x first worker, 1 -> y first worker
            //2 -> x second worker, 3 -> y second worker
            for (int i=0; i < startWorkerPos.size(); i++) {
                reply[i] = startWorkerPos.get(i);
            }
            startWorkerPos.clear();
            placeWorkersPhase = false;
        }
        sendReply();
        resetLighten();
    }

    private void selectedCell(CellButton cellButton) {
        resetLighten();
        if (cellButton.getIdFromList() == -1 && !placeWorkersPhase) {
            return;
        }
        if (placeWorkersPhase) {
            cellButton.lighten();
            if (waitWorker > 0) {
                //should save and show two different selected cells
                startWorkerPos.add(cellButton.x);
                startWorkerPos.add(cellButton.y);
                waitWorker--;
            }
        } else {
            cellButton.lighten();
            reply[0] = cellButton.getIdFromList();
        }
    }

    public void resetLighten(){
        for (int i = 0; i < cellButtonBoard.size(); i++){
            cellButtonBoard.get(i).turnOff();
        }
    }

    private void error(String header, String content) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }

    private void winner() {
        //throws winner windows
        //if button is pressed program exit
        //debug message
        Alert winAlert = new Alert(Alert.AlertType.INFORMATION);
        winAlert.setHeaderText("You Win");
        winAlert.setContentText("Really Really Congrats");
        winAlert.setTitle("Winner winner");
    }

    public void updateBoard(SimpleBoard simpleBoard) {
        if (simpleBoard == null){
            return;
        }
        if (simpleBoard.players.size() == 0){
            return;
        }
        int cell = 0;
        int activePlayers = simpleBoard.players.size();
        ArrayList<String> temp = (ArrayList<String>) players.clone();
        if (activePlayers != nPlayers) {
            if (myName.equals(simpleBoard.players.get(0)) && simpleBoard.players.size() == 1) {
                winner();
            } else refreshPlayers(simpleBoard);
            nPlayers = activePlayers;
        }
        if (simpleBoard.board == null){
            return;
        }
        for (int j = 4; j >= 0; j--) {
            for (int i = 0; i < 5; i++) {
                if (simpleBoard.board[i][j] == 4) {
                    cellButtonBoard.get(cell).setDome();
                } else //search for worker on that cell
                {
                    int index;
                    boolean found = false;
                    for (index = 0; index < simpleBoard.workers.size(); index++) {
                        if (simpleBoard.workers.get(index)[0] == i && simpleBoard.workers.get(index)[1] == j) {
                            int val = 0;
                            if (index >= 2 && index < 4)
                                val = 1;
                            else if (index >= 4 && index < 6)
                                val = 2;
                            cellButtonBoard.get(cell).refresh(simpleBoard.board[i][j], true);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        cellButtonBoard.get(cell).refresh(simpleBoard.board[i][j], false); //no worker and no dome
                    }
                }
                cell++;
            }
        }
    }
}

