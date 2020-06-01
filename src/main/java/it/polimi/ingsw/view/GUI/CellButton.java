package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class CellButton extends Button{

    public int getIdFromList() {
        return idFromList;
    }

    public void setIdFromList(int idFromList) {
        this.idFromList = idFromList;
    }

    //TODO CREATE IMAGES FOR GUI
    private int idFromList;
    public int x;
    public int y;
    private boolean ground;
    private int valReply = -5;

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


    public void refresh(int value, boolean worker) {
        if (value == 0){
            if (worker){
                this.setStyle("-fx-background-color: White");
                this.ground = false;
                return;
            }
            this.setStyle("-fx-background-color: transparent;");
        }
        if (value == 1){
            if (worker){
                this.setStyle("-fx-background-color: Brown");
                return;
            }
            this.setStyle("-fx-background-color: Orange");
        }
        if (value == 2){
            if (worker){
                this.setStyle("-fx-background-color: Pink");
                return;
            }
            this.setStyle("-fx-background-color: Black");
        }
        if (value == 3){
            if (worker){
                this.setStyle("-fx-background-color: Pink");
                return;
            }
            this.setStyle("-fx-background-color: Red");
        }
        if (value == 4) {
            setDome();
        }
    }

    public void setDome() { this.setStyle("-fx-background-color: Blue"); }

    public void lighten(boolean selectable) {
        //apply a layer on the button giving back a feedback about being pressed
        if (selectable){
            this.setStyle("-fx-border-color: #fc0fc0; -fx-border-width: 10px;");
        }else {
            if (this.ground) {
                this.setStyle("-fx-background-color: white;");
                this.setOpacity(0.5);
            } else this.setOpacity(0.5);
        }
    }

    public void turnOff() {
        //reset aspect of board to default
        if (this.ground){
            this.setStyle("-fx-background-color: transparent;");
        } else this.setOpacity(1);
    }

    public int getValReply() {
        return valReply;
    }

    public void setValReply(int valReply) {
        this.valReply = valReply;
    }
}
