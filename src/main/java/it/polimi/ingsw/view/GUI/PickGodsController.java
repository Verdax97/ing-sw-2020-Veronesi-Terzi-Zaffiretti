package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.God;
import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class PickGodsController {

    ClientMain clientMain = null;
    ClientInputGUI clientInputGUI = null;
    int[] reply = new int[] {-5, -5, -5, -5};

    private ArrayList<GodCard> pickableGod = new ArrayList<>();

    boolean masterChoose = false;

    @FXML
    private ChoiceBox listOfGod;

    @FXML
    private ImageView godImage;

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

    public void getDescriptionGod(String msg, boolean yourTurn) {
        confirmGod.setVisible(yourTurn);
        ArrayList<String> godStrings = new ArrayList<>();
        godStrings.addAll(Arrays.asList(msg.split("\n")));
        for (int i = 0; i < godStrings.size(); i++) {
            ArrayList<String> s = new ArrayList<>(Arrays.asList(godStrings.get(i).split(" ", 3)));
            String indexGod = s.get(0).split(Pattern.quote(")"))[0];
            String nameGod = s.get(1).split(":")[0];
            String descriptionGod = s.get(2).split("\n")[0];
            listOfGod.getItems().add(nameGod);
            GodCard godCard = new GodCard(nameGod);
            godCard.id = indexGod;
            godCard.description = descriptionGod;
            godCard.image = new Image("Images/godCards/" + nameGod + ".png");
            pickableGod.add(godCard);
        }
    }

    public void sendReply () {
        clientInputGUI.Reply(reply[0], reply[1], reply[2], reply[3]);
        godEffect.setText("Select a god");
        reply = new int[]{-5, -5, -5, -5};
    }

    public void selectGod(GodCard godCard){
        godImage.setImage(godCard.image);
        godEffect.setText(godCard.description);
        reply[0] = Integer.parseInt(godCard.id);
    }
}
