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

        if (msg.equalsIgnoreCase(Messages.choseGods) || msg.equalsIgnoreCase(Messages.choseYourGod)) {
            Platform.runLater(() -> {
                controllerGui.showGods(msgPacket.altMsg);
            });
        }

        if (msg.equalsIgnoreCase(Messages.waitTurn)){
            controllerGui.waitYourTurn();
            Reply(-5, -5, -5, -5);
        }

        if (msg.equalsIgnoreCase(Messages.placeWorkers)) {
            /*System.out.println("Place your workers.");
            for (int i = 0; i < 2; i++) {
                System.out.println("Place worker " + i + ":");
                while (true) {
                    int[] worker = SelectCell();
                    if (worker[0] >= 0 && worker[0] < 5 && worker[1] >= 0 && worker[1] < 5) {
                        arr[2 * i] = worker[0];
                        arr[2 * i + 1] = worker[1];
                        break;
                    } else {
                        System.out.println("Selected cell is not valid");
                    }
                }
            }*/
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
            /*System.out.println("You have the possibility to make an action before the move phase.\nAll the possible actions:");
            System.out.println(msgPacket.altMsg);
            if (Confirm("Do you want to do it? (y/n)")) {
                arr[1] = 1;
                System.out.println("Insert value");
                arr[0] = ReadIntInput();
            } else {
                arr[1] = 0;
            }
            System.out.println("Insert a valid input");*/
        }

        if (msg.equalsIgnoreCase(Messages.moveAgain)) {
            /*System.out.println("You have the possibility to make another move phase.");
            if (Confirm("Do you want to do it? (y/n)")) {
                arr[1] = 1;
                msg = Messages.build;//to make another move action
            } else {
                arr[1] = 0;
            }*/
        }

        if (msg.equalsIgnoreCase(Messages.move)) {
            /*System.out.println("You must move.\nAll the possible moves your worker can do");
            System.out.println(msgPacket.altMsg);
            arr[0] = ReadIntInput();*/
        }

        if (msg.equalsIgnoreCase(Messages.buildAgain)) {
            /*System.out.println("You have the possibility to make another build phase.");
            if (Confirm("Do you want to do it? (y/n)")) {
                arr[1] = 1;
                msg = Messages.build;//to make another move action
            } else {
                arr[1] = 0;
            }*/
        }

        if (msg.equalsIgnoreCase(Messages.build)) {
            /*System.out.println("You must build.\nAll the possible build your worker can do");
            System.out.println(msgPacket.altMsg);
            arr[0] = ReadIntInput();

            int i;
            for (i = 0; i < msgPacket.board.players.size(); i++) {
                if (clientMain.getNick().equals(msgPacket.board.players.get(i)))
                    break;
            }
            if (msgPacket.board.gods.get(i).getName().equalsIgnoreCase("Atlas")) {
                System.out.println("You have the power to build a dome here");
                if (Confirm("Do you want to do it? (y/n)")) {
                    arr[2] = 1;
                }
            }*/
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
                controllerGui.showGods(msgPacket.altMsg);
            });
        }

        Reply(-5, -5, -5, -5);
    }
}
