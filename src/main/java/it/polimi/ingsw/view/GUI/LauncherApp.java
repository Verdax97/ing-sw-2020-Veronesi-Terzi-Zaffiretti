package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LauncherApp extends Application{

    ChangeWindow changeWindow = new ChangeWindow();
/*
    Scene connectionScene, lobbyScene, matchScene;

    Button buttonConnect, buttonLobby;

    ClientMain clientMain = new ClientMain();
*/
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        /*
        //window on which each other window will be load
        primaryStage = stage;
        //loading .fxml on different loader for further use
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        FXMLLoader loaderLobby = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Lobby.fxml"));
        FXMLLoader loaderMatch = new FXMLLoader(getClass().getClassLoader().getResource("FXML/PickGods.fxml"));
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
        //set client gui input to each window
        ((ClientInputGUI) clientMain.getClientInput()).setLauncherApp(this);
        ((ClientInputGUI) clientMain.getClientInput()).setLobbyController(lobbyController);
        ((ClientInputGUI) clientMain.getClientInput()).setMatchController(matchController);
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
            }
        });
        /*buttonLobby.setOnAction(e ->
        {
            if (lobbyController.lobby()){
                primaryStage.setScene(matchScene);
                primaryStage.setTitle("Match");
            }
        });*/
        //show first scene
        primaryStage = stage;
        Scene firstScene = changeWindow.getFirstWindow();
        primaryStage.setScene(firstScene);
        stage.setTitle("Santorini Game Launcher");
        stage.show();
    }
/*
    public void validNickname(){
        //Exception in thread "Thread-6" java.lang.IllegalStateException: Not on FX application thread; currentThread = Thread-6
        primaryStage.setScene(matchScene);
        primaryStage.setTitle("Match");
    }
*/
    public void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
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
