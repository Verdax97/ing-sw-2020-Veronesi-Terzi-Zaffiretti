package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerGUI {

    Scene connectionScene, lobbyScene, pickGodScene, santoriniMatchScene;
    private ClientMain clientMain = null;
    private ClientInputGUI clientInputGUI = null;
    //public LauncherController launcherController = null;
    private LobbyController lobbyController = null;
    private PickGodsController pickGodsController = null;

    public SantoriniMatchController getSantoriniMatchController() {
        return santoriniMatchController;
    }

    private SantoriniMatchController santoriniMatchController = null;

    private int firstTime = 0;

    public ClientMain getClientMain() {
        return clientMain;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void setClientInputGUI(ClientInputGUI clientInputGUI) { this.clientInputGUI = clientInputGUI; }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Stage primaryStage = null;


    public void getFirstWindow() throws IOException {
        FXMLLoader loaderLauncher = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        Parent rootLauncher = (Parent) loaderLauncher.load();
        LauncherController launcherController = loaderLauncher.getController();
        launcherController.setClientMain(clientMain);
        //((ClientInputGUI) clientMain.getClientInput()).setLauncherController(launcherController);
        connectionScene = new Scene(rootLauncher);
        LauncherApp.primaryStage.setScene(connectionScene);
    }

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

    public void resume(){ lobbyController.showResume(); }

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

    public void showGods(String msg, boolean yourTurn) {
        pickGodsController.getDescriptionGod(msg, yourTurn);
    }

    public void changeToSantoriniMatch(SimpleBoard simpleBoard, boolean yourTurn) throws IOException {
        if (santoriniMatchController == null) {
            FXMLLoader loaderSantoriniMatch = new FXMLLoader(getClass().getClassLoader().getResource("FXML/SantoriniMatch.fxml"));
            Parent rootSantoriniMatch =(Parent) loaderSantoriniMatch.load();
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
        if (yourTurn){
            santoriniMatchController.placeWorkers();
        }
    }

    public void waitYourTurn(){
        if (santoriniMatchController == null) {
            lobbyController.waitForStart();
        } else santoriniMatchController.hideConfirmButton();
    }

    public void itIsYourTurn() { santoriniMatchController.showConfirmButton(); }

    public void selectWorker(String msg) {
        santoriniMatchController.selectWorker(msg);
    }

    public void beforeMovePower(String msg){
        santoriniMatchController.beforeMovePower(msg);
    }

    public void moveAgain(){
        santoriniMatchController.moveAgain();
    }

    public void move(String msg){
        santoriniMatchController.move(msg);
    }

    public void buildAgain(){
        santoriniMatchController.buildAgain();
    }

    public void build(String msg, Boolean atlas){
        santoriniMatchController.build(msg, atlas);
    }

    public void receiveUpdate(SimpleBoard board){
        if (santoriniMatchController != null) {
            santoriniMatchController.updateBoard(board);
        }
    }

    public void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
