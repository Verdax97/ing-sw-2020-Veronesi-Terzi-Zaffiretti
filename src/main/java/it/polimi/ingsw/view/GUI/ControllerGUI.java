package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * Class ControllerGUI manage all the GUI windows and switches GUI from a window to another one.
 */
public class ControllerGUI {

    /**
     * The Connection scene.
     */
    Scene connectionScene, /**
     * The Lobby scene.
     */
    lobbyScene, /**
     * The Pick god scene.
     */
    pickGodScene, /**
     * The Santorini match scene.
     */
    santoriniMatchScene;
    private ClientMain clientMain = null;
    private ClientInputGUI clientInputGUI = null;
    private LobbyController lobbyController = null;
    private PickGodsController pickGodsController = null;
    private SantoriniMatchController santoriniMatchController = null;

    private Stage primaryStage = null;

    /**
     * Method getSantoriniMatchController returns the santoriniMatchController of this ControllerGUI object.
     *
     * @return santoriniMatchController of type SantoriniMatchController
     */
    public SantoriniMatchController getSantoriniMatchController() {
        return santoriniMatchController;
    }

    /**
     * Method getClientMain returns the clientMain of this ControllerGUI object.
     *
     * @return clientMain of type ClientMain
     */
    public ClientMain getClientMain() {
        return clientMain;
    }

    /**
     * Method setClientMain sets the clientMain of this ControllerGUI object.
     *
     * @param clientMain of type ClientMain
     */
    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    /**
     * Method setClientInputGUI sets the clientInputGui of this ControllerGUI object.
     *
     * @param clientInputGUI of type ClientInputGUI
     */
    public void setClientInputGUI(ClientInputGUI clientInputGUI) { this.clientInputGUI = clientInputGUI; }

    /**
     * Method setPrimaryStage sets the primaryStage of this ControllerGUI object.
     *
     * @param primaryStage of type Stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Method getFirstWindow sets visible the first windows of the GUI, Launcher.
     *
     * @throws IOException of type IOException
     */
    public void getFirstWindow() throws IOException {
        FXMLLoader loaderLauncher = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        Parent rootLauncher = loaderLauncher.load();
        LauncherController launcherController = loaderLauncher.getController();
        launcherController.setClientMain(clientMain);
        connectionScene = new Scene(rootLauncher);
        LauncherApp.primaryStage.setScene(connectionScene);
    }

    /**
     * Method changeToLobby switches window in Lobby, master value changes elements shown.
     *
     * @param master of type boolean
     * @throws IOException of type IOException
     */
    public void changeToLobby(boolean master) throws IOException {
        if (lobbyController == null){
            FXMLLoader loaderLobby = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
            Parent rootLobby = loaderLobby.load();
            lobbyController = loaderLobby.getController();
            lobbyController.setClientMain(clientMain);
            lobbyController.setClientInputGUI(clientInputGUI);
            lobbyScene = new Scene(rootLobby);
            primaryStage.setScene(lobbyScene);
            primaryStage.setResizable(false);
            primaryStage.setTitle("Lobby");
        }
        if (master) {
            lobbyController.showNumberPlayers();
        }
        else lobbyController.showNicknames();
    }

    /**
     * Method resume offers to master player the possibility to resume a match in Lobby window.
     */
    public void resume(){ lobbyController.showResume(); }

