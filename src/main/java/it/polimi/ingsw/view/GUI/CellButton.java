package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;

import java.io.Serializable;

public class CellButton extends Button{

    public String position;

    private int valReply = -5;

    CellButton(String position){
        this.position = position;
        this.setStyle("-fx-background-color: Blue");
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }


    public void refresh(int value, int worker) {
        if (value == 0){
            if (worker == 0){

            }
            if (worker == 1){

            }
            if (worker == 2){

            }
            else
                {this.setStyle("-fx-background-color: MediumSeaGreen");
            }

        }
        if (value == 1){
            if (worker == 0){

            }
            if (worker == 1){

            }
            if (worker == 2){

            }
            this.setStyle("-fx-background-color: Red");
        }
        if (value == 2){
            if (worker == 0){

            }
            if (worker == 1){

            }
            if (worker == 2){

            }
            this.setStyle("-fx-background-color: Blue");
        }
        if (value == 3){
            if (worker == 0){

            }
            if (worker == 1){

            }
            if (worker == 2){

            }
            this.setStyle("-fx-background-color: Pink");
        }
        if (value == 4) {
            setDome();
        }
    }

    public void setDome() {
        //TODO create images
        /*
        BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("Image/Buildings/DomeTemp/.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        this.setBackground(background);
         */
        this.setStyle("-fx-background-color: Black");
    }

    public int getValReply() {
        return valReply;
    }

    public void setValReply(int valReply) {
        this.valReply = valReply;
    }
}
