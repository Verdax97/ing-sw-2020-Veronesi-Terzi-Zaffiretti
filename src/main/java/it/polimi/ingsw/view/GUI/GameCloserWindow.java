package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GameCloserWindow {
    @FXML
    private Label message;
    @FXML
    private WebView video;

    String winningMessage = "Winner winner chicken dinner!!!!";
    String losingMessage = "You get nothing! You lose! Good day Sir!";
    String winnerVideo = "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
    String loserVideo = "https://www.youtube.com/watch?v=ymPpIzaanhY";
    String s;

    public void setWinner(boolean won) {
        s = "";
        if (won)
            s = winnerVideo;
        else
            s = loserVideo;
    }

    public void setMessage(String message, boolean won) {
        if (won) {
            this.message.setText(winningMessage);
            return;
        }
        this.message.setText(message);
    }

    public void showVideo() {
        WebEngine webEngine = video.getEngine();
        webEngine.load(s);
    }

    public void closeAll() {
        System.exit(0);
    }
}
