package it.polimi.ingsw.view.GUI;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LauncherApp extends Application implements EventHandler<CustomEvent> {

    Scene scene1, scene2;

    private static Stage primaryStage;

    static Stage getPrimaryStage(){
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Button button1 = new Button("button");
        button1.setOnAction(e -> primaryStage.setScene(scene2));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXML/Launcher.fxml"));
        VBox layout1 = new VBox(20);
        layout1.getChildren().add(button1);
        scene1 = new Scene(layout1, 200, 200);
        primaryStage.setScene(scene1);
        scene2 = new Scene(root);
        scene2.addEventHandler(new EventType<CustomEvent>(), this);
        //stage.setScene(scene2);
        stage.setTitle("Santorini Game Launcher");
        stage.show();
    }

    @Override
    public void handle(CustomEvent customEvent) {
    }

    @Override
    public void stop(){
        System.exit(0);
    }

    public static void main(String[] args){
        Application.launch(LauncherApp.class, args);
    }

    public void onConnection() {
        primaryStage.setScene(scene1);
    }
}
