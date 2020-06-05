package it.polimi.ingsw.view.GUI;
import javafx.scene.image.Image;

/**
 * The type God card.
 */
public class GodCard {

    /**
     * The Id.
     */
    public String id;
    /**
     * The Name.
     */
    public String name;
    /**
     * The Description.
     */
    public String description;
    /**
     * The Image.
     */
    public Image image;

    /**
     * Instantiates a new God card.
     *
     * @param name the name
     */
    public GodCard(String name) {
        this.name = name;
    }

}
