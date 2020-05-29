package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class SantoriniMatchController {

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
    @FXML private Text messageBox;

    private ClientMain clientMain;
    private ClientInput clientInputGUI;
    private int[] reply;

    public void setMyName(String myName) {
        this.myName = myName;
    }

    private String myName;

    private ArrayList<CellButton> cellButtonBoard = new ArrayList<>();
    private int nPlayers;
    private int indexWorkers;
    private boolean active = true;

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

    public void initializeAll(SimpleBoard simpleBoard){
        for (int i=0; i<simpleBoard.players.size(); i++){
            if (i == 0) {
                firstPlayerNick.setText(simpleBoard.players.get(i));
                firstPlayerGodImage.setImage(new Image("Images/godCards/" + simpleBoard.gods.get(i).getName() + ".png"));
                firstPlayerColor.setFill(Color.RED);
            }
            if (i == 1){
                secondPlayerNick.setText(simpleBoard.players.get(i));
                secondPlayerGodImage.setImage(new Image("Images/godCards/" + simpleBoard.gods.get(i).getName() + ".png"));
                secondPlayerColor.setFill(Color.GREEN);
                nPlayers = 2;
                indexWorkers = 3;
            }
            if (i == 2){
                thirdPlayerNick.setText(simpleBoard.players.get(i));
                thirdPlayerGodImage.setImage(new Image("Images/godCards/" + simpleBoard.gods.get(i).getName() + ".png"));
                thirdPlayerColor.setFill(Color.BLUE);
                nPlayers = 3;
                indexWorkers = 5;
            }
        }
        if(simpleBoard.players.size()==2){
            thirdPlayerNick.setVisible(false);
            thirdPlayerGodImage.setVisible(false);
            thirdPlayerColor.setVisible(false);
        }
        initializeBoard();
    }

    private void initializeBoard(){
        for (int i=0; i<5; i++){
            for (int j=0; j<5; j++){
                String position = new String(Integer.toString(i)+"d"+Integer.toString(j));
                CellButton cellButton = new CellButton(position);
                cellButton.setOnAction(e -> selectedCell());
                board.add(cellButton, i, j);
                cellButtonBoard.add(cellButton);
            }
        }
    }

    public void hideConfirmButton(){ confirmButton.setVisible(false); }

    public void showConfirmButton(){ confirmButton.setVisible(true); }

    public void selectWorker(){
        messageBox.setText("Select two different cells, your workers will be put on them");
        waitWorker = true;
    }

    public void beforeMovePower(){

    }


    public void moveAgain(){

    }

    public void move(){
    }

    public void buildAgain(){

    }

    public void build(Boolean atlas) {

    }

    public void sendReply(){
        clientInputGUI.Reply(reply[0], reply[1], reply[2], reply[3]);
        reply = new int[] {-5, -5, -5, -5};
    }

    public void confirmAction(){
        sendReply();
    }

    private void selectedCell(){
        //show in a particular text box info about current selected cell
        //prepare message to send to the server
        if (waitWorker == true){
            //should save and show two different selected cells
        }
        else {
            //normal stuff
        }
    }

    private void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }

    private void winner(){
        //debug message
        System.out.println("Winner");
    }

    private void loser(){
        System.out.println("Loser");
    }

    public void updateBoard(SimpleBoard simpleBoard) {
        int cell = 0;
        int activePlayers = simpleBoard.players.size();
        if ( activePlayers != nPlayers){
            if (activePlayers == 1 && myName == simpleBoard.players.get(0)){
                winner();
            }
            else {
                for (int i = 0; i < nPlayers - 1; i++) {
                    if (myName == simpleBoard.players.get(i)) {
                        active = true;
                    } else active = false;
                }
            }
        }
        if (active == false){
            nPlayers--;
            indexWorkers--;
            loser();
        }
        else {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < indexWorkers; k++) {
                        if (k == 0 || k == 1) {
                            cellButtonBoard.get(cell).refresh(simpleBoard.board[i][j], 0);
                        }
                        if (k == 1 || k == 2) {
                            cellButtonBoard.get(cell).refresh(simpleBoard.board[i][j], 1);
                        }
                        if (k == 3 || k == 4) {
                            cellButtonBoard.get(cell).refresh(simpleBoard.board[i][j], 2);
                        }
                    }
                    cellButtonBoard.get(cell).refresh(simpleBoard.board[i][j], -1);
                    cell++;
                }
            }
        }
    }
}
