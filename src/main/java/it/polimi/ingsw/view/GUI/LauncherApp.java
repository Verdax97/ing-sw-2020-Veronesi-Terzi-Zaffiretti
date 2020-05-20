package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LauncherApp extends Application implements EventHandler<CustomEvent> {

    Scene connectionScene, lobbyScene, matchScene;

    Button buttonConnect;

    ClientMain clientMain = new ClientMain();

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Button button1 = new Button("button");
        button1.setOnAction(e -> primaryStage.setScene(lobbyScene));
        VBox layout1 = new VBox(20);
        layout1.getChildren().add(button1);
        connectionScene = new Scene(layout1, 200, 200);
        primaryStage.setScene(connectionScene);

        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        Parent root = (Parent) loader.load();
        LauncherController launcherController = loader.getController();
        launcherController.setClientMain(clientMain);
        lobbyScene = new Scene(root);
        buttonConnect = (Button) lobbyScene.lookup("#connect");
        buttonConnect.setOnAction(e ->
        {
            if (launcherController.connection())
                primaryStage.setScene(connectionScene);
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
