package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.ServerMultiplexer;
import it.polimi.ingsw.view.ServerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

public class Controller implements Observer {

    private Lobby lobby;
    private Match match;
    private State state = State.LOBBY;

    private ServerMultiplexer server;

    public void setLobby(Lobby lobby)
    {
        this.lobby = lobby;
    }

    public void CreateMatch()
    {
        this.match = new Match(lobby.getPlayers());
        UpdateStatus(State.SETUP);
    }

    public void ParseServerMsg (MsgPacket msgPacket, ServerMultiplexer serverMultiplexer)
    {
        //TODO implement thread for timer undo
        if (!msgPacket.nickname.equals(match.getPlayerTurn().getNickname()))
        {
            //do nothing
            return;
        }
        boolean ok;
        int ret;
        switch (state)
        {
            case LOBBY://
                lobby = serverMultiplexer.lobby;
                CreateMatch();
                break;
            case SETUP:
                match.PickGod(msgPacket);
                ret = match.getLastAction();
                if (ret > 0)
                    if (match.getSetup().getGodPicked().size() == lobby.getnPlayer())
                        UpdateStatus(State.SELECT);
                break;
            case SELECT://select player god
                match.SelectPlayerGod(msgPacket);
                ret = match.getLastAction();
                if (ret > 0)
                    if (match.getSetup().getGodPicked().size() == 0)
                        UpdateStatus(State.PLACEWORKERS);
                break;
            case PLACEWORKERS:
                match.PlaceWorker(msgPacket);
                ret = match.getLastAction();
                if (ret == 2)
                    UpdateStatus(State.STARTTURN);
                break;
            case STARTTURN://check startTurn options
                match.StartTurn(msgPacket);
                ret = match.getLastAction();
                if (ret == 0)
                    UpdateStatus(State.MOVE);
                if (ret == 1)
                    UpdateStatus(State.ENDMATCH);
                if (ret == -1)
                    UpdateStatus(State.STARTTURN);
                break;
            case MOVE:
                match.Move(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    UpdateStatus(State.BUILD);
                if (ret == 10)
                    UpdateStatus(State.ENDMATCH);
                if (ret == -10)
                    UpdateStatus(State.STARTTURN);
                break;
            case BUILD:
                match.Build(msgPacket);
                ret = match.getLastAction();
                if (ret == 1)
                    UpdateStatus(State.STARTTURN);
                if (ret == -10)
                    UpdateStatus(State.STARTTURN);
                break;
            case ENDMATCH://we have a winner winner chicken dinner
                //TODO all this thing
                //delete saved data
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
        if (!(o instanceof ServerMultiplexer))
            throw new IllegalArgumentException();
        ParseServerMsg((MsgPacket)arg, (ServerMultiplexer)o);
    }
}
