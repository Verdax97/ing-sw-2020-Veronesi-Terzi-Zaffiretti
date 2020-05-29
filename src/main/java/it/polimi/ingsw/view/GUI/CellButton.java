package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class CellButton extends Button{

    //TODO CREATE IMAGES FOR GUI

    public String position;
    private int valReply = -5;

    CellButton(String position){
        this.position = position;
        //memo to change this into a Level 0 structure
        this.setStyle("-fx-background-image: url('/Images/Logo.png'); -fx-background-size: 100% 100%; -fx-background-repeat: no-repeat;");
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
        this.setStyle("-fx-background-color: Black");
    }

    public int getValReply() {
        return valReply;
    }

    public void setValReply(int valReply) {
        this.valReply = valReply;
    }
}
