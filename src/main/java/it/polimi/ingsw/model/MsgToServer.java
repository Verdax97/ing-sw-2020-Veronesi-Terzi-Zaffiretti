package it.polimi.ingsw.model;

import java.io.Serializable;

public class MsgToServer implements Serializable {
    public final String nickname;
    public final int x, y, targetX, targetY;

    public MsgToServer(String nickname, int x, int y, int targetX, int targetY) {
        this.nickname = nickname;
        this.x = x;
        this.y = y;
        this.targetX = targetX;
        this.targetY = targetY;
    }
}
