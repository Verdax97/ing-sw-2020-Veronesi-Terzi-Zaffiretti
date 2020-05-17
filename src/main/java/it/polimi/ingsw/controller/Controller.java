package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.server.ServerMultiplexer;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Lobby lobby;
    private Match match;
    private State state = State.LOBBY;
    private ServerMultiplexer serverMultiplexer;

    public void setServerMultiplexer(ServerMultiplexer serverMultiplexer) {
        this.serverMultiplexer = serverMultiplexer;
        serverMultiplexer.addObserver(this);
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public void CreateMatch() {
        this.match = new Match(lobby.getPlayers());
        serverMultiplexer.ConnectObserver(match);
        this.match.StartGame();
        UpdateStatus(State.SETUP);
    }

    public void ParseServerMsg (MsgToServer msgPacket, ServerMultiplexer serverMultiplexer)
    {
        if (!msgPacket.nickname.equals(match.getPlayerTurn().getNickname()))
        {
            //do nothing
            return;
        }
        int ret;
        switch (state)
        {
            case LOBBY://
                lobby = serverMultiplexer.getLobby();
                CreateMatch();
                break;
            case SETUP:
                match.PickGod(msgPacket);
                if (match.getSetup().getGodPicked().size() == lobby.getnPlayer())
                    UpdateStatus(State.SELECT);
                break;
            case SELECT://select player god
                match.SelectPlayerGod(msgPacket);
                if (match.getSetup().getGodPicked().size() == 0)
                    UpdateStatus(State.PLACEWORKERS);
                break;
            case PLACEWORKERS:
                match.PlaceWorker(msgPacket);
                if (match.getLastAction() == 2)
                    UpdateStatus(State.STARTTURN);
                break;
            case STARTTURN://check startTurn options
                match.StartTurn();
                ret = match.getLastAction();
                if (ret == 0)
                    UpdateStatus(State.BEFOREMOVE);
                else if (ret == 1)
                    UpdateStatus(State.ENDMATCH);
                else if (ret == -1)
                    UpdateStatus(State.STARTTURN);
                break;
            case BEFOREMOVE:
                match.BeforeMove(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    UpdateStatus(State.MOVE);
                else if (ret == -1)
                    UpdateStatus(State.STARTTURN);
                break;
            case MOVE:
                match.Move(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    UpdateStatus(State.BUILD);
                else if (ret == 10)
                    UpdateStatus(State.ENDMATCH);
                else if (ret == -10)
                    UpdateStatus(State.STARTTURN);
                break;
            case BUILD:
                match.Build(msgPacket);
                ret = match.getLastAction();
                if (ret == 1 || ret == -10)
                    UpdateStatus(State.STARTTURN);
                if (ret == 10)
                    UpdateStatus(State.ENDMATCH);
                break;
            case ENDMATCH://we have a winner winner chicken dinner
                //TODO all this thing
                //save record data
                //delete game data ()
                //close connections
                break;
            default:
                System.out.println("Error of received message");
                match.CreateMsgPacket(match.getMsgError(), "Wait");
                break;
        }
    }

    private void UpdateStatus(State state)
    {
        this.state = state;
    }


    @Override
    public void update (Observable o, Object arg)
    {
        if (!(o instanceof ServerMultiplexer) || !(arg instanceof MsgToServer))
            throw new IllegalArgumentException();
        ParseServerMsg((MsgToServer) arg, (ServerMultiplexer)o);
    }
}
