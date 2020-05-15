package it.polimi.ingsw.model;

import java.io.Serializable;

public class Player implements Serializable {
    private String nickname;
    private God godPower;
    private boolean Active;

    public Player(String nick) {
        this.nickname = nick;
        this.Active = true;
    }

    public God getGodPower() { return godPower; }

    public void setGodPower(God godPower) { this.godPower = godPower; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public boolean getActive() { return Active; }

    public void setActive(boolean active) { Active = active; }
}
