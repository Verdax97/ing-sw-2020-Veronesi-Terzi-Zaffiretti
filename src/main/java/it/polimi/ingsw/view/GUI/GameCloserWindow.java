package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * The type Game closer window.
 */
public class GameCloserWindow {
    @FXML
    private Label message;
    @FXML
    private MediaView videoPlayer;

    /**
     * The Winning message.
     */
    String winningMessage = "Winner winner chicken dinner!!!!";
    /**
     * The Winner video.
     */
    Media winnerVideo = new Media(getClass().getClassLoader().getResource("videos/win.mp4").toExternalForm());
    /**
     * The Loser video.
     */
    Media loserVideo = new Media(getClass().getClassLoader().getResource("videos/lose.mp4").toExternalForm());
    /**
     * The S.
     */
    Media s;

    /**
     * Sets winner.
     *
     * @param won the won
     */
    public void setWinner(boolean won) {
        if (won)
            s = winnerVideo;
        else
            s = loserVideo;
    }

    /**
     * Sets message.
     *
     * @param message the message
     * @param won     the won
     */
    public void setMessage(String message, boolean won) {
        if (won) {
            this.message.setText(winningMessage);
            return;
        }
        this.message.setText(message);
    }

    /**
     * Show video.
     */
    public void showVideo() {
        MediaPlayer mediaPlayer = new MediaPlayer(s);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(.15);
        videoPlayer.setMediaPlayer(mediaPlayer);
    }

    /**
     * Close all.
     */
    public void closeAll() {
        System.exit(0);
    }
}
