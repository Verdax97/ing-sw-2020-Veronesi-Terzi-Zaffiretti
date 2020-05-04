package it.polimi.ingsw.model;

public class ErrorHandler {
    private final String prefix = "Error ";

    public String GetErrorSetup(int value) {
        switch (value) {
            case -1:
                return "You Lost";
            case 1:
                return "You Won!!";
            case 0:
                return "";
        }
        return "";
    }

    public String GetErrorMove(int value) {
        switch (value) {
            case -1:
                return prefix + "Target cell out of board";
            case -2:
                return prefix + "Target cell is too far";
            case -3:
                return prefix + "Cell is too high";
            case -4:
                return prefix + "Cell is occupied by Dome/Worker";
            case -5:
                return prefix + "Unable to move enemy Worker";
            case -6:
                return prefix + "(Artemis) Same cell as the first one";
            case -7:
                return prefix + "(ForcedMovementGod) no space to move enemy worker";
            case 1:
                return "\n";
            case 2:
                return "Again";
            case 0:
                return "why are you here??!!! this is supposed to be an unreachable case!!! someone will be beat for this";
                // POCO PROFESSIONALE!
        }
        return "";
    }

    public String GetErrorBuild(int value)
    {
        switch (value)
        {
            case -1:
                return prefix + "Target cell out of board";
            case -2:
                return prefix + "Target cell is too far";
            case -3:
                return prefix + "Worker on the cell";
            case -4:
                return prefix + "Cell occupied by a dome";
            case -5:
                return prefix + "(Hephaestus) Not same as last built cell";
            case -6:
                return prefix + "(Hephaestus) Building is > 2";
            case -7:
                return prefix + "(Zeus) third level build forbidden";
            case -8:
                return prefix + "(Demeter) same as last built";
            case -9:
                return prefix + "Cell is a perimeter space";
            case -10:
                return prefix + "You Lost";
            case 1:
                return "\n";
            case 2:
                return "Again";
            case 0:
                return "why are you here??!!! this is supposed to be an unreachable case!!! someone will be beat for this";
        }
        return "";
    }
}
