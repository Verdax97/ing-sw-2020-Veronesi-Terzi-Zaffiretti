package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameSaver;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.MsgToServer;
import it.polimi.ingsw.view.ServerView;
import it.polimi.ingsw.view.server.ServerMultiplexer;

import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

/**
 * Class Controller initialize the match and redirect the messages based on current state
 *
 * @author Davide
 * Created on 20/05/2020
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
     */
    public void CreateMatch(boolean resume) {
        if (resume) {
            try {
                this.match = GameSaver.loadGame();
                serverMultiplexer.ConnectObserver(match);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(-1);
            }
            setState(State.SELECTWORKER);
            match.SelectWorker(new MsgToServer("", -5, -5, -5, -5));
            return;
        }
        this.match = new Match(lobby.getPlayers());
        serverMultiplexer.ConnectObserver(match);
        setState(State.START);
        this.match.StartGame();
    }

    /**
     * Method ParseServerMsg redirect the message from the client to the correct function of match
     *
     * @param msgPacket         of type MsgToServer the message that is needed to make a move
     * @param serverMultiplexer of type ServerMultiplexer
     */
    public void RedirectMessage(MsgToServer msgPacket, ServerMultiplexer serverMultiplexer) {
        if (!msgPacket.nickname.equals(match.getPlayerTurn().getNickname())) {
            //do nothing
            return;
        }
        int ret;
        switch (state) {
            case LOBBY://
                lobby = serverMultiplexer.getLobby();
                CreateMatch(false);
                break;
            case START:
                match.PickGod(msgPacket);
                setState(State.SETUP);
                break;
            case SETUP:
                match.PickGod(msgPacket);
                if (match.getSetup().getGodPicked().size() == lobby.getnPlayer())
                    setState(State.SELECT);
                break;
            case SELECT://select player god
                match.SelectPlayerGod(msgPacket);
                if (match.getSetup().getGodPicked().size() == 0)
                    setState(State.PLACEWORKERS);
                break;
            case PLACEWORKERS:
                match.PlaceWorker(msgPacket);
                if (match.getLastAction() == 2)
                    setState(State.STARTTURN);
                break;
            case STARTTURN://check startTurn options
                match.StartTurn();
                ret = match.getLastAction();
                if (ret == 0)
                    setState(State.SELECTWORKER);
                else if (ret == 1)
                    setState(State.ENDMATCH);
                else if (ret == -1)
                    setState(State.STARTTURN);
                break;
            case SELECTWORKER:
                match.SelectWorker(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    setState(State.BEFOREMOVE);
                else if (ret == 2)
                    setState(State.MOVE);
                break;
            case BEFOREMOVE:
                match.BeforeMove(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    setState(State.MOVE);
                break;
            case MOVE:
                match.Move(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    setState(State.BUILD);
                else if (ret == 10)
                    setState(State.ENDMATCH);
                else if (ret == -10)
                    setState(State.STARTTURN);
                break;
            case BUILD:
                match.Build(msgPacket);
                ret = match.getLastAction();
                if (ret == 1 || ret == -10)
                    setState(State.STARTTURN);
                if (ret == 10)
                    setState(State.ENDMATCH);
                break;
            case ENDMATCH://we have a winner winner chicken dinner
                //TODO all this thing
                //save record data
                //delete game data ()
                break;
            default:
                System.out.println("Error of received message");
                match.CreateMsgPacket(match.getMsgError(), "Wait");
                break;
        }
        new ServerView().PrintBoard(match.getBoard(), match);
    }

    /**
     * Method setState sets the state of this Controller object.
     *
     * @param state the state of this Controller object.
     */
    private void setState(State state) {
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
        RedirectMessage((MsgToServer) arg, (ServerMultiplexer) o);
    }
}
