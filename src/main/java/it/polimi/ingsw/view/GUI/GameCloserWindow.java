package it.polimi.ingsw.view.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * Class GameCloserWindow is the window showed to user in a end of match status, if closed closes the process.
 */
public class GameCloserWindow {

    @FXML private Label message;
    @FXML private MediaView videoPlayer;

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
     * The media that will be showed
     */
    Media s;

    /**
     * Method setWinner sets s of GameCloserWindow object.
     *
     * @param won of type boolean
     */
    public void setWinner(boolean won) {
        if (won)
            s = winnerVideo;
        else
            s = loserVideo;
    }

    /**
     * Method setMessage sets message of GameCloserWindow object.
     *
     * @param message of type String
     * @param won     of type boolean
     */
    public void setMessage(String message, boolean won) {
        if (won) {
            this.message.setText(winningMessage);
            return;
        }
        this.message.setText(message);
    }

    /**
     * Method showVideo shows Media s to the user.
     */
    public void showVideo() {
        MediaPlayer mediaPlayer = new MediaPlayer(s);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(.15);
        videoPlayer.setMediaPlayer(mediaPlayer);
    }

    /**
     * Method closeAll ends the process.
     */
    public void closeAll() {
        System.exit(0);
    }
}
