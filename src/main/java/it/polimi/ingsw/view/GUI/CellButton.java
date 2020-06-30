package it.polimi.ingsw.view.GUI;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * Class Cell implements AnchorPane functionalities
 */
public class CellButton extends AnchorPane {

    private final AnchorPane anchorPane;
    private int idFromList;
    /**
     * The X.
     */
    public int x;
    /**
     * The Y.
     */
    public int y;

    /**
     * Method getIdFromList returns the idFromList of this CellButton object.
     *
     * @return idFromList of type int
     */
    public int getIdFromList() {
        return idFromList;
    }

    /**
     * Method setIdFromList sets the idFromList of this CellButton object.
     *
     * @param idFromList of type int
     */
    public void setIdFromList(int idFromList) {
        this.idFromList = idFromList;
    }

    /**
     * Constructor CellButton creates a new CellButton instance.
     *
     * @param x of type int
     * @param y of type int
     */
    CellButton(int x, int y) {
        this.idFromList = -5;
        this.x = x;
        this.y = y;
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        Rectangle r = new Rectangle(7.5, 7.5, 65, 65);
        r.setStyle("-fx-fill: white; -fx-stroke: black");
        r.setVisible(false);
        r.setMouseTransparent(true);
        getChildren().add(r);
        Rectangle r1 = new Rectangle(12.5, 12.5, 55, 55);
        r1.setStyle("-fx-fill: white; -fx-stroke: black");
        r1.setVisible(false);
        r1.setMouseTransparent(true);
        getChildren().add(r1);
        Circle r2 = new Circle(40, 40, 27.5);
        r2.setStyle("-fx-fill: white; -fx-stroke: black");
        r2.setVisible(false);
        r2.setMouseTransparent(true);
        getChildren().add(r2);
        Circle r3 = new Circle(40, 40, 21);
        r3.setStyle("-fx-fill: blue; -fx-stroke: black");
        r3.setVisible(false);
        r3.setMouseTransparent(true);
        getChildren().add(r3);
        anchorPane = new AnchorPane();
        anchorPane.setPrefSize(80, 80);
        anchorPane.setMouseTransparent(true);
        getChildren().add(anchorPane);
        anchorPane.getStylesheets().add("CSS/Ground.css");
        anchorPane.getStyleClass().add("normal");
    }


    /**
     * Method refresh resets the default aspect of the CellButton object
     *
     * @param value of type int
     */
    public void refresh(int value) {
        synchronized (anchorPane) {
            if (value == 0) {
                getChildren().get(0).setVisible(false);
                getChildren().get(1).setVisible(false);
                getChildren().get(2).setVisible(false);
            } else if (value == 1) {
                getChildren().get(0).setVisible(true);
            } else if (value == 2) {
                getChildren().get(0).setVisible(true);
                getChildren().get(1).setVisible(true);
            } else if (value == 3) {
                getChildren().get(0).setVisible(true);
                getChildren().get(1).setVisible(true);
                getChildren().get(2).setVisible(true);
            }
            if (value == 4) {
                setDome();
            }
        }
    }

    /**
     * Method setDome sets the Dome of this CellButton object visible.
     */
    public void setDome() {
        getChildren().get(3).setVisible(true);
    }

    /**
     * Method lighten apply a border on the button giving back a feedback about being pressed
     *
     * @param selectable of boolean type
     */
    public void lighten(boolean selectable) {
        anchorPane.getStyleClass().clear();
        //apply a border on the button giving back a feedback about being pressed
        if (selectable){
            anchorPane.getStyleClass().add("selectable");
        } else{
            anchorPane.getStyleClass().add("selected");
        }
    }

    /**
     * Method turnOff resets aspect of board to default.
     */
    public void turnOff() {
        anchorPane.getStyleClass().clear();
        anchorPane.getStyleClass().add("normal");
    }
}
