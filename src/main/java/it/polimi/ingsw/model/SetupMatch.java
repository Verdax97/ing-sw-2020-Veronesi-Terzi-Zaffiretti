package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gods.*;

import java.util.ArrayList;

/**
 * Class SetupMatch has all the information that are set before the game begins
 */
public class SetupMatch {

    private final ArrayList<God> godList;
    private final ArrayList<God> godPicked = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();

    /**
     * Constructor SetupMatch creates a new SetupMatch instance.
     */
    public SetupMatch() {
        godList = new ArrayList<>();
        this.addGod(new Apollo());
        this.addGod(new Artemis());
        this.addGod(new Athena());
        this.addGod(new Atlas());
        this.addGod(new Charon());
        this.addGod(new Chrono());
        this.addGod(new Demeter());
        this.addGod(new Hephaestus());
        this.addGod(new Hestia());
        this.addGod(new Minotaur());
        this.addGod(new Pan());
        this.addGod(new Prometheus());
        this.addGod(new Triton());
        this.addGod(new Zeus());
    }


    /**
     * Method PickGod move the god from the one list to the other
     *
     * @param i of type int
     * @return God god
     */
    public God pickGod(int i) {
        God god = godPicked.get(i);
        godPicked.remove(god);
        return god;
    }

    /**
     * Method getGodPicked returns the godPicked of this SetupMatch object.
     *
     * @return the godPicked (type ArrayList&lt;God&gt;) of this SetupMatch object.
     */
    public ArrayList<God> getGodPicked() {
        return godPicked;
    }

    /**
     * Method getGodList returns the godList of this SetupMatch object.
     *
     * @return the godList (type ArrayList&lt;God&gt;) of this SetupMatch object.
     */
    public ArrayList<God> getGodList() {
        return godList;
    }

    /**
     * Method AddGod add a god to the list
     *
     * @param god of type God
     */
    //for the initialization
    private void addGod(God god) {
        this.godList.add(god);
    }

    /**
     * Method AddGodPicked add the selected god to the list of god picked for the game
     *
     * @param god of type God
     */
//for the setup phase
    public void addGodPicked(God god) {
        this.godPicked.add(god);
        this.godList.remove(god);
    }

    /**
     * Method getPlayers returns the players of this SetupMatch object.
     *
     * @return the players (type ArrayList&lt;Player&gt;) of this SetupMatch object.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Method SetPlayers set the player list
     *
     * @param players of type ArrayList&lt;Player&gt;
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Method CreatePlayersFromNickname creates Player objects from the nickname inserted
     *
     * @param player of type ArrayList&lt;String&gt;
     */
    public void createPlayersFromNickname(ArrayList<String> player) {
        for (String elem : player) {
            players.add(new Player(elem));
        }
        if (player.size() < 3)
            return;

        //search for Chrono to be removed
        for (God god : godList) {
            if (god instanceof Chrono) {
                godList.remove(god);
                break;
            }
        }
    }
}
