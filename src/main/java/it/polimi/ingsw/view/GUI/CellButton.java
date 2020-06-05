package it.polimi.ingsw.view.GUI;

import javafx.scene.control.Button;

/**
 * The type Cell button.
 * @author Stefano
 * */
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
        /*
        this.setStyle("-fx-background-color: transparent; -fx-background-size: 100% 100%; -fx-background-repeat: no-repeat;");
        this.setAlignment(Pos.CENTER);
         */
        this.getStylesheets().add("CSS/Ground.css");
        this.getStyleClass().clear();
        this.getStyleClass().add("normal");
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }


    /**
     * Refresh.
     *
     * @param value the value
     */
    public void refresh(int value) {
        synchronized (getStylesheets()) {
            getStylesheets().clear();
            if (value == 0) {
                getStylesheets().add("CSS/Ground.css");
            }
            if (value == 1) {
                this.ground = false;
                getStylesheets().add("CSS/Level1.css");
            }
            if (value == 2) {
                this.ground = false;
                getStylesheets().add("CSS/Level2.css");
            }
            if (value == 3) {
                this.ground = false;
                getStylesheets().add("CSS/Level3.css");
            }
            if (value == 4) {
                setDome();
            }
        }
    }

    /**
     * Sets dome.
     */
    public void setDome() { this.setStyle("-fx-background-color: dodgerblue"); }

    /**
     * Lighten.
     *
     * @param selectable the selectable
     */
    public void lighten(boolean selectable) {
        getStyleClass().clear();
        //apply a border on the button giving back a feedback about being pressed
        if (selectable){
            getStyleClass().add("selectable");
        } else{
            getStyleClass().add("normal");
        }
    }

    /**
     * Turn off.
     */
    public void turnOff() {
        //reset aspect of board to default
        getStyleClass().clear();
        getStyleClass().add("normal");
    }
}
