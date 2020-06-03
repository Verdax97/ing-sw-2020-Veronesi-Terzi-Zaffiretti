package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

/**
 * The type Cell button.
 */
public class CellButton extends Button{

    /**
     * Gets id from list.
     *
     * @return the id from list
     */
    public int getIdFromList() {
        return idFromList;
    }

    /**
     * Sets id from list.
     *
     * @param idFromList the id from list
     */
    public void setIdFromList(int idFromList) {
        this.idFromList = idFromList;
    }

    //TODO CREATE IMAGES FOR GUI
    private int idFromList;
    /**
     * The X.
     */
    public int x;
    /**
     * The Y.
     */
    public int y;
    private boolean ground;

    /**
     * Instantiates a new Cell button.
     *
     * @param x the x
     * @param y the y
     */
    CellButton(int x, int y){
        this.idFromList = -5;
        this.x = x;
        this.y = y;
        this.ground = true;
        //memo to change this into a Level 0 structure
        this.setStyle("-fx-background-color: transparent; -fx-background-size: 100% 100%; -fx-background-repeat: no-repeat;");
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }


    /**
     * Refresh.
     *
     * @param value the value
     */
    public void refresh(int value) {
        if (value == 0){
            this.setStyle("-fx-background-color: transparent;");
        }
        if (value == 1){
            this.ground = false;
            this.setStyle("-fx-background-color: Orange");
        }
        if (value == 2){
            this.ground = false;
            this.setStyle("-fx-background-color: Black");
        }
        if (value == 3){
            this.ground = false;
            this.setStyle("-fx-background-color: Red");
        }
        if (value == 4) {
            setDome();
        }
    }

    /**
     * Sets dome.
     */
    public void setDome() { this.setStyle("-fx-background-color: Blue"); }

    /**
     * Lighten.
     *
     * @param selectable the selectable
     */
    public void lighten(boolean selectable) {
        //apply a layer on the button giving back a feedback about being pressed
        if (selectable){
            this.setStyle("-fx-border-color: #fc0fc0; -fx-border-width: 5px;");
        } else{
            this.setStyle("-fx-border-width: 0px;");
        }
    }

    /**
     * Turn off.
     */
    public void turnOff() {
        //reset aspect of board to default
        this.setOpacity(1);
        if (this.ground){
            this.setStyle("-fx-background-color: transparent;");
        } else {
            this.setStyle("-fx-border-width: 0px;");
        }
    }
}
