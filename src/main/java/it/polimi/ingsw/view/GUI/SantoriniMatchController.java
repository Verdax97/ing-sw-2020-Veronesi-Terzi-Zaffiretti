package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class SantoriniMatchController {

    @FXML private VBox playersInfo;
    @FXML private GridPane board;
    @FXML private AnchorPane firstPlayerPane;
    @FXML private AnchorPane secondPlayerPane;
    @FXML private AnchorPane thirdPlayerPane;
    @FXML
    private Button confirmButton;
    @FXML
    private Text whosTurn;
    @FXML
    private Text godMessageBox;
    @FXML
    private CheckBox powerGodUse;
    @FXML
    private Label messageBox;

    private ClientMain clientMain;
    private ClientInput clientInputGUI;
    private int[] reply = {-5, -5, -5, -5};

    private final ArrayList<CellButton> cellButtonBoard = new ArrayList<>();
    private final ArrayList<Circle> cellWorker = new ArrayList<>();
    private int nPlayers;
    private String myName;
    private final ArrayList<String> players = new ArrayList<>();

    private boolean turn = false;
    private int waitWorker = 0;
    private boolean placeWorkersPhase = false;
    private final ArrayList<Integer> startWorkerPos = new ArrayList<>();
    private boolean powerGodAnswer = false;
    private boolean atlas = false;

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
        //set all players color gods and descriptions
        refreshPlayers(simpleBoard);
        //setup all the board
        initializeBoard(simpleBoard);
    }

    private void initializeBoard(SimpleBoard simpleBoard) {
        int k = 0, z = 0;
        for (int j = 4; j >= 0; j--) {
            for (int i = 0; i < 5; i++) {
                //create and set the custom button
                CellButton cellButton = new CellButton(i, j);
                cellButton.setOnAction(e -> selectedCell(cellButton));
                board.add(cellButton, k, z);
                //create the "worker" placeholder
                Circle worker = new Circle();
                worker.setMouseTransparent(true);
                worker.setRadius(15.0);
                board.add(worker, k, z);
                //add the button and the worker to the list
                cellButtonBoard.add(cellButton);
                cellWorker.add(worker);
                //grid pane works opposite than matrix
                if (k == 4) {
                    k = 0;
                    z++;
                } else k++;
            }
        }
        updateBoard(simpleBoard);
    }

    public void hideConfirmButton() {
        whosTurn.setText("Please wait your turn");
        godMessageBox.setText("");
        powerGodUse.setVisible(false);
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
        //reset to default
        for (CellButton cellButton : cellButtonBoard) {
            cellButton.setIdFromList(-5);
        }
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
            for (CellButton cellButton : cellButtonBoard) {
                if (cellButton.x == Integer.parseInt(x) && cellButton.y == Integer.parseInt(y)) {
                    cellButton.setIdFromList(Integer.parseInt(index));
                    cellButton.lighten(true);
                    break;
                }
            }
        }
    }

    public void placeWorkers(){
        Platform.runLater(()-> {
            messageBox.setText("Select two different cell\nwhere you want to put your workers");
        });
        waitWorker = 2;
        placeWorkersPhase = true;
    }

    public void selectWorker(String msg) {
        Platform.runLater(()-> {
            messageBox.setText("Select worker you want to perform your turn");
        });
        powerGodUse.setVisible(false);
        lightUpBoard(msg);
    }

    public void beforeMovePower(String msg) {
        godMessageBox.setText("You can perform an action before your move");
        powerGodUse.setVisible(true);
        godMessageBox.setText("Do you want to make an action before the move?");
        powerGodAnswer = true;
        lightUpBoard(msg);
    }

    public void moveAgain(String msg) {
        godMessageBox.setText("You can move again");
        powerGodUse.setVisible(true);
        godMessageBox.setText("Do you want to move again?");
        powerGodAnswer = true;
        lightUpBoard(msg);
    }

    public void buildAgain(String msg) {
        godMessageBox.setText("You could build again");
        powerGodUse.setVisible(true);
        godMessageBox.setText("Do you want to build again?");
        powerGodAnswer = true;
        lightUpBoard(msg);
    }

    public void move(String msg) {
        Platform.runLater(() -> {
            messageBox.setText("Select cell you want to move to");
        });
        powerGodUse.setVisible(false);
        lightUpBoard(msg);
    }

    public void build(String msg, Boolean atlas) {
        if (atlas) {
            this.atlas = true;
            powerGodAnswer = true;
            powerGodUse.setVisible(true);
            godMessageBox.setText("Do you want to build a dome?");
        } else
            powerGodUse.setVisible(false);
        Platform.runLater(()-> {
            messageBox.setText("Select cell you want to build on");
        });
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
            } else if (powerGodAnswer) {
                isThisAtlasBuilding();
            } else {
                reply[1] = -5;
            }
            sendReply();
            resetLighten();
        }
    }

    private void isThisAtlasBuilding() {
        powerGodAnswer = false;
        if (powerGodUse.isSelected()) {
            if (atlas) {
                reply[2] = 1;
                this.atlas = false;
                return;
            }

            reply[1] = 1;
            return;
        }
        if (atlas) {
            reply[2] = 0;
            this.atlas = false;
            return;
        }
        reply[1] = 0;
    }

    private void selectedCell(CellButton cellButton) {
        if (cellButton.getIdFromList() == -5 && !placeWorkersPhase) {
            return;
        }
        if (placeWorkersPhase) {
            if (waitWorker == 0) {
                return;
            }
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
        cellButton.getStyleClass().clear();
        cellButton.getStyleClass().add("selected");
    }

    public void resetLighten() {
        for (CellButton cellButton : cellButtonBoard) {
            cellButton.turnOff();
            cellButton.setIdFromList(-5);
        }
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
        //nothing to show
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
                            cellButtonBoard.get(cell).refresh(simpleBoard.board[i][j]);
                            cellWorker.get(cell).setStyle("-fx-fill: " + pickColor(val) + "; -fx-stroke: black");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        cellButtonBoard.get(cell).refresh(simpleBoard.board[i][j]); //no worker and no dome
                        cellWorker.get(cell).setStyle("-fx-fill: " + pickColor(5) + "; -fx-stroke: transparent");
                    }
                }
                cell++;
            }
        }
    }

    private String pickColor(int val) {
        switch (val) {
            case 0 -> {
                return "red";
            }
            case 1 -> {
                return "green";
            }
            case 2 -> {
                return "blue";
            }
            default -> {
                return "transparent";
            }
        }
    }

    public void resume(boolean yourTurn) {
        //todo
        waitWorker = 0;
        turn = yourTurn;
    }
}

