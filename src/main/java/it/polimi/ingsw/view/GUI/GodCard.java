package it.polimi.ingsw.view.GUI;

import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class GodCard extends Button {

    public String id;
    public String name;
    public String description;
    public Image image;

    public GodCard(String name) {
        this.name = name;
    }

}
