package it.polimi.ingsw.model;

public class ErrorHandler {

    public String GetErrorSetup(int value)
    {
        switch (value)
        {
            case -1:
                return "You Lost";
            case 1:
                return "You Won!!";
            case 0:
                return "";
        }
        return "";
    }
    public String GetErrorMove(int value)
    {
        switch (value)
        {
            case -1:
                return "Target cell out of board";
            case -2:
                return "Target cell is too far";
            case -3:
                return "Cell is too high";
            case -4:
                return "Cell is occupied by Dome/Worker";
            case -5:
                return "Unable to move enemy Worker";
            case -6:
                return "(Artemis) Same cell as the first one";
            case -7:
                return "(ForcedMovementGod) no space to move enemy worker";
            case 1:
                return "";
            case 2:
                return "Again";
            case 0:
                return "why are you here??!!! this is supposed to be an unreachable case!!! someone will be beat for this";
        }
        return "";
    }

    public String GetErrorBuild(int value)
    {
        switch (value)
        {
            case -1:
                return "Target cell out of board";
            case -2:
                return "Target cell is too far";
            case -3:
                return "Worker on the cell";
            case -4:
                return "Cell occupied by a dome";
            case -5:
                return "(Hephaestus) Not same as last built cell";
            case -6:
                return "(Hephaestus) Building is > 2";
            case -7:
                return "(Zeus) third level build forbidden";
            case -8:
                return "(Demeter) same as last built";
            case -9:
                return "cell is a perimeter space";
            case -10:
                return "You Lost";
            case 1:
                return "";
            case 2:
                return "Again";
            case 0:
                return "why are you here??!!! this is supposed to be an unreachable case!!! someone will be beat for this";
        }
        return "";
    }
}
