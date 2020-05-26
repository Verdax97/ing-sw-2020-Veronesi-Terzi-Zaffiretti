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

    //valore boolean se ha worker on it
    //set different layers for different levels of build
    //set dome image
    //set worker image
}
