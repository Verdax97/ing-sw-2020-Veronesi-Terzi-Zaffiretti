package it.polimi.ingsw.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Match;

import java.io.PrintStream;

/**
 * Class ServerView is used for printing the board information on the server console
 */
public class ServerView {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    private final PrintStream outputStream;


    /**
     * Constructor ServerView creates a new ServerView instance.
     */
    public ServerView() {
        this.outputStream = new PrintStream(System.out);
    }

    /**
     * Method printBoard ...
     *
     * @param board of type Board
     * @param match of type Match
     */
    public void printBoard(Board board, Match match) {
        if (match.getPlayers().size() == 0)
            return;
        outputStream.print(ANSI_RED + match.getPlayers().get(0).getNickname() + " ");
        if (match.getPlayers().size() == 2)
            outputStream.print(ANSI_GREEN + match.getPlayers().get(1).getNickname() + " ");
        if (match.getPlayers().size() == 3)
            outputStream.print(ANSI_BLUE + match.getPlayers().get(2).getNickname() + ANSI_RESET);
        outputStream.println(ANSI_RESET);
        if (board == null)
            return;
        for (int j = 4; j >= 0; j--) {
            outputStream.print(j + "|");
            for (int i = 0; i < 5; i++) {
                if (board.getCell(i, j).getDome()) {
                    outputStream.print("D" + " ");
                } else if (board.getCell(i, j).getWorker() != null) {
                    if (match.getPlayers().get(0).getNickname().equals(board.getCell(i, j).getWorker().getPlayer().getNickname())) {
                        System.out.print(ANSI_RED + board.getCell(i, j).getBuilding()+ANSI_RESET + " ");
                    } else if (match.getPlayers().get(1).getNickname().equals(board.getCell(i, j).getWorker().getPlayer().getNickname())) {
                        System.out.print(ANSI_GREEN + board.getCell(i, j).getBuilding() + ANSI_RESET + " ");
                    } else if (match.getPlayers().get(2).getNickname().equals(board.getCell(i, j).getWorker().getPlayer().getNickname())) {
                        System.out.print(ANSI_BLUE + board.getCell(i, j).getBuilding() + ANSI_RESET + " ");
                    }
                } else {
                    System.out.print(board.getCell(i, j).getBuilding() + " ");
                }
            }
            outputStream.println();
        }
        outputStream.println("Y|---------");
        outputStream.println("X 0 1 2 3 4\n\n");
    }
}
