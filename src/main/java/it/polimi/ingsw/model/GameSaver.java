package it.polimi.ingsw.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Class GameSaver save/loads old games not finished
 */
public class GameSaver {
    private static File saveFile;

    /**
     * Method checkForGames true if there is another game with the same players
     *
     * @param lobby of type Lobby
     * @return boolean
     */
    public static boolean checkForGames(Lobby lobby) {
        ArrayList<String> players = new ArrayList<>(lobby.getPlayers());
        Collections.sort(players);
        StringBuilder fileName = new StringBuilder();
        for (String player : players) {
            fileName.append(player);
            if (!player.equals(players.get(players.size() - 1)))
                fileName.append("-");
        }
        fileName.append(".txt");
        // Creates Directory savedGames if missing
        File directory = new File("savedGames");
        if (!directory.exists()){directory.mkdir();}
        //Creates SaveGame if it doesn't already exist or returns true if it already exists
        saveFile = new File("savedGames/" + fileName.toString());
        if (!saveFile.exists()) {
            System.out.println("File created: " + saveFile.getName());
            return false;
        } else {
            System.out.println("File already exists.");
            return true;
        }
    }

    /**
     * Method saveGame saves the game on a file
     *
     * @param match of type Match
     */
    public static void saveGame(Match match) {
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(saveFile);
            ArrayList<Player> players = match.getSetup().getPlayers();
            //prints the players in turn order
            for (int i = 0; i < players.size(); i++) {
                fileWriter.write(players.get(i).getNickname());
                if (i == players.size() - 1)
                    fileWriter.write("\n");
                else
                    fileWriter.write("-");
            }
            //prints the player that have to start the next turn
            fileWriter.write(match.getPlayerTurn().getNickname() + "\n");
            //prints the players gods
            for (int i = 0; i < players.size(); i++) {
                fileWriter.write(players.get(i).getGodPower().name);
                if (i == players.size() - 1)
                    fileWriter.write("\n");
                else
                    fileWriter.write("-");
            }

            fileWriter.write(PrintBoard(players, match.getBoard()));


            fileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred when saving the game.");
            e.printStackTrace();
        }
    }

    /**
     * Method loadGame loads the old game
     *
     * @return Match
     * @throws FileNotFoundException when
     */
    public static Match loadGame() throws FileNotFoundException {
        Scanner scanner = new Scanner(saveFile);
        String s = scanner.nextLine();
        ArrayList<String> players = new ArrayList<>();
        for (String name :
                s.split("-")) {
            players.add(name);
        }

        Match match = new Match(players);

        String playerTurnName = scanner.nextLine();
        Player playerTurn = null;
        int nPlayer = 0;
        for (int e = 0; e < match.getPlayers().size(); e++ ){
            if (playerTurnName.equals(match.getPlayers().get(e).getNickname())){
                nPlayer = e;
                playerTurn = match.getPlayers().get(e);
            }
        }

        s = scanner.nextLine();
        int playerN = 0;
        for (String name : s.split("-")){
            match.getPlayers().get(playerN).setGodPower(godFromName(name, match));
            playerN++;
        }

        s = scanner.nextLine();
        int i;
        ArrayList<Integer> playerDebuffed = new ArrayList<>();
        for (String name : s.split("-")){
            playerDebuffed.add(Integer.parseInt(name));
        }


        Worker worker;
        for (int x = 0; x < 5; x++){
            s = scanner.nextLine();
            for (int y = 0; y < 5; y++){
                String cell = s.split(" ")[y];
                match.getBoard().getCell(x,y).setBuilding(cell.charAt(0)-48);
                if (cell.length() == 2){
                    if (cell.charAt(1) != 'D') {
                        i = Integer.parseInt(cell)%10;
                        worker = new Worker();
                        worker.setPlayer(match.getPlayers().get(i));
                        match.getBoard().getCell(x, y).setWorker(worker);
                        if (playerDebuffed.get(i) == 1){worker.setDebuff(true);}
                    } else match.getBoard().getCell(x, y).setDome(true);
                }
            }
        }

        match.setPlayerTurn(playerTurn);
        match.setnPlayer(nPlayer);


        return match;
    }

    /**
     * Method PrintBoard print the board on the file
     *
     * @param players of type ArrayList<Player>
     * @param board   of type Board
     * @return String
     */
    private static String PrintBoard(ArrayList<Player> players, Board board) {
        StringBuilder s = new StringBuilder();
        //prints the debuffs
        int[] debuffed = new int[players.size()];
        //prints the debuffs
        for (int i = 0; i < players.size(); i++) {
            s.append(debuffed[i]);
            if (i == players.size() - 1)
                s.append("\n");
            else
                s.append("-");
        }
        //prints the board valBuilding-Dome-player
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                s.append(board.getCell(i, j).getBuilding());
                if (board.getCell(i, j).getDome())
                    s.append("D");
                if (board.getCell(i, j).getWorker() != null) {
                    for (int k = 0; k < players.size(); k++) {
                        if (players.get(k) == board.getCell(i, j).getWorker().getPlayer()) {
                            s.append(k);
                            debuffed[k] = board.getCell(i, j).getWorker().isDebuff() ? 1 : 0;
                        }
                    }
                }
                s.append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    /**
     * Method godFromName gets the god from the name
     *
     * @param name  of type String
     * @param match of type Match
     * @return God
     */
    private static God godFromName(String name, Match match) {
        for (int index = 0; index < match.getSetup().getGodList().size(); index++) {
            if (match.getSetup().getGodList().get(index).name.equals(name)) {
                return match.getSetup().getGodList().get(index);
            }
        }
        return null;
    }
}