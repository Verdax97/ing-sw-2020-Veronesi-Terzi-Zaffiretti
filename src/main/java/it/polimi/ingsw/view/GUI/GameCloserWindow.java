package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class GameCloserWindow {
    @FXML
    private Label message;
    @FXML
    private MediaView videoPlayer;

    String winningMessage = "Winner winner chicken dinner!!!!";
    //String losingMessage = "You get nothing! You lose! Good day Sir!";
    Media winnerVideo = new Media(getClass().getClassLoader().getResource("videos/win.mp4").toExternalForm());
    Media loserVideo = new Media(getClass().getClassLoader().getResource("videos/lose.mp4").toExternalForm());
    Media s;

    public void setWinner(boolean won) {
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
        MediaPlayer mediaPlayer = new MediaPlayer(s);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(.15);
        videoPlayer.setMediaPlayer(mediaPlayer);
    }

    public void closeAll() {
        System.exit(0);
    }
}
