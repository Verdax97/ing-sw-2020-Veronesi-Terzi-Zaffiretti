package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.view.GUI.ControllerGUI;
import it.polimi.ingsw.view.GUI.LobbyController;
import it.polimi.ingsw.view.GUI.PickGodsController;
import it.polimi.ingsw.view.GUI.SantoriniMatchController;
import javafx.application.Platform;

import java.io.IOException;

public class ClientInputGUI extends ClientInput {

    private ControllerGUI controllerGui = null;
    private LobbyController lobbyController = null;
    private PickGodsController pickGodsController = null;

    public SantoriniMatchController getSantoriniMatchController() {
        return santoriniMatchController;
    }

    public void setSantoriniMatchController(SantoriniMatchController santoriniMatchController) {
        this.santoriniMatchController = santoriniMatchController;
    }

    private SantoriniMatchController santoriniMatchController = null;

    public ControllerGUI getControllerGui() { return controllerGui; }

    public void setControllerGui(ControllerGUI controllerGui) { this.controllerGui = controllerGui; }

    public LobbyController getLobbyController() { return lobbyController; }

    public void setLobbyController(LobbyController lobbyController) { this.lobbyController = lobbyController; }

    public PickGodsController getPickGodsController() { return pickGodsController; }

    public void setPickGodsController(PickGodsController pickGodsController) { this.pickGodsController = pickGodsController; }

    public ClientInputGUI(ClientMain clientMain) { super(clientMain); }

    @Override
    public void ParseMsg(MsgPacket msgPacket) {

        String msg = msgPacket.msg;

        if (msg.split(" ")[0].equalsIgnoreCase(Messages.error)) {
            String errorMsg = msg.split("\n", 2)[0];
            msg = msg.split("\n", 2)[1];
            Platform.runLater(()-> {
                controllerGui.error("Error", errorMsg);
            });
        }

        if (msg.equalsIgnoreCase(Messages.lobby)) {
            Platform.runLater(()-> {
            try {
                    controllerGui.changeToLobby(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.nickname)) {
            Platform.runLater(()-> {
                try {
                    controllerGui.changeToLobby(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.start)) {
            Platform.runLater(()-> {
                try {
                    controllerGui.changeToPickGods();
                    Reply(-5, -5, -5, -5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.resume)) {
            controllerGui.resume();
        }

        if (msg.equalsIgnoreCase(Messages.choseGods) || msg.equalsIgnoreCase(Messages.choseYourGod)) {
            Platform.runLater(() -> {
                controllerGui.showGods(msgPacket.altMsg, true);
            });
        }

        if (msg.equalsIgnoreCase(Messages.waitTurn)){
            controllerGui.waitYourTurn();
            Reply(-5, -5, -5, -5);
        }

        if (msg.equalsIgnoreCase(Messages.placeWorkers)) {
            Platform.runLater(()-> {
                try {
                    controllerGui.changeToSantoriniMatch(msgPacket.board);
                    controllerGui.itIsYourTurn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.startTurn)) {
            controllerGui.itIsYourTurn();
            Reply(-5, -5, -5, -5);
            return;
        }

        if (msg.equalsIgnoreCase(Messages.selectWorker)) {
            controllerGui.selectWorker();
        }

        if (msg.equalsIgnoreCase(Messages.beforeMove)) {
            controllerGui.beforeMovePower();
        }

        if (msg.equalsIgnoreCase(Messages.moveAgain)) {
            controllerGui.moveAgain();
        }

        if (msg.equalsIgnoreCase(Messages.move)) {
            controllerGui.move();
        }

        if (msg.equalsIgnoreCase(Messages.buildAgain)) {
            controllerGui.buildAgain();
        }

        if (msg.equalsIgnoreCase(Messages.build)) {
            boolean atlas = false;
            int i;
            for (i = 0; i < msgPacket.board.players.size(); i++) {
                if (clientMain.getNick().equals(msgPacket.board.players.get(i))){
                    break;
                }
            }
            if (msgPacket.board.gods.get(i).getName().equalsIgnoreCase("Atlas")) {
                atlas = true;
            }
            controllerGui.build(atlas);
        }
    }

    @Override
    public void updateNotYourTurn(MsgPacket msgPacket) {
        String msg = msgPacket.msg;

        if (msg.split(" ")[0].equalsIgnoreCase(Messages.error)) {
            msg = msg.split("\n", 2)[1];
        }

        if (msg.equalsIgnoreCase(Messages.start)) {
            Platform.runLater(() -> {
                try {
                    controllerGui.changeToPickGods();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.choseGods) || msg.equalsIgnoreCase(Messages.choseYourGod)) {
            Platform.runLater(() -> {
                controllerGui.showGods(msgPacket.altMsg, false);
            });
        }

        if (msg.equalsIgnoreCase(Messages.placeWorkers)) {
            Platform.runLater(()-> {
                try {
                    controllerGui.changeToSantoriniMatch(msgPacket.board);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        Reply(-5, -5, -5, -5);
    }
}
