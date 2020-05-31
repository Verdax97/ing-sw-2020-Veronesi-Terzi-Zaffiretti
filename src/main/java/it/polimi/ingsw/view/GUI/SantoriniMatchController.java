package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SantoriniMatchController {

    @FXML private AnchorPane santoriniMatch;
    @FXML private GridPane board;
    @FXML private Text firstPlayerNick;
    @FXML private Text secondPlayerNick;
    @FXML private Text thirdPlayerNick;
    @FXML private ImageView firstPlayerGodImage;
    @FXML private ImageView secondPlayerGodImage;
    @FXML private ImageView thirdPlayerGodImage;
    @FXML private Circle firstPlayerColor;
    @FXML private Circle secondPlayerColor;
    @FXML private Circle thirdPlayerColor;
    @FXML private Rectangle currentOne;
    @FXML private Rectangle currentTwo;
    @FXML private Rectangle currentThree;
    @FXML private Button confirmButton;
    @FXML private Text whosTurn;
    @FXML private Text godUsageMessageBox;
    @FXML private ChoiceBox godPowerUse;
    @FXML private CheckBox domeTruth;
    @FXML private Text messageBox;
    @FXML private Text selectedCellBox;

    private ClientMain clientMain;
    private ClientInput clientInputGUI;
    private int[] reply = {-5, -5, -5, -5};

    private ArrayList<CellButton> cellButtonBoard = new ArrayList<>();
    private int nPlayers;

    private String myName;
    //i discover who i am
    private boolean first = false, second = false, third = false;
    //1 red, 2 green, 3 blue
    private int myColor;

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

    public void initializeAll(SimpleBoard simpleBoard) {
        santoriniMatch.setStyle("-fx-background-image: url('/Images/SantoriniBoard.png'); -fx-background-size: 100% 100%; -fx-background-repeat: no-repeat;");
        for (int i = 0; i < simpleBoard.players.size(); i++) {
            if (i == 0) {
                firstPlayerNick.setText(simpleBoard.players.get(i));
                firstPlayerGodImage.setImage(new Image("Images/godCards/" + simpleBoard.gods.get(i).getName() + ".png"));
                firstPlayerColor.setFill(Color.RED);
                if (myName.equals(simpleBoard.players.get(i))) {
                    first = true;
                    myColor = 0;
                }
            }
            if (i == 1) {
                secondPlayerNick.setText(simpleBoard.players.get(i));
                secondPlayerGodImage.setImage(new Image("Images/godCards/" + simpleBoard.gods.get(i).getName() + ".png"));
                secondPlayerColor.setFill(Color.GREEN);
                nPlayers = 2;
                if (myName.equals(simpleBoard.players.get(i))) {
                    second = true;
                    myColor = 1;
                }
            }
            if (i == 2) {
                thirdPlayerNick.setText(simpleBoard.players.get(i));
                thirdPlayerGodImage.setImage(new Image("Images/godCards/" + simpleBoard.gods.get(i).getName() + ".png"));
                thirdPlayerColor.setFill(Color.BLUE);
                nPlayers = 3;
                if (myName.equals(simpleBoard.players.get(i))) {
                    third = true;
                    myColor = 2;
                }
            }
            players.add(simpleBoard.players.get(i));
        }
        if (simpleBoard.players.size() == 2) {
            thirdPlayerNick.setVisible(false);
            thirdPlayerGodImage.setVisible(false);
            thirdPlayerColor.setVisible(false);
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
        godUsageMessageBox.setText("");
        confirmButton.setVisible(false);
    }

    public void showConfirmButton() {
        whosTurn.setText("It is Your Turn");
        confirmButton.setVisible(true);
    }

    public void placeWorkers(){
        messageBox.setText("Select two different cell where you want to put your workers");
        waitWorker = 2;
        placeWorkersPhase = true;
    }

    public void selectWorker() {
        messageBox.setText("Select worker you want to perform your turn");
    }

    public void beforeMovePower() {
        godUsageMessageBox.setText("You can perform an action before your move");
        godPowerUse.setVisible(true);
    }

    public void moveAgain() {
        godUsageMessageBox.setText("You can move again");
        godPowerUse.setVisible(true);
    }

    public void buildAgain() {
        godUsageMessageBox.setText("You could build again");
        godPowerUse.setVisible(true);
    }

    public void godPowerUsed(){
        String choice = godPowerUse.getValue().toString();
        if (choice.equalsIgnoreCase("Yes")){
            reply[0] = 1;
        }
        if (choice.equalsIgnoreCase("No")){
            reply[0] = 0;
        }
        godPowerUse.setVisible(false);
    }

    public void move() {
        messageBox.setText("Select cell you want to move to");
    }

    public void build(Boolean atlas) {
        messageBox.setText("Select cell you want to build on");
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
    }

    private void selectedCell(CellButton cellButton) {
        //TODO make the button light up when pressed and eventually light off
        cellButton.setStyle("-fx-background-color: #333333");
        String temp = String.format("%d cose %d", cellButton.x, cellButton.y);
        selectedCellBox.setText(temp);
        if (waitWorker > 0) {
            //should save and show two different selected cells
            startWorkerPos.add(cellButton.x);
            startWorkerPos.add(cellButton.y);
            waitWorker--;
            }
        else {
            //show in a particular text box info about current selected cell

            //prepare message to send to the server
            reply[0]=cellButton.x;
            reply[1]=cellButton.y;
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

    private void loser() {
        //removes himself from visible players
        //throw lose window
        //debug message
        //should remove from visible player that got eliminated
        Alert loseAlert = new Alert(Alert.AlertType.INFORMATION);
        loseAlert.setHeaderText("You lose");
        loseAlert.setContentText("Press ok and continue to spectate the game");
    }

    private void thisManLose(String loser){
        int index = -1;
        for (int i = 0; i < players.size(); i++){
            if (loser.equals(players.get(i))){
                index = i;
                break;
            }
        }
        players.remove(loser);
        nPlayers--;
        if (index == 0){
            firstPlayerNick.setVisible(false);
            firstPlayerGodImage.setVisible(false);
            firstPlayerColor.setVisible(false);
        }
        if (index == 1){
            secondPlayerNick.setVisible(false);
            secondPlayerGodImage.setVisible(false);
            secondPlayerColor.setVisible(false);
        }
        if(index == 2){
            thirdPlayerNick.setVisible(false);
            thirdPlayerGodImage.setVisible(false);
            thirdPlayerColor.setVisible(false);
        }
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
            } else {
                temp.removeAll(simpleBoard.players);
                if (myName.equals(temp.get(0))) {
                    loser();
                } else thisManLose(temp.get(0));
            }
        } else {
            for (int i = 0; i < nPlayers; i++) {
                if (myName.equals(simpleBoard.players.get(i))) {
                    //light up rectangle of current player
                    if (i == 0 && first) {
                        currentOne.setVisible(true);
                        currentTwo.setVisible(false);
                        currentThree.setVisible(false);
                    }
                    if (i == 1 && second) {
                        currentOne.setVisible(false);
                        currentTwo.setVisible(true);
                        currentThree.setVisible(false);
                    }
                    if (i == 2 && third) {
                        currentOne.setVisible(false);
                        currentTwo.setVisible(false);
                        currentThree.setVisible(true);
                    }
                }
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
}

