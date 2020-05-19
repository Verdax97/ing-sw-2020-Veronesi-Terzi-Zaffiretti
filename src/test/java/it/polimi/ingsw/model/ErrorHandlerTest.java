package it.polimi.ingsw.model;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ErrorHandlerTest {

    @Test
    public void GetErrorSetupTest(){
        ErrorHandler errorHandler = new ErrorHandler();
        assertEquals("GetErrorSetup value error", "You Lost", errorHandler.GetErrorSetup(-1));
        assertEquals("GetErrorSetup value error", "You Won!!", errorHandler.GetErrorSetup(1));
        assertEquals("GetErrorSetup value error", "", errorHandler.GetErrorSetup(0));
        int n = 15;
        assertEquals("GetErrorSetup value error", "Error generic", errorHandler.GetErrorSetup(n));

    }

    @Test
    public void GetErrorMoveTest(){
        ErrorHandler errorHandler = new ErrorHandler();
        assertEquals("GetErrorMove value error", "Error Target cell out of board", errorHandler.GetErrorMove(-1));
        assertEquals("GetErrorMove value error", "Error Target cell is too far", errorHandler.GetErrorMove(-2));
        assertEquals("GetErrorMove value error", "Error Cell is too high", errorHandler.GetErrorMove(-3));
        assertEquals("GetErrorMove value error", "Error Cell is occupied by Dome/Worker", errorHandler.GetErrorMove(-4));
        assertEquals("GetErrorMove value error", "Error Unable to move enemy Worker", errorHandler.GetErrorMove(-5));
        assertEquals("GetErrorMove value error", "Error (Artemis) Same cell as the first one", errorHandler.GetErrorMove(-6));
        assertEquals("GetErrorMove value error", "Error (ForcedMovementGod) no space to move enemy worker", errorHandler.GetErrorMove(-7));
        assertEquals("GetErrorMove value error", "\n", errorHandler.GetErrorMove(1));
        assertEquals("GetErrorMove value error", "Again", errorHandler.GetErrorMove(2));
        //testiamo anche le cose poco professionali per coverage
        errorHandler.GetErrorMove(0);
        errorHandler.GetErrorMove(15);
    }

    @Test
    public void GetErrorBuildTest(){
        ErrorHandler errorHandler = new ErrorHandler();
        assertEquals("GetErrorBuild value error", "Error Target cell out of board", errorHandler.GetErrorBuild(-1));
        assertEquals("GetErrorBuild value error", "Error Target cell is too far", errorHandler.GetErrorBuild(-2));
        assertEquals("GetErrorBuild value error", "Error Worker on the cell", errorHandler.GetErrorBuild(-3));
        assertEquals("GetErrorBuild value error", "Error Cell occupied by a dome", errorHandler.GetErrorBuild(-4));
        assertEquals("GetErrorBuild value error", "Error (Hephaestus) Not same as last built cell", errorHandler.GetErrorBuild(-5));
        assertEquals("GetErrorBuild value error", "Error (Hephaestus) Building is > 2", errorHandler.GetErrorBuild(-6));
        assertEquals("GetErrorBuild value error", "Error (Zeus) third level build forbidden", errorHandler.GetErrorBuild(-7));
        assertEquals("GetErrorBuild value error", "Error (Demeter) same as last built", errorHandler.GetErrorBuild(-8));
        assertEquals("GetErrorBuild value error", "Error Cell is a perimeter space", errorHandler.GetErrorBuild(-9));
        assertEquals("GetErrorBuild value error", "Error You Lost", errorHandler.GetErrorBuild(-10));
        assertEquals("GetErrorBuild value error", "\n", errorHandler.GetErrorBuild(1));
        assertEquals("GetErrorBuild value error", "Again", errorHandler.GetErrorBuild(2));
        // testiamo altre cose poco professionali per coverage
        errorHandler.GetErrorBuild(0);
        errorHandler.GetErrorBuild(15);
    }
}
