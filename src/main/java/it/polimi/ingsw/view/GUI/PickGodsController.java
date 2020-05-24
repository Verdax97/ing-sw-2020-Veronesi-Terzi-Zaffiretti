package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.client.ClientInputGUI;
import it.polimi.ingsw.view.client.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class PickGodsController {

    ClientMain clientMain = null;
    ClientInputGUI clientInputGUI = null;
    int[] reply = new int[] {-5, -5, -5, -5};

    @FXML
    private GridPane godSelectionPane;

    @FXML
    private Text godEffect;

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
        int colLimit = godStrings.size() / 2;
        for (int i = 0; i < godStrings.size(); i++) {
            ArrayList<String> s = new ArrayList<>();
            s.addAll(Arrays.asList(godStrings.get(i).split(" ")));
            String indexGod = s.get(0).split(new String(")"))[0];
            String nameGod = s.get(1).split(":")[0];
            String descriptionGod = s.get(2);
            GodCard godCard = new GodCard();
            godCard.name = nameGod;
            godCard.description = descriptionGod;
            godCard.setId(indexGod);
            godCard.setGraphic(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("Images/godCards/"+nameGod+".png"))));
            godSelectionPane.add(godCard, row, i);
            godCard.setOnAction(e -> selectGod(godCard));
            if (i > colLimit){
                row++;
            }
        }
    }

    public void sendReply(){
        clientInputGUI.Reply(reply[0], reply[1], reply[2], reply[3]);
        reply = new int[] {-5, -5, -5, -5};
    }

    public void selectGod(GodCard godCard){
        godEffect.setText(godCard.description);
        reply[0] = Integer.parseInt(godCard.getId());
    }

}
