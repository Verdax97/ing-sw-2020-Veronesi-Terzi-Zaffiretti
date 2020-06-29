package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

/**
 * The type Santorini match controller.
 */
public class SantoriniMatchController {

    @FXML
    private VBox playersInfo;
    @FXML
    private GridPane board;
    @FXML
    private AnchorPane thirdPlayerPane;
    @FXML
    private Button confirmButton;
    @FXML
    private Text whosTurn;
    @FXML
    private Button otherActionButton;
    @FXML
    private Label messageBox;
    @FXML
    private VBox chatVBox;
    @FXML
    private TextField chatInput;
    @FXML
    private ScrollPane chatPane;
    @FXML
    private Button chatButton;


    private ClientMain clientMain;
    private ClientInputGUI clientInputGUI;
    private int[] reply = {-5, -5, -5, -5};

    private final ArrayList<CellButton> cellButtonBoard = new ArrayList<>();
    private final ArrayList<Circle> cellWorker = new ArrayList<>();
    private int nPlayers;

    private boolean turn = false;
    private int waitWorker = 0;
    private boolean placeWorkersPhase = false;
    private final ArrayList<Integer> startWorkerPos = new ArrayList<>();
    private boolean powerGodAnswer = false;
    private boolean atlas = false;

    private CellButton lastCell;

    /**
     * Gets client main.
     *
     * @return the client main
     */
    public ClientMain getClientMain() {
        return clientMain;
    }

