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

import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SantoriniMatchController {

    @FXML private VBox playersInfo;
    @FXML private GridPane board;
    @FXML private AnchorPane firstPlayerPane;
    @FXML private AnchorPane secondPlayerPane;
    @FXML private AnchorPane thirdPlayerPane;
    @FXML private Button confirmButton;
    @FXML private Text whosTurn;
    @FXML private Text godMessageBox;
    @FXML private CheckBox powerGodUse;
    @FXML private Text messageBox;

    private ClientMain clientMain;
    private ClientInput clientInputGUI;
    private int[] reply = {-5, -5, -5, -5};

    private ArrayList<CellButton> cellButtonBoard = new ArrayList<>();
    private int nPlayers;
    private String myName;
    private ArrayList<String> players = new ArrayList<>();

    private boolean turn = false;
    private int waitWorker = 0;
    private boolean placeWorkersPhase = false;
    private ArrayList<Integer> startWorkerPos = new ArrayList<>();
    private boolean powerGodAnswer = false;

    public ClientMain getClientMain() {
        return clientMain;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void setClientInputGUI(ClientInput clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    public void setMyName(String myName) {
        this.myName = myName;
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
                //grid pane works opposite than matrix
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
        turn = false;
    }

    public void showConfirmButton() {
        whosTurn.setText("It is Your Turn");
        godMessageBox.setText("");
        confirmButton.setVisible(true);
        turn = true;
    }

    public void lightUpBoard(String msg){
        //should parse message of possibilities
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(msg.split("\n")));
        for (int p = 0; p < temp.size(); p++){
            //still parsing that message
            ArrayList<String> s = new ArrayList<>(Arrays.asList(temp.get(p).split(" ")));
            String index = s.get(0).split(Pattern.quote(")"))[0];
            String almostX = s.get(1).split(Pattern.quote(","))[0];
            String x = almostX.replaceAll(Pattern.quote("("), "");
            String y = s.get(2).split(Pattern.quote(")"))[0];
            //should scroll array of buttons, if value corresponds, the button should light up
            for (int i = 0; i < cellButtonBoard.size(); i++){
                if (cellButtonBoard.get(i).x == Integer.parseInt(x) && cellButtonBoard.get(i).y == Integer.parseInt(y)){
                    cellButtonBoard.get(i).setIdFromList(Integer.parseInt(index));
                    cellButtonBoard.get(i).lighten(true);
                }
            }
        }
    }

    public void placeWorkers(){
        messageBox.setText("Select two different cell\nwhere you want to put your workers");
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
        powerGodAnswer = true;
        lightUpBoard(msg);
    }

    public void moveAgain(String msg) {
        godMessageBox.setText("You can move again");
        powerGodUse.setVisible(true);
        powerGodAnswer = true;
        lightUpBoard(msg);
    }

    public void buildAgain(String msg) {
        godMessageBox.setText("You could build again");
        powerGodUse.setVisible(true);
        powerGodAnswer = true;
        lightUpBoard(msg);
    }

    public void move(String msg) {
        messageBox.setText("Select cell you want to move to");
        lightUpBoard(msg);
    }

    public void build(String msg, Boolean atlas) {
        if (atlas){
            powerGodAnswer = true;
            powerGodUse.setVisible(true);
        }
        messageBox.setText("Select cell you want to build on");
        lightUpBoard(msg);
    }

    public void sendReply() {
        clientInputGUI.Reply(reply[0], reply[1], reply[2], reply[3]);
        reply = new int[]{-5, -5, -5, -5};
    }

    public void confirmAction() {
        if (turn) {
            if (placeWorkersPhase) {
                //0 -> x first worker, 1 -> y first worker
                //2 -> x second worker, 3 -> y second worker
                for (int i = 0; i < startWorkerPos.size(); i++) {
                    reply[i] = startWorkerPos.get(i);
                }
                startWorkerPos.clear();
                placeWorkersPhase = false;
            }
            if (powerGodAnswer){
                if (powerGodUse.isSelected()){
                    reply[1] = 1;
                } else {
                    reply[0] = 0;
                }
                powerGodAnswer = false;
            }
            sendReply();
            resetLighten();
        }
    }

    private void selectedCell(CellButton cellButton) {
        resetLighten();
        if (cellButton.getIdFromList() == -5 && !placeWorkersPhase) {
            return;
        }
        if (placeWorkersPhase) {
            cellButton.lighten(false);
            if (waitWorker > 0) {
                //should save and show two different selected cells
                startWorkerPos.add(cellButton.x);
                startWorkerPos.add(cellButton.y);
                waitWorker--;
            }
        } else {
            cellButton.lighten(false);
            reply[0] = cellButton.getIdFromList();
        }
    }

    public void resetLighten(){
        for (int i = 0; i < cellButtonBoard.size(); i++){
            cellButtonBoard.get(i).turnOff();
        }
    }

    //check usage
    private void error(String header, String content) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }

    //check usage
    public void enlightenPlayer(int val){
        for (int i= 0; i<nPlayers; i++){
            if (i == val){
                playersInfo.getChildren().get(i).setStyle("-fx-background-color: #6495ed");
            } else playersInfo.getChildren().get(i).setStyle("-fx-background-color: Black");
        }
    }

    //simply refreshes players in case someone has lost
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
        //check if someone has lost
        if (activePlayers != nPlayers) {
            if (myName.equals(simpleBoard.players.get(0)) && simpleBoard.players.size() == 1) {
                winner();
            } else refreshPlayers(simpleBoard);
            nPlayers = activePlayers;
        }
        if (simpleBoard.board == null){
            return;
        }
        //updates the board itself
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

