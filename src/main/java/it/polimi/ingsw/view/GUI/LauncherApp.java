package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LauncherApp extends Application implements EventHandler<CustomEvent> {

    Scene connectionScene, lobbyScene, matchScene;

    Button buttonConnect;

    ClientMain clientMain = new ClientMain();

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        FXMLLoader loaderLobby = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
        FXMLLoader loaderMatch = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Match.fxml"));
        Parent root = (Parent) loader.load();
        Parent rootLobby = (Parent) loaderLobby.load();
        Parent rootMatch = (Parent) loaderMatch.load();
        LauncherController launcherController = loader.getController();
        LobbyController lobbyController = loaderLobby.getController();
        MatchController matchcontroller = loaderMatch.getController();
        launcherController.setClientMain(clientMain);
        connectionScene = new Scene(root);
        lobbyScene = new Scene(rootLobby);
        primaryStage.setScene(connectionScene);
        buttonConnect = (Button) connectionScene.lookup("#connect");
        buttonConnect.setOnAction(e ->
        {
            if (launcherController.connection())
                primaryStage.setScene(lobbyScene);
                primaryStage.setTitle("Lobby");
        });
        stage.setTitle("Santorini Game Launcher");
        stage.show();
    }

    @Override
    public void handle(CustomEvent event) {
        event.invokeHandler(this);
        event.consume();
    }

    @Override
    public void stop(){
        System.exit(0);
    }

    public static void main(String[] args) {
        try {
            Application.launch(LauncherApp.class, args);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void onConnection() {
        primaryStage.setScene(connectionScene);
    }
}
