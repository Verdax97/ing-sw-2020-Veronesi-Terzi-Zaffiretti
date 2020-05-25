package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerGUI {

    Scene connectionScene, lobbyScene, pickGodScene, santoriniMatchScene;
    private ClientMain clientMain = null;
    private ClientInputGUI clientInputGUI = null;
    //public LauncherController launcherController = null;
    private LobbyController lobbyController = null;
    private PickGodsController pickGodsController = null;
    private SantoriniMatchController santoriniMatchController = null;

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

    public void changeToPickGods() throws IOException {
        FXMLLoader loaderPickGod = new FXMLLoader(getClass().getClassLoader().getResource("FXML/PickGods.fxml"));
        Parent rootMatch = (Parent) loaderPickGod.load();
        pickGodsController = loaderPickGod.getController();
        pickGodsController.setClientMain(clientMain);
        pickGodsController.setClientInputGUI(clientInputGUI);
        ((ClientInputGUI) clientMain.getClientInput()).setPickGodsController(pickGodsController);
        pickGodScene = new Scene(rootMatch);
        primaryStage.setScene(pickGodScene);
        primaryStage.setTitle("Match");
    }

    public void showGods(String msg){
        pickGodsController.getDescriptionGod(msg);
    }

    public void changeToSantoriniMatch() throws IOException {
        FXMLLoader loaderSantoriniMatch = new FXMLLoader(getClass().getClassLoader().getResource("FXML/SantoriniMatch.fxml"));
        Parent rootSantoriniMatch = loaderSantoriniMatch.load();
        santoriniMatchController = loaderSantoriniMatch.getController();
        santoriniMatchController.setClientMain(clientMain);
        santoriniMatchController.setClientInputGUI(clientInputGUI);
        ((ClientInputGUI) clientMain.getClientInput()).setSantoriniMatchController(santoriniMatchController);
        santoriniMatchScene = new Scene(rootSantoriniMatch);
        primaryStage.setScene(santoriniMatchScene);
        primaryStage.setTitle("Santorini Board Game");
    }

    public void waitYourTurn(){
        return;
    }
}
