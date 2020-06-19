package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgPacket;
import it.polimi.ingsw.model.SimpleBoard;
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
        if (msg.split(" ")[0].equalsIgnoreCase(Messages.ERROR)) {
            String errorMsg = msg.split("\n", 2)[0];
            msg = msg.split("\n", 2)[1];
            Platform.runLater(() -> controllerGui.error("Error", errorMsg));
        }

        if (msg.equalsIgnoreCase(Messages.LOBBY)) {
            Platform.runLater(() -> {
                try {
                    controllerGui.changeToLobby(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.INSERT_NICKNAME)) {
            Platform.runLater(() -> {
                try {
                    controllerGui.changeToLobby(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.START)) {
            Platform.runLater(() -> {
                try {
                    controllerGui.changeToPickGods();
                    Reply(-5, -5, -5, -5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.RESUME)) {
            controllerGui.resume();
        }

        if (msg.equalsIgnoreCase(Messages.CHOSE_GODS) || msg.equalsIgnoreCase(Messages.CHOSE_YOUR_GOD)) {
            Platform.runLater(() -> controllerGui.showGods(msgPacket.altMsg, true));
        }

        if (msg.equalsIgnoreCase(Messages.WAIT_TURN)) {
            controllerGui.waitYourTurn();
        }

        if (msg.equalsIgnoreCase(Messages.PLACE_WORKERS)) {
            Platform.runLater(() -> {
                try {
                    controllerGui.changeToSantoriniMatch(msgPacket.board, true, false);
                    controllerGui.itIsYourTurn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.START_TURN)) {
            controllerGui.itIsYourTurn();
            if (msgPacket.board.players.size() > 1)
                Reply(-5, -5, -5, -5);
            return;
        }

        if (msg.equalsIgnoreCase(Messages.SELECT_WORKER)) {
            if (controllerGui.getSantoriniMatchController() == null) {
                Platform.runLater(() -> {
                    try {
                        controllerGui.changeToSantoriniMatch(msgPacket.board, true, true);
                        controllerGui.selectWorker(msgPacket.altMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else
                controllerGui.selectWorker(msgPacket.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.BEFORE_MOVE)) {
            controllerGui.beforeMovePower(msgPacket.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.MOVE_AGAIN)) {
            controllerGui.moveAgain(msgPacket.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.MOVE)) {
            controllerGui.move(msgPacket.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.BUILD_AGAIN)) {
            controllerGui.buildAgain(msgPacket.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.BUILD)) {
            boolean atlas = false;
            int i;
            for (i = 0; i < msgPacket.board.players.size(); i++) {
                if (clientMain.getNick().equals(msgPacket.board.players.get(i))) {
                    break;
                }
            }
            if (msgPacket.board.gods.get(i).getName().equalsIgnoreCase("Atlas")) {
                atlas = true;
            }
            controllerGui.build(msgPacket.altMsg, atlas);
        }

        controllerGui.activePlayer(msgPacket.board, msgPacket.nickname);
    }

    /** @see ClientInput#updateNotYourTurn(MsgPacket) */
    @Override
    public void updateNotYourTurn(MsgPacket msgPacket) {
        String msg = msgPacket.msg;
        controllerGui.activePlayer(msgPacket.board, msgPacket.nickname);
        if (msg.split(" ")[0].equalsIgnoreCase(Messages.ERROR)) {
            msg = msg.split("\n", 2)[1];
        }

        if (msg.equalsIgnoreCase(Messages.START)) {
            Platform.runLater(() -> {
                try {
                    controllerGui.changeToPickGods();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.CHOSE_GODS) || msg.equalsIgnoreCase(Messages.CHOSE_YOUR_GOD)) {
            Platform.runLater(() -> controllerGui.showGods(msgPacket.altMsg, false));
        }

        if (msg.equalsIgnoreCase(Messages.PLACE_WORKERS)) {
            Platform.runLater(() -> {
                try {
                    controllerGui.changeToSantoriniMatch(msgPacket.board, false, false);
                    controllerGui.waitYourTurn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.SELECT_WORKER)) {
            if (controllerGui.getSantoriniMatchController() == null) {
                Platform.runLater(() -> {
                    try {
                        controllerGui.changeToSantoriniMatch(msgPacket.board, false, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        controllerGui.waitYourTurn();


        //Reply(-5, -5, -5, -5);

        controllerGui.activePlayer(msgPacket.board, msgPacket.nickname);
    }

    /**
     * @see ClientInput#printBoard(SimpleBoard)
     */
    @Override
    public void printBoard(SimpleBoard board) {
        Platform.runLater(() -> controllerGui.receiveUpdate(board));
    }

    /**
     * @see ClientInput#updateEndGame()
     */
    @Override
    public void updateEndGame() {
        Platform.runLater(() -> controllerGui.endGame(clientMain.getReceivedMsg().altMsg, clientMain.getNick().equals(clientMain.getReceivedMsg().nickname)));
    }


    /**
     * @see ClientInput#closeGame()
     */
    @Override
    public void closeGame() {
        if (clientMain.getReceivedMsg().msg.equals(Messages.END))
            return;
        System.out.println("Closing the game.");
        Platform.runLater(() -> controllerGui.infoPopUp("Game is closing", "Some player has disconnected, closing the game"));
        //System.exit(1);
    }
}