    /**
     * Sets client main.
     *
     * @param clientMain the client main
     */
    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    /**
     * Sets client input gui.
     *
     * @param clientInputGUI the client input gui
     */
    public void setClientInputGUI(ClientInputGUI clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    /**
     * Initialize all.
     *
     * @param simpleBoard the simple board
     */
    public void initializeAll(SimpleBoard simpleBoard) {
        //set all players color gods and descriptions
        refreshPlayers(simpleBoard);
        //setup all the board
        initializeBoard(simpleBoard);
        chatButton.setDefaultButton(true);
    }

    /**
     * Method initializeBoard initialize the board with players and building (if the game is resuming)
     *
     * @param simpleBoard of type SimpleBoard
     */
    private void initializeBoard(SimpleBoard simpleBoard) {
        int k = 0, z = 0;
        for (int j = 4; j >= 0; j--) {
            for (int i = 0; i < 5; i++) {
                //create and set the custom button
                CellButton cellButton = new CellButton(i, j);
                cellButton.setOnMouseClicked(e -> selectCell(cellButton));
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

    /**
     * Hide confirm button.
     */
    public void hideConfirmButton() {
        whosTurn.setText("Please wait your turn");
        otherActionButton.setVisible(false);
        Platform.runLater(() -> {
            messageBox.setText("");
            otherActionButton.setText("Cancel");
        });
        confirmButton.setVisible(false);
        turn = false;
    }

    /**
     * Show confirm button.
     */
    public void showConfirmButton() {
        whosTurn.setText("It is Your Turn");
        confirmButton.setVisible(true);
        turn = true;
    }

    /**
     * Light up board.
     *
     * @param msg the msg
     */
    public void lightUpBoard(String msg){
        //reset to default
        for (CellButton cellButton : cellButtonBoard) {
            cellButton.setIdFromList(-5);
        }
        //should parse message of possibilities
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(msg.split("\n")));
        for (String value : temp) {
            //still parsing that message
            ArrayList<String> s = new ArrayList<>(Arrays.asList(value.split(" ")));
            String index = s.get(0).split(Pattern.quote(")"))[0];
            if (s.size() > 1) {
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
    }

    /**
     * Place workers.
     */
    public void placeWorkers(){
        Platform.runLater(() -> messageBox.setText("Select two different cells\nwhere you want to put your workers"));
        waitWorker = 2;
        placeWorkersPhase = true;
    }

    /**
     * Select worker.
     *
     * @param msg the msg
     */
    public void selectWorker(String msg) {
        Platform.runLater(() -> messageBox.setText("Select worker you want to perform your turn"));
        Platform.runLater(() -> otherActionButton.setText("Cancel"));
        otherActionButton.setVisible(false);
        lightUpBoard(msg);
    }

    /**
     * Before move power.
     *
     * @param msg the msg
     */
    public void beforeMovePower(String msg) {
        otherActionButton.setVisible(true);
        Platform.runLater(() -> {
            otherActionButton.setText("Cancel");
            messageBox.setText("You can perform an action before your move");
        });
        powerGodAnswer = true;
        lightUpBoard(msg);
    }

    /**
     * Move again.
     *
     * @param msg the msg
     */
    public void moveAgain(String msg) {
        otherActionButton.setVisible(true);
        Platform.runLater(() -> {
            otherActionButton.setText("Cancel");
            messageBox.setText("You can move again");
        });
        powerGodAnswer = true;
        lightUpBoard(msg);
    }

    /**
     * Build again.
     *
     * @param msg the msg
     */
    public void buildAgain(String msg) {
        otherActionButton.setVisible(true);
        Platform.runLater(() -> {
            otherActionButton.setText("Cancel");
            messageBox.setText("You can build again");
        });
        powerGodAnswer = true;
        lightUpBoard(msg);
    }

    /**
     * Move.
     *
     * @param msg the msg
     */
    public void move(String msg) {
        Platform.runLater(() -> messageBox.setText("Select the cell you want to move to"));
        Platform.runLater(() -> otherActionButton.setText("Cancel"));
        otherActionButton.setVisible(false);
        lightUpBoard(msg);
    }

    /**
     * Build.
     *
     * @param msg   the msg
     * @param atlas the atlas
     */
    public void build(String msg, Boolean atlas) {
        if (atlas) {
            this.atlas = true;
            powerGodAnswer = true;
            otherActionButton.setVisible(true);
            Platform.runLater(() -> {
                otherActionButton.setText("Build normally");
                confirmButton.setText("Build dome");
            });
        }
        Platform.runLater(() -> messageBox.setText("Select the cell you want to build on"));
        lightUpBoard(msg);
    }

    /**
     * Send reply.
     */
    public void sendReply() {
        clientInputGUI.reply(reply[0], reply[1], reply[2], reply[3]);
        reply = new int[]{-5, -5, -5, -5};
    }

    /**
     * Confirm action.
     */
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
                isThisAtlasBuilding(true);
            } else {
                reply[1] = -5;
            }
            sendReply();
            resetLighten();
            otherActionButton.setVisible(false);
            Platform.runLater(() -> {
                otherActionButton.setText("Cancel");
                confirmButton.setText("Confirm");
            });
        }
    }

    /**
     * Method isThisAtlasBuilding check if the buttons text need to be changed
     *
     * @param cond of type boolean
     */
    private void isThisAtlasBuilding(boolean cond) {
        powerGodAnswer = false;
        if (cond) {
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

    /**
     * Other action.
     */
    public void otherAction() {
        if (turn) {
            if (powerGodAnswer) {
                isThisAtlasBuilding(false);
            } else {
                reply[1] = -5;
            }
            sendReply();
            resetLighten();
            otherActionButton.setVisible(false);
            Platform.runLater(() -> {
                otherActionButton.setText("Cancel");
                confirmButton.setText("Confirm");
            });
        }
    }

    /**
     * Method selectCell is used to select the cell values
     *
     * @param cellButton of type CellButton
     */
    private void selectCell(CellButton cellButton) {
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
            if (lastCell != null)
                lastCell.lighten(true);
            cellButton.lighten(false);
            lastCell = cellButton;
            reply[0] = cellButton.getIdFromList();
        }
        cellButton.getStyleClass().clear();
        cellButton.getStyleClass().add("selected");
    }

    /**
     * Reset lighten.
     */
    public void resetLighten() {
        for (CellButton cellButton : cellButtonBoard) {
            cellButton.turnOff();
            cellButton.setIdFromList(-5);
        }
    }

    /**
     * Enlighten player.
     *
     * @param val the val
     */
//check usage
    public void enlightenPlayer(int val) {
        try {
            for (int i = 0; i < nPlayers; i++) {
                if (i == val) {
                    playersInfo.getChildren().get(i).setStyle("-fx-background-color: #6495ed");
                } else playersInfo.getChildren().get(i).setStyle("-fx-background-color: Black");
            }
        } catch (NullPointerException e) {
            System.out.println("ok");
        }
    }

    /**
     * Refresh players.
     *
     * @param simpleBoard the simple board
     */
//simply refreshes players in case someone has lost
    public void refreshPlayers(SimpleBoard simpleBoard){
        for (int i = 0; i < simpleBoard.players.size(); i++) {
            for (int j = 0; j < simpleBoard.players.size(); j++) {
                int finalI = i;
                Platform.runLater(() -> {
                    AnchorPane temp = (AnchorPane) playersInfo.getChildren().get(finalI);
                    ((ImageView) temp.getChildren().get(1)).setImage(new Image("Images/godCards/" + simpleBoard.gods.get(finalI).getName() + ".png"));
                    ((Label) temp.getChildren().get(2)).setText(simpleBoard.players.get(finalI));
                    ((Label) temp.getChildren().get(3)).setText(simpleBoard.gods.get(finalI).getDescription());
                });
            }
        }
        if (simpleBoard.players.size() == 2) {
            thirdPlayerPane.setVisible(false);
        }
    }

    /**
     * Update board.
     *
     * @param simpleBoard the simple board
     */
    public void updateBoard(SimpleBoard simpleBoard) {
        //nothing to show
        if (simpleBoard == null){
            return;
        }
        if (simpleBoard.players.size() == 0){
            return;
        }
        lastCell = null;
        int cell = 0;
        int activePlayers = simpleBoard.players.size();
        //check if someone has lost
        if (activePlayers != nPlayers) {
            refreshPlayers(simpleBoard);
            nPlayers = activePlayers;
        }
        if (simpleBoard.board == null){
            return;
        }
        //updates the board itself
        for (int j = 4; j >= 0; j--) {
            for (int i = 0; i < 5; i++) {
                cellButtonBoard.get(cell).refresh(simpleBoard.board[i][j]);
                if (simpleBoard.board[i][j] == 4) {
                    cellWorker.get(cell).setStyle("-fx-fill: " + pickColor(5) + "; -fx-stroke: transparent");
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
                            cellWorker.get(cell).setStyle("-fx-fill: " + pickColor(val) + "; -fx-stroke: black");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        cellWorker.get(cell).setStyle("-fx-fill: " + pickColor(5) + "; -fx-stroke: transparent");
                    }
                }
                cell++;
            }
        }
    }

    /**
     * Method pickColor used for displaying the correct workers color
     *
     * @param val of type int
     * @return String
     */
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

    /**
     * Resume.
     *
     * @param yourTurn the your turn
     */
    public void resume(boolean yourTurn) {
        waitWorker = 0;
        turn = yourTurn;
        if (!turn)
            hideConfirmButton();
    }

    /**
     * Method sendChatMessage send the message to the server if it is not empty
     */
    public void sendChatMessage() {
        if (chatInput.getText().length() > 0) {
            clientInputGUI.sendChatMsg(chatInput.getText());
            chatInput.setText("");
        }
    }

    /**
     * Method receiveChatMessage receives a chat message from the server and update the chat panel
     *
     * @param msg of type String
     */
    public void receiveChatMessage(String msg) {
        String finalMsg = msg.split(":", 2)[1];
        Platform.runLater(() -> {
            chatVBox.getChildren().add(new Label(finalMsg));
            chatPane.setVvalue(1);
        });
    }
}

