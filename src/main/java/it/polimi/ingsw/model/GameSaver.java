package it.polimi.ingsw.model;

import it.polimi.ingsw.view.Colors;

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
    private static StringBuilder fileName;

    /**
     * Method checkForGames true if there is another game with the same players
     *
     * @param lobby of type Lobby
     * @return boolean boolean
     * @throws IOException the io exception
     */
    public static boolean checkForGames(Lobby lobby) throws IOException {
        ArrayList<String> players = new ArrayList<>(lobby.getPlayers());
        Collections.sort(players);
        fileName = new StringBuilder();
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
            //saveFile.createNewFile();
            System.out.println("File does not exists.");
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
                if (!saveFile.createNewFile())
                    throw new IOException();
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
            System.out.println(Colors.ANSI_RED + e.toString() + Colors.ANSI_RESET);
        }
    }

    /**
     * Method loadGame loads the old game
     *
     * @return Match match
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


        Worker worker;
        for (int x = 0; x < 5; x++){
            s = scanner.nextLine();
            for (int y = 0; y < 5; y++){
                String cell = s.split(" ")[y];
                match.getBoard().getCell(x,y).setBuilding(cell.charAt(0)-48);
                if (cell.length() == 2){
                    if (cell.charAt(1) != 'D') {
                        int i = Integer.parseInt(cell) % 10;
                        worker = new Worker();
                        worker.setPlayer(match.getPlayers().get(i));
                        match.getBoard().getCell(x, y).setWorker(worker);
                    } else match.getBoard().getCell(x, y).setDome(true);
                }
            }
        }

        s = scanner.nextLine();
        if (s.length() == 2){
            int pos = Integer.parseInt(s);
            match.getBoard().getCell(pos/10, pos%10).getWorker().setLastMovement(1);
        }

        match.setPlayerTurn(playerTurn);
        match.setnPlayer(nPlayer);

        scanner.close();
        return match;
    }

    /**
     * Method PrintBoard print the board on the file
     *
     * @param players of type ArrayList&lt;Player&gt;
     * @param board   of type Board
     * @return String
     */
    private static String PrintBoard(ArrayList<Player> players, Board board) {
        StringBuilder s = new StringBuilder();
        //prints the debuffs
        String debuffed = "5";
        //prints the debuffs

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
                            if (board.getCell(i, j).getWorker().getPlayer().getGodPower().getName().equals("Athena") &&
                                    board.getCell(i, j).getWorker().getLastMovement() == 1) {
                                debuffed = i + Integer.toString(j);
                            }
                        }
                    }
                }
                s.append(" ");
            }
            s.append("\n");
        }
        s.append(debuffed);

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

    /**
     * Method closeFile delete the file of the game
     */
    public static void deleteGameData() {
        saveFile = new File("savedGames/" + fileName.toString());
        if (saveFile.delete())
            System.out.println("File deleted successfully!");
        else System.out.println("Cannot delete file");
    }
}