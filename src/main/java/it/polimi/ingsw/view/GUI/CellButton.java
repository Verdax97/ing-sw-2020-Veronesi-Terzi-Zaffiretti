package it.polimi.ingsw.view.GUI;

import javafx.scene.control.Button;

public class CellButton extends Button {

    String position;
    int color;

    CellButton(String position){
        this.position = position;
    }

    public void setWorker() {
        if (color == 0){
            /*
            BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("Image/Buildings/DomeTemp/.png").toExternalForm()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            this.setBackground(background);
             */
        }
        if (color == 1){
            /*
            BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("Image/Buildings/DomeTemp/.png").toExternalForm()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            this.setBackground(background);
             */
        }
        if (color == 2){
            /*
            BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("Image/Buildings/DomeTemp/.png").toExternalForm()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            this.setBackground(background);
             */
        }
        if (color == 3){
            /*
            BackgroundImage backgroundImage = new BackgroundImage( new Image( getClass().getResource("Image/Buildings/DomeTemp/.png").toExternalForm()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background = new Background(backgroundImage);
            this.setBackground(background);
             */
        }
    }

    public void setBuilding(int value) {
        if (value == 0){
            this.setStyle("-fx-background-color: MediumSeaGreen");
            this.color = 0;
        }
        if (value == 1){
            this.setStyle("-fx-background-color: Red");
            this.color = 1;
        }
        if (value == 2){
            this.setStyle("-fx-background-color: Blue");
            this.color = 2;
        }
        if (value == 3){
            this.setStyle("-fx-background-color: Pink");
            this.color = 3;
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

    private int valReply = -5;

    public int getValReply() {
        return valReply;
    }

    public void setValReply(int valReply) {
        this.valReply = valReply;
    }
}
