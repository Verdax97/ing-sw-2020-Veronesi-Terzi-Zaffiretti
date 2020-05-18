package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class MsgPacket implements Serializable {
    public String nickname;
    public String msg;
    public String altMsg;
    public SimpleBoard board;

    public MsgPacket(String nick, String msg, String altMsg, SimpleBoard board) {
        this.nickname = nick;
        this.altMsg = altMsg;
        this.msg = msg;
        this.board = board;
    }
}
