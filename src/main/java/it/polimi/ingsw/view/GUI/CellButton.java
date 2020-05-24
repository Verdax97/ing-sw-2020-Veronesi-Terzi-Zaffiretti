package it.polimi.ingsw.view.GUI;

import javafx.scene.control.Button;

public class CellButton extends Button {

    public int getValReply() {
        return valReply;
    }

    public void setValReply(int valReply) {
        this.valReply = valReply;
    }

    private int valReply = -5;
}
