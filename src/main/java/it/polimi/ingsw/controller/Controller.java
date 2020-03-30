package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.Match;
import it.polimi.ingsw.model.Player;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Lobby lobby;
    private Player playerTurn;
    private Match match;

    public void getLobby(Lobby lobby){
            this.lobby = new Lobby();
    }

    public void createMatch(List players){}

    public Player killPlayer(Player player){}

    public void update (Observable o, Object arg){}

}
