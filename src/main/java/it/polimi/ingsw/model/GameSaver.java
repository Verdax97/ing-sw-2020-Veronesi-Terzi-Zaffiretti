package it.polimi.ingsw.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GameSaver {
    private static File saveFile;

    public static boolean checkForGames(Lobby lobby) {
        ArrayList<String> players = new ArrayList<>(lobby.getPlayers());
        Collections.sort(players);
        StringBuilder fileName = new StringBuilder();
        for (String player :
                players) {
            fileName.append(player);
            if (!player.equals(players.get(players.size() - 1)))
                fileName.append("-");
        }
        fileName.append(".txt");
        try {
            saveFile = new File("/savedGames/" + fileName.toString());
            if (saveFile.createNewFile()) {
                System.out.println("File created: " + saveFile.getName());
                return false;
            } else {
                System.out.println("File already exists.");
                return true;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return false;
        }
    }

    public void saveGame(Match match) {
        try {
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
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Match loadGame() throws FileNotFoundException {
        Scanner scanner = new Scanner(saveFile);
        String s = scanner.nextLine();
        ArrayList<String> players = new ArrayList<>();
        for (String name :
                s.split("-")) {
            players.add(name);
        }

        Match match = new Match(players);

        //todo setup nplayer and playerTurn
        return match;
    }

    private String PrintBoard(ArrayList<Player> players, Board board) {
        StringBuilder s = new StringBuilder();
        //prints the debuffs
        int[] debuffed = new int[players.size()];
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
            }

        }
        s.append("\n");
        //prints the debuffs
        for (int i = 0; i < players.size(); i++) {
            s.append(debuffed[i]);
            if (i == players.size() - 1)
                s.append("\n");
            else
                s.append("-");
        }
        return s.toString();
    }
}