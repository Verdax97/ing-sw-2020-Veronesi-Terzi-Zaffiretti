package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.view.GUI.ChangeWindow;
import it.polimi.ingsw.view.GUI.LobbyController;
import it.polimi.ingsw.view.GUI.PickGodsController;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ClientInputGUI extends ClientInput {

    private ChangeWindow changeWindow;

    public ChangeWindow getChangeWindow() { return changeWindow; }

    public void setChangeWindow(ChangeWindow changeWindow) { this.changeWindow = changeWindow; }

    //private LauncherApp launcherApp;
    private LobbyController lobbyController;
    private PickGodsController pickGodsController;

    /*public LauncherApp getLauncherApp() { return launcherApp; }

    public void setLauncherApp(LauncherApp launcherApp) { this.launcherApp = launcherApp; }*/

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public PickGodsController getPickGodsController() {
        return pickGodsController;
    }

    public void setPickGodsController(PickGodsController pickGodsController) {
        this.pickGodsController = pickGodsController;
    }

    public ClientInputGUI(ClientMain clientMain) {
        super(clientMain);
    }

    @Override
    public void ParseMsg(MsgPacket msgPacket) {
        String msg = msgPacket.msg;

        if (msg.split(" ")[0].equalsIgnoreCase(Messages.error)) {
            //launcherApp.error old version
            error("Error", msg.split("\n", 2)[0]);
            //(Colors.ANSI_RED + msg.split("\n", 2)[0] + Colors.ANSI_RESET);
            msg = msg.split("\n", 2)[1];
        }

        if (msg.equalsIgnoreCase(Messages.lobby)) {
            try {
                changeWindow.changeToLobby();
                } catch (IOException e) {
                e.printStackTrace();
            }
            lobbyController.showNumberPlayers();
        }

        if (msg.equalsIgnoreCase(Messages.nickname)) {
            lobbyController.showNicknames();
        }

        if (msg.equalsIgnoreCase(Messages.choseGods) || msg.equalsIgnoreCase(Messages.choseYourGod)) {
            pickGodsController.getDescriptionGod(msgPacket.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.waitTurn)){
            //launcherApp set in this way throws Exception in thread "Thread-6" java.lang.IllegalStateException: Not on FX application thread; currentThread = Thread-6
            //launcherApp.validNickname();
            Reply(-5, -5, -5, -5);
        }
    }

    public void error(String header, String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(content);
        errorAlert.showAndWait();
    }
}
