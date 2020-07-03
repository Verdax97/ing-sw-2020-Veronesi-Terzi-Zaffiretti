package it.polimi.ingsw.view.client;

import it.polimi.ingsw.model.Messages;
import it.polimi.ingsw.model.MsgToClient;
import it.polimi.ingsw.model.MsgToServer;
import it.polimi.ingsw.model.SimpleBoard;
import it.polimi.ingsw.view.GUI.ControllerGUI;
import javafx.application.Platform;

import java.io.IOException;

/**
 * Class ClientInputGUI receives a message and modify the gui
 */
public class ClientInputGUI extends ClientInput {

    private ControllerGUI controllerGui = null;

    /**
     * Method setControllerGui sets the controllerGui of this ClientInputGUI object.
     *
     * @param controllerGui the controllerGui of this ClientInputGUI object.
     */
    public void setControllerGui(ControllerGUI controllerGui) {
        this.controllerGui = controllerGui;
    }

    /**
     * Constructor ClientInput creates a new ClientInput instance.
     *
     * @param clientMain of type ClientMain
     */
    public ClientInputGUI(ClientMain clientMain) {
        super(clientMain);
    }

    /**
     * @see ClientInput#parseMsg(MsgToClient)
     */
    @Override
    public void parseMsg(MsgToClient msgToClient) {

        String msg = msgToClient.msg;
        if (msg.split(" ")[0].equalsIgnoreCase(Messages.ERROR)) {
            String errorMsg = msg.split("\n", 2)[0];
            msg = msg.split("\n", 2)[1];
            Platform.runLater(() -> controllerGui.error("Error", errorMsg));
        }
        if (msg.split(":", 2)[0].equals("Chat")) {
            receiveChatMsg(msg);
            return;
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
                    reply(-5, -5, -5, -5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.RESUME)) {
            controllerGui.resume();
        }

        if (msg.equalsIgnoreCase(Messages.CHOSE_GODS) || msg.equalsIgnoreCase(Messages.CHOSE_YOUR_GOD)) {
            Platform.runLater(() -> controllerGui.showGods(msgToClient.altMsg, true));
        }

        if (msg.equalsIgnoreCase(Messages.WAIT_TURN)) {
            controllerGui.waitYourTurn();
        }

        if (msg.equalsIgnoreCase(Messages.PLACE_WORKERS)) {
            Platform.runLater(() -> {
                try {
                    controllerGui.changeToSantoriniMatch(msgToClient.board, true, false);
                    controllerGui.itIsYourTurn();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (msg.equalsIgnoreCase(Messages.START_TURN)) {
            controllerGui.itIsYourTurn();
            reply(-5, -5, -5, -5);
            return;
        }

        if (msg.equalsIgnoreCase(Messages.SELECT_WORKER)) {
            if (controllerGui.getSantoriniMatchController() == null) {
                Platform.runLater(() -> {
                    try {
                        controllerGui.changeToSantoriniMatch(msgToClient.board, true, true);
                        controllerGui.selectWorker(msgToClient.altMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else
                controllerGui.selectWorker(msgToClient.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.BEFORE_MOVE)) {
            controllerGui.beforeMovePower(msgToClient.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.MOVE_AGAIN)) {
            controllerGui.moveAgain(msgToClient.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.MOVE)) {
            controllerGui.move(msgToClient.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.BUILD_AGAIN)) {
            controllerGui.buildAgain(msgToClient.altMsg);
        }

        if (msg.equalsIgnoreCase(Messages.BUILD)) {
            boolean atlas = false;
            int i;
            for (i = 0; i < msgToClient.board.players.size(); i++) {
                if (clientMain.getNick().equals(msgToClient.board.players.get(i))) {
                    break;
                }
            }
            if (msgToClient.board.gods.get(i).getName().equalsIgnoreCase("Atlas")) {
                atlas = true;
            }
            controllerGui.build(msgToClient.altMsg, atlas);
        }

        controllerGui.activePlayer(msgToClient.board, msgToClient.nickname);
    }

    /**
     * @see ClientInput#updateNotYourTurn(MsgToClient)
     */
    @Override
    public void updateNotYourTurn(MsgToClient msgToClient) {
        String msg = msgToClient.msg;
        if (msg.split(":", 2)[0].equals("Chat")) {
            receiveChatMsg(msg);
            return;
        }
        controllerGui.activePlayer(msgToClient.board, msgToClient.nickname);
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
            Platform.runLater(() -> controllerGui.showGods(msgToClient.altMsg, false));
        }

        if (msg.equalsIgnoreCase(Messages.PLACE_WORKERS)) {
            Platform.runLater(() -> {
                try {
                    controllerGui.changeToSantoriniMatch(msgToClient.board, false, false);
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
                        controllerGui.changeToSantoriniMatch(msgToClient.board, false, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }


        controllerGui.waitYourTurn();

        controllerGui.activePlayer(msgToClient.board, msgToClient.nickname);
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
        Platform.runLater(() -> controllerGui.closePopUp("Game is closing", "Connection closed with the server, closing the game"));
    }

    /**
     * Method sendChatMsg sends the msg to the server
     *
     * @param msg of type String
     */
    public void sendChatMsg(String msg) {
        if (clientMain.isEnding)
            return;
        setChanged();
        notifyObservers(new MsgToServer("Chat:" + clientMain.getNick() + ":" + msg, -5, -5, -5, -5));
    }

    /**
     * Method receiveChatMsg pass the chat message received to the controller gui
     *
     * @param msg of type String
     */
    public void receiveChatMsg(String msg) {
        controllerGui.receiveChatMessage(msg);
    }
}
