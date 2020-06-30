package it.polimi.ingsw.view.GUI;
import javafx.scene.image.Image;

/**
 * Class GodCard contains information about a card
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
     * Constructor GodCard creates a new GodCard instance.
     *
     * @param name of type String
     */
    public GodCard(String name) {
        this.name = name;
    }

}
