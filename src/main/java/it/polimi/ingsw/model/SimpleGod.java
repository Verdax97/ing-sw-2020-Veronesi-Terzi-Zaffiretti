package it.polimi.ingsw.model;

import javafx.scene.image.Image;

import java.io.Serializable;

public class SimpleGod implements Serializable {
    protected String name;
    protected String description;
    protected Image img = null;

    public void setSimpleGod(String name, String description, Image img) {
        this.name = name;
        this.description = description;
        this.img = img;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getImg() {
        return img;
    }
}
