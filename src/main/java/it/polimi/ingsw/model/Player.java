package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class Player contains all the info about the player
 */
public class Player implements Serializable {

    private String nickname;
    private God godPower;
    private boolean Active;

    /**
     * Constructor Board creates a new Board instance.
     *
     * @param nick of type String
     */
    public Player(String nick) {
        this.nickname = nick;
        this.Active = true;
    }

    /**
     * Method getGodPower return the godPower of this Player object.
     *
     * @return godPower of type God
     */
    public God getGodPower() { return godPower; }

    /**
     * Method setGodPower sets the godPower of this Player object.
     *
     * @param godPower of type God
     */
    public void setGodPower(God godPower) { this.godPower = godPower; }

    /**
     * Method getNickname returns the nickname of this Player object.
     *
     * @return nickname of type String
     */
    public String getNickname() { return nickname; }

    /**
     * Method setNickname sets the nickname of this Player object.
     *
     * @param nickname of type String
     */
    public void setNickname(String nickname) { this.nickname = nickname; }

    /**
     * Method getActive returns the Active of this Player object.
     *
     * @return Active of type boolean
     */
    public boolean getActive() { return Active; }

    /**
     * Method setActive sets the Active of this Player object.
     *
     * @param active of type boolean
     */
    public void setActive(boolean active) { Active = active; }
}
