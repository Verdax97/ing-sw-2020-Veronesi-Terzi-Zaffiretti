package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.view.GUI.ControllerGUI;
import it.polimi.ingsw.view.GUI.LobbyController;
import it.polimi.ingsw.view.GUI.PickGodsController;
import it.polimi.ingsw.view.GUI.SantoriniMatchController;
import javafx.application.Platform;

import java.io.IOException;

/**
 * Class ClientInputGUI receives a message and modify the gui
 */
public class ClientInputGUI extends ClientInput {

    private ControllerGUI controllerGui = null;
    private LobbyController lobbyController = null;
    private PickGodsController pickGodsController = null;

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    private String myName;


    /**
     * Method setSantoriniMatchController sets the santoriniMatchController of this ClientInputGUI object.
     *
     *
     *
     * @param santoriniMatchController the santoriniMatchController of this ClientInputGUI object.
     *
     */
    public void setSantoriniMatchController(SantoriniMatchController santoriniMatchController) {
        this.santoriniMatchController = santoriniMatchController;
    }

    private SantoriniMatchController santoriniMatchController = null;

    /**
     * Method setControllerGui sets the controllerGui of this ClientInputGUI object.
     *
     *
     *
     * @param controllerGui the controllerGui of this ClientInputGUI object.
     *
     */
    public void setControllerGui(ControllerGUI controllerGui) {
        this.controllerGui = controllerGui;
    }

    /**
     * Method setLobbyController sets the lobbyController of this ClientInputGUI object.
     *
     *
     *
     * @param lobbyController the lobbyController of this ClientInputGUI object.
     *
     */
    public void setLobbyController(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    /**
     * Method setPickGodsController sets the pickGodsController of this ClientInputGUI object.
     *
     *
     *
     * @param pickGodsController the pickGodsController of this ClientInputGUI object.
     *
     */
    public void setPickGodsController(PickGodsController pickGodsController) {
        this.pickGodsController = pickGodsController;
    }

    /**
     * Constructor ClientInput creates a new ClientInput instance.
     *
     * @param clientMain of type ClientMain
     */
    public ClientInputGUI(ClientMain clientMain) {
        super(clientMain);
    }

    /** @see ClientInput#ParseMsg(MsgPacket) */
    @Override
    public void ParseMsg(MsgPacket msgPacket) {

        String msg = msgPacket.msg;

        if (msg.split(" ")[0].equalsIgnoreCase(Messages.error)) {
            String errorMsg = msg.split("\n", 2)[0];
            msg = msg.split("\n", 2)[1];
            Platform.runLater(() -> controllerGui.error("Error", errorMsg));
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
            Platform.runLater(() -> controllerGui.showGods(msgPacket.altMsg, true));
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

    /** @see ClientInput#updateNotYourTurn(MsgPacket) */
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
            Platform.runLater(() -> controllerGui.showGods(msgPacket.altMsg, false));
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
