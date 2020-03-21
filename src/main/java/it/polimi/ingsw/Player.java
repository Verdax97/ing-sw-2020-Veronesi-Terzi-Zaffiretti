package it.polimi.ingsw;

public class Player {
    private String nickname;
    private God godPower;
    private boolean Active;

    public God getGodPower() { return godPower; }

    public void setGodPower(God godPower) { this.godPower = godPower; }

    public String getNickname() { return nickname; }

    public void setNickname(String nickname) { this.nickname = nickname; }

    public boolean getActive() { return Active; }

    public void setActive(boolean active) { Active = active; }
}
