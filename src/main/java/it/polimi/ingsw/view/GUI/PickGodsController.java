package it.polimi.ingsw.view.GUI;

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

/**
 * The type Pick gods controller.
 */
public class PickGodsController {

    /**
     * The Client main.
     */
    ClientMain clientMain = null;
    /**
     * The Client input gui.
     */
    ClientInputGUI clientInputGUI = null;
    /**
     * The Reply.
     */
    int[] reply = new int[] {-5, -5, -5, -5};

    private ArrayList<GodCard> pickableGod = new ArrayList<>();

    @FXML
    private ChoiceBox listOfGod;

    @FXML
    private ImageView godImage;

    @FXML
    private Text godEffect;

    @FXML
    private Button confirmGod;

    @FXML
    private Text sceneTitle;

    /**
     * Sets client input gui.
     *
     * @param clientInputGUI the client input gui
     */
    public void setClientInputGUI(ClientInputGUI clientInputGUI) {
        this.clientInputGUI = clientInputGUI;
    }

    /**
     * Sets client main.
     *
     * @param clientMain the client main
     */
    public void setClientMain(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    /**
     * Gets description god.
     *
     * @param msg      the msg
     * @param yourTurn the your turn
     */
    public void getDescriptionGod(String msg, boolean yourTurn) {
        confirmGod.setVisible(yourTurn);

        if (yourTurn)
            sceneTitle.setText("Pick a god");
        else
            sceneTitle.setText("Wait for your turn");

        //reset all
        pickableGod.clear();
        godEffect.setText("Select a god");
        godImage.setImage(null);
        listOfGod.getItems().clear();
        ArrayList<String> godStrings = new ArrayList<>(Arrays.asList(msg.split("\n")));
        //create godCards
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

    /**
     * Send reply.
     */
    public void sendReply () {
        clientInputGUI.Reply(reply[0], reply[1], reply[2], reply[3]);
        godEffect.setText("Select a god");
        godImage.setImage(null);
        reply = new int[]{-5, -5, -5, -5};
    }

    /**
     * Select god.
     */
    public void selectGod() {
        if (listOfGod.getValue() == null)
            return;
        GodCard godCard = getGodCard(listOfGod.getValue().toString());
        if (godCard == null)
            return;
        godImage.setImage(godCard.image);
        godEffect.setText(godCard.description);
        reply[0] = Integer.parseInt(godCard.id);
    }

    private GodCard getGodCard(String name) {
        for (GodCard card : pickableGod) {
            if (card.name.equals(name))
                return card;
        }
        return null;
    }
}
