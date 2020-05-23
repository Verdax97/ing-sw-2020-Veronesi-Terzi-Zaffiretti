package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.view.GUI.LauncherApp;
import it.polimi.ingsw.view.GUI.LobbyController;
import it.polimi.ingsw.view.GUI.MatchController;
import javafx.scene.control.Alert;

public class ClientInputGUI extends ClientInput {

    private LauncherApp launcherApp;
    private LobbyController lobbyController;
    private MatchController matchController;

    public LauncherApp getLauncherApp() { return launcherApp; }

    public void setLauncherApp(LauncherApp launcherApp) { this.launcherApp = launcherApp; }

    public LobbyController getLobbyController() {
        return lobbyController;
    }

    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    public MatchController getMatchController() {
        return matchController;
    }

    public void setMatchController(MatchController matchController) {
        this.matchController = matchController;
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
            lobbyController.showNumberPlayers();
        }

        if (msg.equalsIgnoreCase(Messages.nickname)) {
            lobbyController.showNicknames();
        }

        if (msg.equalsIgnoreCase(Messages.choseGods) || msg.equalsIgnoreCase(Messages.choseYourGod)) {
            matchController.getDescriptionGod(msgPacket.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.waitTurn)){
            lobbyController.validNickname();
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
