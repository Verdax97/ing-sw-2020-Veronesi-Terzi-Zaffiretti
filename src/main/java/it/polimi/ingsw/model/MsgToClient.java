package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class MsgToClient contains updates of the game state designated to game clients
 */
public class MsgToClient implements Serializable {

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
     * Constructor MsgToClient creates a new MsgToClient instance.
     *
     * @param nick   the nick
     * @param msg    the msg
     * @param altMsg the alt msg
     * @param board  the board
     */
    public MsgToClient(String nick, String msg, String altMsg, SimpleBoard board) {
        this.nickname = nick;
        this.altMsg = altMsg;
        this.msg = msg;
        this.board = board;
    }
}
