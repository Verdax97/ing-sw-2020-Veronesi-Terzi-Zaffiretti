package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameSaver;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MsgToServer;
import it.polimi.ingsw.view.server.ServerMultiplexer;

import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

/**
 * Class Controller initialize the match and redirect the messages based on current state
 */
public class Controller implements Observer {

    private Lobby lobby;
    private Match match;
    private State state = State.LOBBY;
    private ServerMultiplexer serverMultiplexer;

    /**
     * Method setServerMultiplexer sets the serverMultiplexer of this Controller object.
     *
     * @param serverMultiplexer the serverMultiplexer of this Controller object.
     */
    public void setServerMultiplexer(ServerMultiplexer serverMultiplexer) {
        this.serverMultiplexer = serverMultiplexer;
        serverMultiplexer.addObserver(this);
    }

    /**
     * Method setLobby sets the lobby of this Controller object.
     *
     * @param lobby the lobby of this Controller object.
     */
    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    /**
     * Method CreateMatch instantiate and initialize match and send first message to the players
     *
     * @param resume the resume
     */
    public void createMatch(boolean resume) {
        if (resume) {
            try {
                this.match = GameSaver.loadGame();
                serverMultiplexer.connectObservers(match);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(-1);
            }
            setState(State.SELECTWORKER);
            match.StartTurn();
            return;
        }
        this.match = new Match(lobby.getPlayers());
        serverMultiplexer.connectObservers(match);
        setState(State.START);
        this.match.StartGame();
    }

    /**
     * Method ParseServerMsg redirect the message from the client to the correct function of match
     *
     * @param msgPacket         of type MsgToServer the message that is needed to make a move
     * @param serverMultiplexer of type ServerMultiplexer
     */
    public void redirectMessage(MsgToServer msgPacket, ServerMultiplexer serverMultiplexer) {
        if (!msgPacket.nickname.equals(match.getPlayerTurn().getNickname())) {
            //do nothing
            return;
        }
        int ret;
        switch (state) {
//
            case START -> {
                match.PickGod(msgPacket);
                setState(State.SETUP);
            }
            case SETUP -> {
                match.PickGod(msgPacket);
                if (match.getSetup().getGodPicked().size() == lobby.getnPlayer())
                    setState(State.SELECT);
            }
            case SELECT -> {
                //select player god
                match.SelectPlayerGod(msgPacket);
                if (match.getSetup().getGodPicked().size() == 0)
                    setState(State.PLACEWORKERS);
            }
            case PLACEWORKERS -> {
                match.PlaceWorker(msgPacket);
                if (match.getLastAction() == 2)
                    setState(State.STARTTURN);
            }
            case STARTTURN -> {
                //check startTurn options
                match.StartTurn();
                ret = match.getLastAction();
                if (ret == 0)
                    setState(State.SELECTWORKER);
                else if (ret == 1)
                    setState(State.ENDMATCH);
            }
            case SELECTWORKER -> {
                match.SelectWorker(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    setState(State.BEFOREMOVE);
                else if (ret == 2)
                    setState(State.MOVE);
            }
            case BEFOREMOVE -> {
                match.BeforeMove(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    setState(State.MOVE);
            }
            case MOVE -> {
                match.Move(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    setState(State.BUILD);
                else if (ret == 10)
                    setState(State.ENDMATCH);
                else if (ret == -10)
                    setState(State.STARTTURN);
            }
            case BUILD -> {
                match.Build(msgPacket);
                ret = match.getLastAction();
                if (ret == 1 || ret == -10)
                    setState(State.STARTTURN);
                if (ret == 10)
                    setState(State.ENDMATCH);
            }
            case ENDMATCH -> {
                //we have a winner winner chicken dinner
                //delete game data
                GameSaver.deleteGameData();
                System.out.println("Player " + match.getPlayerTurn().getNickname() + " won!!!");
                System.out.println("Shutdown server");
                serverMultiplexer.closeConnections();
            }
            default -> {
                System.out.println("Error of received message");
                match.CreateMsgPacket(match.getMsgError(), "Wait");
            }
        }
    }

    /**
     * Method setState sets the state of this Controller object.
     *
     * @param state the state of this Controller object.
     */
    public void setState(State state) {
        this.state = state;
    }


    /**
     * Method update when receives a message calls the RedirectMessage function
     *
     * @param o   of type Observable
     * @param arg of type Object
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof ServerMultiplexer) || !(arg instanceof MsgToServer))
            throw new IllegalArgumentException();
        redirectMessage((MsgToServer) arg, (ServerMultiplexer) o);
    }
}
