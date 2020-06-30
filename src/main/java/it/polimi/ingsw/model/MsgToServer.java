package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class MsgToServer.
 */
public class MsgToServer implements Serializable {
    /**
     * The Nickname.
     */
    public final String nickname;
    /**
     * The X.
     */
    public final int x, /**
     * The Y.
     */
    y, /**
     * The Target x.
     */
    targetX, /**
     * The Target y.
     */
    targetY;

    /**
     * Instantiates a new Msg to server.
     *
     * @param nickname the nickname
     * @param x        the x
     * @param y        the y
     * @param targetX  the target x
     * @param targetY  the target y
     */
    public MsgToServer(String nickname, int x, int y, int targetX, int targetY) {
        this.nickname = nickname;
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
    }
}
