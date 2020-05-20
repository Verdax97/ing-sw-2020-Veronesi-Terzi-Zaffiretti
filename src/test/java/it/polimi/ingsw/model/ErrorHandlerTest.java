package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorHandlerTest {

    @Test
    public void GetErrorSetupTest() {
        ErrorHandler errorHandler = new ErrorHandler();
        Assertions.assertEquals("You Lost", errorHandler.GetErrorSetup(-1));
        Assertions.assertEquals("You Won!!", errorHandler.GetErrorSetup(1));
        Assertions.assertEquals("", errorHandler.GetErrorSetup(0));
        int n = 15;
        Assertions.assertEquals("Error generic", errorHandler.GetErrorSetup(n));

    }

    @Test
    public void GetErrorMoveTest() {
        ErrorHandler errorHandler = new ErrorHandler();
        Assertions.assertEquals("Error Target cell out of board", errorHandler.GetErrorMove(-1));
        Assertions.assertEquals("Error Target cell is too far", errorHandler.GetErrorMove(-2));
        Assertions.assertEquals("Error Cell is too high", errorHandler.GetErrorMove(-3));
        Assertions.assertEquals("Error Cell is occupied by Dome/Worker", errorHandler.GetErrorMove(-4));
        Assertions.assertEquals("Error Unable to move enemy Worker", errorHandler.GetErrorMove(-5));
        Assertions.assertEquals("Error (Artemis) Same cell as the first one", errorHandler.GetErrorMove(-6));
        Assertions.assertEquals("Error (ForcedMovementGod) no space to move enemy worker", errorHandler.GetErrorMove(-7));
        Assertions.assertEquals("\n", errorHandler.GetErrorMove(1));
        Assertions.assertEquals("Again", errorHandler.GetErrorMove(2));
        //testiamo anche le cose poco professionali per coverage
        errorHandler.GetErrorMove(0);
        errorHandler.GetErrorMove(15);
    }

    @Test
    public void GetErrorBuildTest() {
        ErrorHandler errorHandler = new ErrorHandler();
        Assertions.assertEquals("Error Target cell out of board", errorHandler.GetErrorBuild(-1));
        Assertions.assertEquals("Error Target cell is too far", errorHandler.GetErrorBuild(-2));
        Assertions.assertEquals("Error Worker on the cell", errorHandler.GetErrorBuild(-3));
        Assertions.assertEquals("Error Cell occupied by a dome", errorHandler.GetErrorBuild(-4));
        Assertions.assertEquals("Error (Hephaestus) Not same as last built cell", errorHandler.GetErrorBuild(-5));
        Assertions.assertEquals("Error (Hephaestus) Building is > 2", errorHandler.GetErrorBuild(-6));
        Assertions.assertEquals("Error (Zeus) third level build forbidden", errorHandler.GetErrorBuild(-7));
        Assertions.assertEquals("Error (Demeter) same as last built", errorHandler.GetErrorBuild(-8));
        Assertions.assertEquals("Error Cell is a perimeter space", errorHandler.GetErrorBuild(-9));
        Assertions.assertEquals("Error You Lost", errorHandler.GetErrorBuild(-10));
        Assertions.assertEquals("\n", errorHandler.GetErrorBuild(1));
        Assertions.assertEquals("Again", errorHandler.GetErrorBuild(2));
        // testiamo altre cose poco professionali per coverage
        errorHandler.GetErrorBuild(0);
        errorHandler.GetErrorBuild(15);
    }
}
