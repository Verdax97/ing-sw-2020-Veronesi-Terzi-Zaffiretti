package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Lobby lobby;
    private Player playerTurn;
    private Match match;

    public void CreateLobby(Lobby lobby){
            this.lobby = new Lobby();
    }

    public void AddPlayerLobby(String player)
    {
        this.lobby.AddPlayer(player);
    }

    public void CreateMatch()
    {
        this.match = new Match(lobby.getPlayers());
        this.playerTurn = match.getPlayers().get(0);
    }

    public void killPlayer(Player player){}

    public void ParseServerMsg (String msgView)
    {}

    @Override
    public void update (Observable o, Object arg){}

}
