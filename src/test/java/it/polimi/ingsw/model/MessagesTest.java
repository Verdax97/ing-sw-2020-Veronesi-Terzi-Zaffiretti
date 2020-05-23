package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

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
        Assertions.assertEquals(lobby, Messages.lobby);
        Assertions.assertEquals(nickname, Messages.nickname);
        Assertions.assertEquals(resume, Messages.resume);
        Assertions.assertEquals(start, Messages.start);
        Assertions.assertEquals(choseGods, Messages.choseGods);
        Assertions.assertEquals(choseYourGod, Messages.choseYourGod);
        Assertions.assertEquals(placeWorkers, Messages.placeWorkers);
        Assertions.assertEquals(selectWorker, Messages.selectWorker);
        Assertions.assertEquals(startTurn, Messages.startTurn);
        Assertions.assertEquals(beforeMove, Messages.beforeMove);
        Assertions.assertEquals(move, Messages.move);
        Assertions.assertEquals(moveAgain, Messages.moveAgain);
        Assertions.assertEquals(build, Messages.build);
        Assertions.assertEquals(buildAgain, Messages.buildAgain);
        Assertions.assertEquals(waitTurn, Messages.waitTurn);
        Assertions.assertEquals(error, Messages.error);
        Assertions.assertEquals(End, Messages.End);

    }
}