    /**
     * Method changeToPickGods switches window in PickGods.
     *
     * @throws IOException of type IOException
     */
    public void changeToPickGods() throws IOException {
        FXMLLoader loaderPickGod = new FXMLLoader(getClass().getClassLoader().getResource("FXML/PickGods.fxml"));
        Parent rootMatch = loaderPickGod.load();
        pickGodsController = loaderPickGod.getController();
        pickGodsController.setClientMain(clientMain);
        pickGodsController.setClientInputGUI(clientInputGUI);
        pickGodScene = new Scene(rootMatch);
        primaryStage.setScene(pickGodScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Pick God Cards");
    }

    /**
     * Method showGods shows players the pick able god cards, yourTurn value change elements shown
     *
     * @param msg      of type String
     * @param yourTurn of type boolean
     */
    public void showGods(String msg, boolean yourTurn) {
        pickGodsController.getDescriptionGod(msg, yourTurn);
    }

    /**
     * Method changeToSantoriniMatch switches window in SantoriniMatch, yourTurn value changes elements shown, resume value changes state of the window.
     *
     * @param simpleBoard of type SimpleBoard
     * @param yourTurn    of type boolean
     * @param resume      of type boolean
     * @throws IOException of type IOException
     */
    public void changeToSantoriniMatch(SimpleBoard simpleBoard, boolean yourTurn, boolean resume) throws IOException {
        if (santoriniMatchController == null) {
            FXMLLoader loaderSantoriniMatch = new FXMLLoader(getClass().getClassLoader().getResource("FXML/SantoriniMatch.fxml"));
            Parent rootSantoriniMatch = loaderSantoriniMatch.load();
            santoriniMatchController = loaderSantoriniMatch.getController();
            santoriniMatchController.setClientMain(clientMain);
            santoriniMatchController.setClientInputGUI(clientInputGUI);
            santoriniMatchScene = new Scene(rootSantoriniMatch);
            primaryStage.setScene(santoriniMatchScene);
            primaryStage.setTitle("Santorini Board Game");
            primaryStage.setResizable(false);
            santoriniMatchController.initializeAll(simpleBoard);
        }
        if (resume)
            santoriniMatchController.resume(yourTurn);
        else if (yourTurn) {
            santoriniMatchController.placeWorkers();
        }
    }

    /**
     * Method waitYourTurn changes elements shown if it is not your turn in SantoriniMatch window.
     */
    public void waitYourTurn(){
        if (santoriniMatchController == null) {
            lobbyController.waitForStart();
        } else santoriniMatchController.hideConfirmButton();
    }

    /**
     * Method itIsYourTurn changes elements shown if it is not your turn in SantoriniMatch window.
     */
    public void itIsYourTurn() { santoriniMatchController.showConfirmButton(); }

    /**
     * Method selectWorker.
     *
     * @param msg the msg
     */
    public void selectWorker(String msg) {
        santoriniMatchController.selectWorker(msg);
    }

    /**
     * Method beforeMovePower.
     *
     * @param msg of type String
     */
    public void beforeMovePower(String msg){
        santoriniMatchController.beforeMovePower(msg);
    }

    /**
     * Method moveAgain.
     *
     * @param msg of type String
     */
    public void moveAgain(String msg){
        santoriniMatchController.moveAgain(msg);
    }

    /**
     * Method move.
     *
     * @param msg of type String
     */
    public void move(String msg){
        santoriniMatchController.move(msg);
    }

    /**
     * Method buildAgain.
     *
     * @param msg of type String
     */
    public void buildAgain(String msg){
        santoriniMatchController.buildAgain(msg);
    }

    /**
     * Method Build.
     *
     * @param msg   of type String
     * @param atlas of type boolean
     */
    public void build(String msg, Boolean atlas){
        santoriniMatchController.build(msg, atlas);
    }

    /**
     * Method activePlayer.
     *
     * @param simpleBoard of type SimpleBoard
     * @param name of type String
     */
    public void activePlayer(SimpleBoard simpleBoard, String name) {
        if (simpleBoard == null)
            return;
        for (int val = 0; val < simpleBoard.players.size(); val++)
            if (simpleBoard.players.get(val).equals(name)) {
                int finalVal = val;
                Platform.runLater(() -> {
                    try {
                        santoriniMatchController.enlightenPlayer(finalVal);
                    } catch (NullPointerException e) {
                        //should do nothing
                    }
                });
            }
    }

    /**
     * Method receiveUpdate.
     *
     * @param board the board
     */
    public void receiveUpdate(SimpleBoard board){
        if (santoriniMatchController != null) {
            santoriniMatchController.updateBoard(board);
        }
    }

    /**
     * Method error popups an error window.
     *
     * @param header  of type String
     * @param content of type String
     */
    public void error(String header, String content) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }

    /**
     * Method endGame changes window to the endgame scene.
     *
     * @param message of type String
     * @param won     of type boolean
     */
    public void endGame(String message, boolean won) {
        FXMLLoader loaderEndgame = new FXMLLoader(getClass().getClassLoader().getResource("FXML/GameClosing.fxml"));
        Parent rootEndGame;
        Stage secondStage = new Stage();
        try {
            rootEndGame = loaderEndgame.load();
            ((GameCloserWindow) loaderEndgame.getController()).setWinner(won);
            ((GameCloserWindow) loaderEndgame.getController()).setMessage(message, won);
            Scene secondScene = new Scene(rootEndGame);
            secondStage.setTitle("Game Ending");
            secondStage.setScene(secondScene);
            secondStage.show();
            ((GameCloserWindow) loaderEndgame.getController()).showVideo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method closePopUp popups an info window.
     *
     * @param title   of type String
     * @param message of type String
     */
    public void closePopUp(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("Confirm to close the game.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // ... user chose OK
            System.exit(0);
        } else {
            // ... user chose CANCEL or closed the dialog
            closePopUp("You will never get rid of me this way", "Bold of you to assume that i can so easily be bested!");
        }
    }

    /**
     * Method receiveChatMessage passes the message to the SantoriniMatchController.
     *
     * @param msg of type String
     */
    public void receiveChatMessage(String msg) {
        if (santoriniMatchController != null)
            santoriniMatchController.receiveChatMessage(msg);
    }
}
