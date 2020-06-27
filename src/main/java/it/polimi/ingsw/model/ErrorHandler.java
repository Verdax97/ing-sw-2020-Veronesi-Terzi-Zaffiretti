package it.polimi.ingsw.model;

/**
 * The type Error handler.
 */
public class ErrorHandler {
    private final String prefix = Messages.ERROR + " ";

    /**
     * Get error setup string.
     *
     * @param value the value
     * @return the string
     */
    public String GetErrorSetup(int value) {
        return switch (value) {
            case -1 -> "You Lost";
            case 1 -> "You Won!!";
            case 0 -> "";
            case -7 -> Messages.ERROR + " No space to move the worker";
            default -> Messages.ERROR + " generic";
        };
    }

    /**
     * Get error move string.
     *
     * @param value the value
     * @return the string
     */
    public String GetErrorMove(int value) {
        return switch (value) {
            case -1 -> prefix + "Target cell out of board";
            case -2 -> prefix + "Target cell is too far";
            case -3 -> prefix + "Cell is too high";
            case -4 -> prefix + "Cell is occupied by Dome/Worker";
            case -5 -> prefix + "Unable to move enemy Worker";
            case -6 -> prefix + "(Artemis) Same cell as the first one";
            case -7 -> prefix + "(ForcedMovementGod) no space to move enemy worker";
            case 1 -> "\n";
            case 2 -> "Again";
            case 0 -> "why are you here??!!! this is supposed to be an unreachable case!!! someone will be beat for this";
            default -> Messages.ERROR + " Generic";
        };
    }

    /**
     * Get error build string.
     *
     * @param value the value
     * @return the string
     */
    public String GetErrorBuild(int value) {
        return switch (value) {
            case -1 -> prefix + "Target cell out of board";
            case -2 -> prefix + "Target cell is too far";
            case -3 -> prefix + "Worker on the cell";
            case -4 -> prefix + "Cell occupied by a dome";
            case -5 -> prefix + "(Hephaestus) Not same as last built cell";
            case -6 -> prefix + "(Hephaestus) Building is > 2";
            case -7 -> prefix + "(Zeus) third level build forbidden";
            case -8 -> prefix + "(Demeter) same as last built";
            case -9 -> prefix + "Cell is a perimeter space";
            case -10 -> prefix + "You Lost";
            case 1 -> "\n";
            case 2 -> "Again";
            case 0 -> "why are you here??!!! this is supposed to be an unreachable case!!! someone will be beat for this";
            default -> Messages.ERROR + " generic";
        };
    }
}
