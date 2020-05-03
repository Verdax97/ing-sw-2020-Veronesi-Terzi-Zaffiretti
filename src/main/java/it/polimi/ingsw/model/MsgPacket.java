package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MsgPacket implements Serializable
{
    public ArrayList<Player> players;
    public String nickname;
    public String msg;
    public String altMsg;
    public Board board;
    public MsgPacket(String nick, String msg, String altMsg, Board board, ArrayList<Player> players)
    {
        this.nickname = nick;
        this.altMsg = altMsg;
        this.msg = msg;
        this.board = board;
        this.players = players;
    }
}
