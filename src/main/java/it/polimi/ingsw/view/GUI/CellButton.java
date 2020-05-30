package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class CellButton extends Button{

    //TODO CREATE IMAGES FOR GUI

    public int x;
    public int y;
    public boolean worker;
    private int valReply = -5;

    CellButton(int x, int y){
        this.x = x;
        this.y = y;
        this.worker = false;
        //memo to change this into a Level 0 structure
        this.setStyle("-fx-background-image: url('/Images/Logo.png'); -fx-background-size: 100% 100%; -fx-background-repeat: no-repeat;");
        this.setAlignment(Pos.CENTER);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }


    public void refresh(int value, boolean worker) {
        if (value == 0){
            if (worker == true){
                this.setStyle("-fx-background-color: White");
            }
            this.setStyle("-fx-background-color: Grey");
        }
        if (value == 1){
            if (worker == true){
                this.setStyle("-fx-background-color: Brown");
            }
            this.setStyle("-fx-background-color: Orange");
        }
        if (value == 2){
            if (worker == true){
                this.setStyle("-fx-background-color: Blue");
            }
            this.setStyle("-fx-background-color: Black");
        }
        if (value == 3){
            if (worker == true){
                this.setStyle("-fx-background-color: Pink");
            }
            this.setStyle("-fx-background-color: Red");
        }
        if (value == 4) {
            setDome();
        }
    }

    public void setDome() { this.setStyle("-fx-background-color: Black"); }

    public int getValReply() {
        return valReply;
    }

    public void setValReply(int valReply) {
        this.valReply = valReply;
    }
}
