package it.polimi.ingsw.view;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class UI extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Santorini Board Game");
        ListView<String> listView = new ListView<>();
        listView.setPrefSize(250, 50);
        ObservableList<String> viewItems =FXCollections.observableArrayList (
                "CLI", "GUI");
        listView.setItems(viewItems);
        Label valueLbl = new Label("Seleziona interfaccia grafica: ");
        listView.getSelectionModel().selectedItemProperty().
                addListener((ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
                    valueLbl.setText(newVal);
                });
        Label label = new Label("Benvenuti in Santorini");
        Button btn = new Button();
        BorderPane panel = new BorderPane();
        panel.setCenter(btn);
        panel.setTop(label);
        panel.setBottom(listView);
        btn.setText("Start");
        btn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                String choiches = listView.getSelectionModel().getSelectedItem();
                if (choiches == "CLI"){
                    //cli starts
                }
                if (choiches == "GUI"){
                    //gui starts
                }
            }
        });
        Pane root = new Pane();
        root.getChildren().add(panel);
        primaryStage.setScene(new Scene(root, 250, 250));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
