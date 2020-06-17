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
 * The type Controller gui.
 * @author Stefano
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
    //public LauncherController launcherController = null;
    private LobbyController lobbyController = null;
    private PickGodsController pickGodsController = null;

    /**
     * Gets santorini match controller.
     *
     * @return the santorini match controller
     */
    public SantoriniMatchController getSantoriniMatchController() {
        return santoriniMatchController;
    }

    private SantoriniMatchController santoriniMatchController = null;

    private int firstTime = 0;

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
    public void setClientInputGUI(ClientInputGUI clientInputGUI) { this.clientInputGUI = clientInputGUI; }

    /**
     * Gets primary stage.
     *
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Sets primary stage.
     *
     * @param primaryStage the primary stage
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Stage primaryStage = null;


    /**
     * Gets first window.
     *
     * @throws IOException the io exception
     */
    public void getFirstWindow() throws IOException {
        FXMLLoader loaderLauncher = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        Parent rootLauncher = (Parent) loaderLauncher.load();
        LauncherController launcherController = loaderLauncher.getController();
        launcherController.setClientMain(clientMain);
        //((ClientInputGUI) clientMain.getClientInput()).setLauncherController(launcherController);
        connectionScene = new Scene(rootLauncher);
        LauncherApp.primaryStage.setScene(connectionScene);
    }

    /**
     * Change to lobby.
     *
     * @param master the master
     * @throws IOException the io exception
     */
    public void changeToLobby(boolean master) throws IOException {
        if (lobbyController == null){
            FXMLLoader loaderLobby = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
            Parent rootLobby = (Parent) loaderLobby.load();
            lobbyController = loaderLobby.getController();
            lobbyController.setClientMain(clientMain);
            lobbyController.setClientInputGUI(clientInputGUI);
            ((ClientInputGUI) clientMain.getClientInput()).setLobbyController(lobbyController);
            lobbyScene = new Scene(rootLobby);
            primaryStage.setScene(lobbyScene);
            primaryStage.setTitle("Lobby");
        }
        if (master) {
            lobbyController.showNumberPlayers();
        }
        else lobbyController.showNicknames();
    }

    /**
     * Resume.
     */
    public void resume(){ lobbyController.showResume(); }

    /**
     * Change to pick gods.
     *
     * @throws IOException the io exception
     */
    public void changeToPickGods() throws IOException {
        FXMLLoader loaderPickGod = new FXMLLoader(getClass().getClassLoader().getResource("FXML/PickGods.fxml"));
        Parent rootMatch = (Parent) loaderPickGod.load();
        pickGodsController = loaderPickGod.getController();
        pickGodsController.setClientMain(clientMain);
        pickGodsController.setClientInputGUI(clientInputGUI);
        ((ClientInputGUI) clientMain.getClientInput()).setPickGodsController(pickGodsController);
        pickGodScene = new Scene(rootMatch);
        primaryStage.setScene(pickGodScene);
        primaryStage.setTitle("Pick God Cards");
    }

    /**
     * Show gods.
     *
     * @param msg      the msg
     * @param yourTurn the your turn
     */
    public void showGods(String msg, boolean yourTurn) {
        pickGodsController.getDescriptionGod(msg, yourTurn);
    }

    /**
     * Change to santorini match.
     *
     * @param simpleBoard the simple board
     * @param yourTurn    the your turn
     * @param resume      the resume
     * @throws IOException the io exception
     */
    public void changeToSantoriniMatch(SimpleBoard simpleBoard, boolean yourTurn, boolean resume) throws IOException {
        if (santoriniMatchController == null) {
            FXMLLoader loaderSantoriniMatch = new FXMLLoader(getClass().getClassLoader().getResource("FXML/SantoriniMatch.fxml"));
            Parent rootSantoriniMatch = (Parent) loaderSantoriniMatch.load();
            santoriniMatchController = loaderSantoriniMatch.getController();
            santoriniMatchController.setClientMain(clientMain);
            santoriniMatchController.setClientInputGUI(clientInputGUI);
            ((ClientInputGUI) clientMain.getClientInput()).setSantoriniMatchController(santoriniMatchController);
            santoriniMatchScene = new Scene(rootSantoriniMatch);
            primaryStage.setScene(santoriniMatchScene);
            primaryStage.setTitle("Santorini Board Game");
            santoriniMatchController.setMyName(clientInputGUI.getMyName());
            santoriniMatchController.initializeAll(simpleBoard);
        }
        if (resume)
            santoriniMatchController.resume(yourTurn);
        else if (yourTurn) {
            santoriniMatchController.placeWorkers();
        }
    }

    /**
     * Wait your turn.
     */
    public void waitYourTurn(){
        if (santoriniMatchController == null) {
            lobbyController.waitForStart();
        } else santoriniMatchController.hideConfirmButton();
    }

    /**
     * It is your turn.
     */
    public void itIsYourTurn() { santoriniMatchController.showConfirmButton(); }

    /**
     * Select worker.
     *
     * @param msg the msg
     */
    public void selectWorker(String msg) {
        santoriniMatchController.selectWorker(msg);
    }

    /**
     * Before move power.
     *
     * @param msg the msg
     */
    public void beforeMovePower(String msg){
        santoriniMatchController.beforeMovePower(msg);
    }

    /**
     * Move again.
     *
     * @param msg the msg
     */
    public void moveAgain(String msg){
        santoriniMatchController.moveAgain(msg);
    }

    /**
     * Move.
     *
     * @param msg the msg
     */
    public void move(String msg){
        santoriniMatchController.move(msg);
    }

    /**
     * Build again.
     *
     * @param msg the msg
     */
    public void buildAgain(String msg){
        santoriniMatchController.buildAgain(msg);
    }

    /**
     * Build.
     *
     * @param msg   the msg
     * @param atlas the atlas
     */
    public void build(String msg, Boolean atlas){
        santoriniMatchController.build(msg, atlas);
    }

    /**
     * Active player.
     *
     * @param simpleBoard the board with all information
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
                        System.out.println("ok");
                    }
                });
            }
    }

    /**
     * Receive update.
     *
     * @param board the board
     */
    public void receiveUpdate(SimpleBoard board){
        if (santoriniMatchController != null) {
            santoriniMatchController.updateBoard(board);
        }
    }

    /**
     * Error.
     *
     * @param header  the header
     * @param content the content
     */
    public void error(String header, String content) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }

    public void endGame(String message, boolean won) {
        System.out.println("We are in the endgame!");
        FXMLLoader loaderEndgame = new FXMLLoader(getClass().getClassLoader().getResource("FXML/GameClosing.fxml"));
        Parent rootEndGame = null;
        Stage secondStage = new Stage();
        try {
            rootEndGame = (Parent) loaderEndgame.load();
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

    public void infoPopUp(String title, String message) {
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
            infoPopUp("You will never get rid of me this way", "Bold of you to assume that i can so easily be bested!");
        }
    }
}
