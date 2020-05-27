package it.polimi.ingsw.view.GUI;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class CellButton extends Button {

    String position;
    public Image worker;
    public Color building;
    public Image dome;

    private int valReply = -5;

    public int getValReply() {
        return valReply;
    }

    public void setValReply(int valReply) {
        this.valReply = valReply;
    }

    CellButton(String position){
        this.position = position;
    }
}
