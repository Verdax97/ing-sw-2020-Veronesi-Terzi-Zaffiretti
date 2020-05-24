package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeWindow{

    Scene connectionScene, lobbyScene, matchScene;
    private ClientMain clientMain = null;
    private ClientInputGUI clientInputGUI = null;

    public ClientMain getClientMain() {
        return clientMain;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void setClientInputGUI(ClientInputGUI clientInputGUI) { this.clientInputGUI = clientInputGUI; }


    public void getFirstWindow() throws IOException {
        FXMLLoader loaderLauncher = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        Parent rootLauncher = (Parent) loaderLauncher.load();
        LauncherController launcherController = loaderLauncher.getController();
        launcherController.setClientMain(clientMain);
        //((ClientInputGUI) clientMain.getClientInput()).setLauncherController(launcherController);
        connectionScene = new Scene(rootLauncher);
        LauncherApp.primaryStage.setScene(connectionScene);
    }

    public void changeToLobby() throws IOException {
        FXMLLoader loaderLobby = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
        Parent rootLobby = (Parent) loaderLobby.load();
        LobbyController lobbyController = loaderLobby.getController();
        lobbyController.setClientMain(clientMain);
        ((ClientInputGUI) clientMain.getClientInput()).setLobbyController(lobbyController);
        lobbyScene = new Scene(rootLobby);
        LauncherApp.primaryStage.setScene(lobbyScene);
        LauncherApp.primaryStage.setTitle("Lobby");
    }

    public void changeToPickGods() throws IOException {
        FXMLLoader loaderMatch = new FXMLLoader(getClass().getClassLoader().getResource("FXML/PickGods.fxml"));
        Parent rootMatch = (Parent) loaderMatch.load();
        PickGodsController pickGodsController = loaderMatch.getController();
        pickGodsController.setClientMain(clientMain);
        ((ClientInputGUI) clientMain.getClientInput()).setPickGodsController(pickGodsController);
        matchScene = new Scene(rootMatch);
        LauncherApp.primaryStage.setScene(matchScene);
        LauncherApp.primaryStage.setTitle("Match");
    }

    public void changeToSantoriniMatch() throws IOException {
        //TODO
    }
}
