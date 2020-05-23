package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LauncherApp extends Application{

    Scene connectionScene, lobbyScene, matchScene;

    Button buttonConnect, buttonLobby;

    ClientMain clientMain = new ClientMain();

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        //window on which each other window will be load
        primaryStage = stage;
        //loading .fxml on different loader for further use
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        FXMLLoader loaderLobby = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
        FXMLLoader loaderMatch = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Match.fxml"));
        //each window gets his data
        Parent root = (Parent) loader.load();
        Parent rootLobby = (Parent) loaderLobby.load();
        Parent rootMatch = (Parent) loaderMatch.load();
        //set each controller to his own window
        LauncherController launcherController = loader.getController();
        LobbyController lobbyController = loaderLobby.getController();
        MatchController matchController = loaderMatch.getController();
        //set client main to each window
        launcherController.setClientMain(clientMain);
        lobbyController.setClientMain(clientMain);
        matchController.setClientMain(clientMain);

        clientMain.setClientInput((ClientInput) new ClientInputGUI(clientMain));
        //set client input to each window
        lobbyController.setClientInputGUI((ClientInputGUI) clientMain.getClientInput());
        matchController.setClientInputGUI((ClientInputGUI) clientMain.getClientInput());
        //set different scenes
        connectionScene = new Scene(root);
        lobbyScene = new Scene(rootLobby);
        matchScene = new Scene(rootMatch);
        //set first scene shown to user
        primaryStage.setScene(connectionScene);
        //set on button window change
        buttonConnect = (Button) connectionScene.lookup("#connect");
        buttonLobby = (Button) lobbyScene.lookup("#lobby");
        //on connect method execution if return true changes window to Lobby, connection is established
        buttonConnect.setOnAction(e ->
        {
            if (launcherController.connection()){
                primaryStage.setScene(lobbyScene);
                primaryStage.setTitle("Lobby");
                //on lobby method execution if return true changes window to Santorini Match, nickname is set
                buttonLobby.setOnAction(f ->
                {
                    if (lobbyController.lobby())
                        primaryStage.setScene(matchScene);
                        primaryStage.setTitle("Santorini Match");
                });
            }
        });
        //show first scene
        stage.setTitle("Santorini Game Launcher");
        stage.show();
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
}
