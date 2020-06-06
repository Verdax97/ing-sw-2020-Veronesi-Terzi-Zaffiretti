package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessagesTest {
    @Test
    public void Test() {
        Messages msg = new Messages();
        String lobby = "Lobby";
        String nickname = "Insert Nickname";
        String resume = "Resume";
        String start = "Start";
        String choseGods = "choseGods";
        String choseYourGod = "choseYourGod";
        String placeWorkers = "Place";
        String selectWorker = "SelectWorker";
        String startTurn = "StartTurn";
        String beforeMove = "BeforeMove";
        String move = "Move";
        String moveAgain = "Move Again";
        String build = "Build";
        String buildAgain = "Build Again";
        String waitTurn = "Wait";
        String error = "Error";
        String End = "End";
        Assertions.assertEquals(lobby, Messages.LOBBY);
        Assertions.assertEquals(nickname, Messages.INSERT_NICKNAME);
        Assertions.assertEquals(resume, Messages.RESUME);
        Assertions.assertEquals(start, Messages.START);
        Assertions.assertEquals(choseGods, Messages.CHOSE_GODS);
        Assertions.assertEquals(choseYourGod, Messages.CHOSE_YOUR_GOD);
        Assertions.assertEquals(placeWorkers, Messages.PLACE_WORKERS);
        Assertions.assertEquals(selectWorker, Messages.SELECT_WORKER);
        Assertions.assertEquals(startTurn, Messages.START_TURN);
        Assertions.assertEquals(beforeMove, Messages.BEFORE_MOVE);
        Assertions.assertEquals(move, Messages.MOVE);
        Assertions.assertEquals(moveAgain, Messages.MOVE_AGAIN);
        Assertions.assertEquals(build, Messages.BUILD);
        Assertions.assertEquals(buildAgain, Messages.BUILD_AGAIN);
        Assertions.assertEquals(waitTurn, Messages.WAIT_TURN);
        Assertions.assertEquals(error, Messages.ERROR);
        Assertions.assertEquals(End, Messages.END);

    }
}