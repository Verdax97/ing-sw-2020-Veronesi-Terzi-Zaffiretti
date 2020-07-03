package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorHandlerTest {

    @Test
    public void GetErrorSetupTest() {
        ErrorHandler errorHandler = new ErrorHandler();
        Assertions.assertEquals("You Lost", errorHandler.getErrorSetup(-1));
        Assertions.assertEquals("You Won!!", errorHandler.getErrorSetup(1));
        Assertions.assertEquals("", errorHandler.getErrorSetup(0));
        Assertions.assertEquals("Error No space to move the worker", errorHandler.getErrorSetup(-7));
        int n = 15;
        Assertions.assertEquals("Error generic", errorHandler.getErrorSetup(n));

    }

    @Test
    public void GetErrorMoveTest() {
        ErrorHandler errorHandler = new ErrorHandler();
        Assertions.assertEquals("Error Target cell out of board", errorHandler.getErrorMove(-1));
        Assertions.assertEquals("Error Target cell is too far", errorHandler.getErrorMove(-2));
        Assertions.assertEquals("Error Cell is too high", errorHandler.getErrorMove(-3));
        Assertions.assertEquals("Error Cell is occupied by Dome/Worker", errorHandler.getErrorMove(-4));
        Assertions.assertEquals("Error Unable to move enemy Worker", errorHandler.getErrorMove(-5));
        Assertions.assertEquals("Error (Artemis) Same cell as the first one", errorHandler.getErrorMove(-6));
        Assertions.assertEquals("Error (ForcedMovementGod) no space to move enemy worker", errorHandler.getErrorMove(-7));
        Assertions.assertEquals("\n", errorHandler.getErrorMove(1));
        Assertions.assertEquals("Again", errorHandler.getErrorMove(2));
        //testiamo anche le cose poco professionali per coverage
        errorHandler.getErrorMove(0);
        errorHandler.getErrorMove(15);
    }

    @Test
    public void GetErrorBuildTest() {
        ErrorHandler errorHandler = new ErrorHandler();
        Assertions.assertEquals("Error Target cell out of board", errorHandler.getErrorBuild(-1));
        Assertions.assertEquals("Error Target cell is too far", errorHandler.getErrorBuild(-2));
        Assertions.assertEquals("Error Worker on the cell", errorHandler.getErrorBuild(-3));
        Assertions.assertEquals("Error Cell occupied by a dome", errorHandler.getErrorBuild(-4));
        Assertions.assertEquals("Error (Hephaestus) Not same as last built cell", errorHandler.getErrorBuild(-5));
        Assertions.assertEquals("Error (Hephaestus) Building is > 2", errorHandler.getErrorBuild(-6));
        Assertions.assertEquals("Error (Zeus) third level build forbidden", errorHandler.getErrorBuild(-7));
        Assertions.assertEquals("Error (Demeter) same as last built", errorHandler.getErrorBuild(-8));
        Assertions.assertEquals("Error Cell is a perimeter space", errorHandler.getErrorBuild(-9));
        Assertions.assertEquals("Error You Lost", errorHandler.getErrorBuild(-10));
        Assertions.assertEquals("\n", errorHandler.getErrorBuild(1));
        Assertions.assertEquals("Again", errorHandler.getErrorBuild(2));
        // testiamo altre cose poco professionali per coverage
        errorHandler.getErrorBuild(0);
        errorHandler.getErrorBuild(15);
    }
}
