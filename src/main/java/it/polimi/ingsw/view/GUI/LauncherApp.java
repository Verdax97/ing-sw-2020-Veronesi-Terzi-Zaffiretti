package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class LauncherApp stars the GUI process
 */
public class LauncherApp extends Application{

    /**
     * The Controller gui.
     */
    ControllerGUI controllerGUI = new ControllerGUI();

    /**
     * The Client main.
     */
    ClientMain clientMain = new ClientMain();

    /**
     * The constant primaryStage.
     */
    public static Stage primaryStage;

    /**
     * Method start used to start the gui
     *
     * @param stage of type Stage
     * @throws Exception when
     */
    @Override
    public void start(Stage stage) throws Exception {
        //set first window
        primaryStage = stage;
        controllerGUI.setPrimaryStage(primaryStage);
        controllerGUI.setClientMain(clientMain);
        clientMain.setClientInput(new ClientInputGUI(clientMain));
        controllerGUI.setClientInputGUI((ClientInputGUI) clientMain.getClientInput());
        ((ClientInputGUI) clientMain.getClientInput()).setControllerGui(controllerGUI);
        controllerGUI.getFirstWindow();
        stage.setTitle("Santorini Game Launcher");
        stage.setOnCloseRequest((e) -> System.exit(1));
        stage.show();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {
            Application.launch(LauncherApp.class, args);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
