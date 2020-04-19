package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.ServerView;

import java.util.Observable;
import java.util.Observer;

public class Controller implements Observer {

    private Lobby lobby;
    private Player playerTurn;
    private Match match;
    private State state;

    /*
    public void CreateLobby(Lobby lobby){
            this.lobby = new Lobby();
    }

    public void AddPlayerLobby(String player)
    {
        this.lobby.AddPlayer(player);
    }
    */

    public void setLobby(Lobby lobby)
    {
        this.lobby = lobby;
    }


    public void CreateMatch()
    {
        this.match = new Match(lobby.getPlayers());
        this.playerTurn = match.getPlayers().get(0);
        this.match.StartMatch();
    }

    //remove player from players list and worker from the board
    public void killPlayer(Player player)
    {
        for (int i = 0; i < match.getPlayers().size(); i++) {
            if (player.getNickname().equals(match.getPlayers().get(i).getNickname()))
            {
                match.getPlayers().remove(i);

                return;
            }
        }
    }

    public void ParseServerMsg (String msgView)
    {
        int workerX, workerY, targetX, targetY, typeBuilding, godPower;
        int[] gods = new int[lobby.getPlayers().size()];
        switch (state)
        {
            case SETUP:
                match.PickGod(Integer.parseInt(msgView.split(" ")[0]));
                break;
            case MOVE://need 5 int
                workerX = Integer.parseInt(msgView.split(" ")[0]);
                workerY = Integer.parseInt(msgView.split(" ")[1]);
                targetX = Integer.parseInt(msgView.split(" ")[2]);
                targetY = Integer.parseInt(msgView.split(" ")[3]);
                godPower = Integer.parseInt(msgView.split(" ")[4]);
                match.Move(workerX, workerY, targetX, targetY, godPower, playerTurn);
                break;
            case BUILD://need 4 int
                targetX = Integer.parseInt(msgView.split(" ")[0]);
                targetY = Integer.parseInt(msgView.split(" ")[1]);
                godPower = Integer.parseInt(msgView.split(" ")[2]);
                typeBuilding = Integer.parseInt(msgView.split(" ")[3]);
                match.Build(targetX, targetY, godPower, typeBuilding, playerTurn);
                break;
            case LOBBY://
                CreateMatch();
                break;
            case SELECT://select all god
                match.SelectPlayerGod(Integer.parseInt(msgView.split(" ")[0]), playerTurn);
                break;
            case ENDMATCH://we have a winner winner chicken dinner
                break;
            case CHECKWINCONDITION://check after move and build
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
        if (!(o instanceof ServerView))
            throw new IllegalArgumentException();
        if (((ServerView) o).getState() == State.LOBBY)
        {
            this.lobby = ((ServerView) o).getLobby();
        }
        UpdateStatus(((ServerView) o).getState());
        ParseServerMsg(((ServerView) o).getMsgIn());
    }
}
