package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class SimpleGod the base class for all gods
 */
public class SimpleGod implements Serializable {
    /**
     * The Name.
     */
    protected String name;
    /**
     * The Description.
     */
    protected String description;

    /**
     * Method setSimpleGod initializes the values
     *
     * @param name        of type String
     * @param description of type String
     */
    public void setSimpleGod(String name, String description) {
        this.name = name;
        this.description = description;
    }


    /**
     * Method getName returns the name of this SimpleGod object.
     *
     * @return the name (type String) of this SimpleGod object.
     */
    public String getName() {
        return name;
    }

    /**
     * Method getDescription returns the description of this SimpleGod object.
     *
     * @return the description (type String) of this SimpleGod object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method getImg returns the img of this SimpleGod object.
     *
     * @return the img (type Image) of this SimpleGod object.
     */
}
