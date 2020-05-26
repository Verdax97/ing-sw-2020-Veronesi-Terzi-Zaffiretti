package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class PickGodsController {

    ClientMain clientMain = null;
    ClientInputGUI clientInputGUI = null;
    int[] reply = new int[] {-5, -5, -5, -5};

    boolean masterChoose = false;
    boolean masterPick = false;

    @FXML
    private GridPane godSelectionPane;

    @FXML
    private Text godEffect;

    @FXML
    private Button confirmGod;

    public void setClientInputGUI(ClientInputGUI clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void getDescriptionGod(String msg){
        ArrayList<String> godStrings = new ArrayList<>();
        godStrings.addAll(Arrays.asList(msg.split("/n")));
        //godSelectionPane.getChildren().clear();
        int row = 0;
        int colLimit = 0;
        if (godStrings.size()%2 == 0){
            colLimit = godStrings.size()/2;
        }
        else colLimit = godStrings.size()/2 + 1;
        for (int i = 0; i < godStrings.size(); i++) {
            ArrayList<String> s = new ArrayList<>(Arrays.asList(godStrings.get(i).split(" ", 3)));
            String indexGod = s.get(0).split(Pattern.quote(")"))[0];
            String nameGod = s.get(1).split(":")[0];
            String descriptionGod = s.get(2).split("\n")[0];
            GodCard godCard = new GodCard();
            godCard.name = nameGod;
            godCard.description = descriptionGod;
            godCard.setId(indexGod);
            godCard.setMinSize(200, 250);
            godCard.setMaxSize(200, 250);
            Image image = new Image("Images/godCards/" + nameGod + ".png", godCard.getWidth(), godCard.getHeight(), false, true, true);
            BackgroundImage bImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    new BackgroundSize(godCard.getWidth(), godCard.getHeight(), true, true, true, false));
            Background backGround = new Background(bImage);
            godCard.setBackground(backGround);
            int finalRow = row;
            int finalI = i;
            Platform.runLater(() -> {
                godSelectionPane.add(godCard, finalRow, finalI, 1, 1);
                godCard.setOnAction(e -> selectGod(godCard));
            });
            if (i >= colLimit) {
                row++;
            }
        }
    }

    public void sendReply(){
        clientInputGUI.Reply(reply[0], reply[1], reply[2], reply[3]);
        godEffect.setText("Select a god");
        reply = new int[]{-5, -5, -5, -5};
    }

    public void selectGod(GodCard godCard){
        godEffect.setText(godCard.description);
        reply[0] = Integer.parseInt(godCard.getId());
    }

}
