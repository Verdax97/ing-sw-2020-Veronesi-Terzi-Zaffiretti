package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInput;
import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Application;
import javafx.stage.Stage;


public class LauncherApp extends Application{

    ChangeWindow changeWindow = new ChangeWindow();

    ClientMain clientMain = new ClientMain();

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        //set first window
        primaryStage = stage;
        changeWindow.setPrimaryStage(primaryStage);
        changeWindow.setClientMain(clientMain);
        clientMain.setClientInput(new ClientInputGUI(clientMain));
        changeWindow.setClientInputGUI((ClientInputGUI) clientMain.getClientInput());
        ((ClientInputGUI) clientMain.getClientInput()).setChangeWindow(changeWindow);
        changeWindow.getFirstWindow();
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
