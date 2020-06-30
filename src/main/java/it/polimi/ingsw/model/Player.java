package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Player
 */
public class Player implements Serializable {

    private String nickname;
    private God godPower;
    private boolean Active;

    /**
     * Instantiates a new Player.
     *
     * @param nick the nick
     */
    public Player(String nick) {
        this.nickname = nick;
        this.Active = true;
    }

    /**
     * Gets god power.
     *
     * @return the god power
     */
    public God getGodPower() { return godPower; }

    /**
     * Sets god power.
     *
     * @param godPower the god power
     */
    public void setGodPower(God godPower) { this.godPower = godPower; }

    /**
     * Gets nickname.
     *
     * @return the nickname
     */
    public String getNickname() { return nickname; }

    /**
     * Sets nickname.
     *
     * @param nickname the nickname
     */
    public void setNickname(String nickname) { this.nickname = nickname; }

    /**
     * Gets active.
     *
     * @return the active
     */
    public boolean getActive() { return Active; }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) { Active = active; }
}
