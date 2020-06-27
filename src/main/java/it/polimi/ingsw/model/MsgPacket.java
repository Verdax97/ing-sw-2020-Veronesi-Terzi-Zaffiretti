package it.polimi.ingsw.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Msg packet.
 */
public class MsgPacket implements Serializable {
    private static final long serialVersionUID = 1234L;
    /**
     * The Nickname.
     */
    public String nickname;
    /**
     * The Msg.
     */
    public String msg;
    /**
     * The Alt msg.
     */
    public String altMsg;
    /**
     * The Board.
     */
    public SimpleBoard board;

    /**
     * Instantiates a new Msg packet.
     *
     * @param nick   the nick
     * @param msg    the msg
     * @param altMsg the alt msg
     * @param board  the board
     */
    public MsgPacket(String nick, String msg, String altMsg, SimpleBoard board) {
        this.nickname = nick;
        this.altMsg = altMsg;
        this.msg = msg;
        this.board = board;
    }
}
