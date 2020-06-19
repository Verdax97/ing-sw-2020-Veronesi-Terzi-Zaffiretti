package it.polimi.ingsw.model;


import it.polimi.ingsw.model.gods.Hephaestus;
import it.polimi.ingsw.model.gods.Hestia;
import it.polimi.ingsw.model.gods.Prometheus;
import it.polimi.ingsw.model.gods.Triton;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;

public class MatchTest {

    @Test
    public void getLastActionTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        Assertions.assertEquals(0, match.getLastAction());
    }

    @Test
    public void getPlayerTurnTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);

        match.StartGame();
        Assertions.assertEquals(players.get(1), match.getPlayerTurn().getNickname());
    }

    @Test
    public void getSetupTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        match.getSetup();
        Assertions.assertTrue(true);
    }

    @Test
    public void getBoard(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        Board board = new Board();
        match.setBoard(board);
        Assertions.assertEquals(board, match.getBoard());
    }

    @Test
    public void pickGodTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        match.StartGame();
        MsgToServer msg50 = new MsgToServer("Pino", 50, 0, 0, 0 );
        match.PickGod(msg50);
        Assertions.assertEquals(-1, match.getLastAction());
        match.PickGod(msg50);
        Assertions.assertEquals(-1, match.getLastAction());
        MsgToServer msg1 = new MsgToServer("Pino", 1, 0, 0, 0 );
        match.PickGod(msg1);
        Assertions.assertEquals(1, match.getLastAction());
        MsgToServer msg2 = new MsgToServer("Pino", 2, 0, 0, 0 );
        match.PickGod(msg2);
        Assertions.assertEquals(1, match.getLastAction());
    }

    @Test
    public void PlaceWorkerTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);

        match.StartGame();
        God god1 = new God();
        God god2 = new God();
        match.getPlayers().get(0).setGodPower(god1);
        match.getPlayers().get(1).setGodPower(god2);
        MsgToServer msg0511 = new MsgToServer("Pino", 0, 5, 1, 1);
        match.PlaceWorker(msg0511);
        Assertions.assertEquals(-1, match.getLastAction());
        MsgToServer msg0000 = new MsgToServer("Pino", 0, 0, 0, 0);
        match.PlaceWorker(msg0000);
        Assertions.assertEquals(-1, match.getLastAction());
        MsgToServer msg0011 = new MsgToServer("Pino", 0, 0, 1, 1);
        match.PlaceWorker(msg0011);
        Assertions.assertEquals(1, match.getLastAction());
        MsgToServer msg4433 = new MsgToServer("Pino", 4, 4, 3, 3);
        match.PlaceWorker(msg4433);
        Assertions.assertEquals(2, match.getLastAction());
    }

    @Test
    public void SelectGodPlayerTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);

        match.StartGame();
        MsgToServer msg = new MsgToServer("Gino", 1, 0, 0, 0);
        match.PickGod(msg);
        match.PickGod(msg);
        MsgToServer msg1 = new MsgToServer("Gino", -1, 0, 0, 0);
        match.SelectPlayerGod(msg1);
        Assertions.assertEquals(-1, match.getLastAction());
        msg = new MsgToServer("Gino", 0, 0, 0, 0);
        match.SelectPlayerGod(msg);
        Assertions.assertEquals(1, match.getLastAction());
        match.SelectPlayerGod(msg);
        Assertions.assertEquals(1, match.getLastAction());
    }

    @Test
    public void StartTurnTest() throws IOException {
        ArrayList<String> players = new ArrayList<>();
        Lobby lobby = new Lobby();
        players.add("Gino");
        players.add("Pino");
        lobby.AddPlayer(players.get(0));
        lobby.AddPlayer(players.get(1));
        Match match = new Match(players);
        match.StartGame();
        God god = new Hestia();
        God god1 = new Hestia();
        match.getPlayers().get(0).setGodPower(god);
        match.getPlayers().get(1).setGodPower(god1);
        MsgToServer msg = new MsgToServer("Pino", 0, 0, 2, 2);
        match.PlaceWorker(msg);
        msg = new MsgToServer("Pino", 4, 4, 3, 3);
        match.PlaceWorker(msg);
        GameSaver.checkForGames(lobby);
        match.StartTurn();
        Assertions.assertEquals(0, match.getLastAction());
        match.getBoard().getCell(1, 0).setBuilding(3);
        match.getBoard().getCell(1,1).setBuilding(3);
        match.getBoard().getCell(0,1).setBuilding(3);
        match.getBoard().getCell(2, 2).setWorker(null);
        match.StartTurn();
        Assertions.assertEquals(-10, match.getLastAction());
        match.getBoard().getCell(0, 0).setWorker(match.getBoard().getCell(4, 4).getWorker());
        match.getBoard().getCell(4,4).setWorker(null);
        match.getBoard().getCell(0,0).getWorker().setPlayer(match.getPlayerTurn());
        match.StartTurn();
        Assertions.assertEquals(1, match.getLastAction());
    }

    @Test
    public void MoveTest() throws IOException {
        ArrayList<String> players = new ArrayList<>();
        Lobby lobby = new Lobby();
        players.add("Gino");
        players.add("Pino");
        lobby.AddPlayer(players.get(0));
        lobby.AddPlayer(players.get(1));
        Match match = new Match(players);
        match.StartGame();
        MsgToServer msgPacket = new MsgToServer("Pino", 0, -5, -5, -5);
        Worker worker = new Worker();
        worker.setPlayer(match.getPlayerTurn());
        Worker worker1 = new Worker();
        worker1.setPlayer(match.getPlayers().get(0));
        Triton triton = new Triton();
        Hephaestus hephaestus = new Hephaestus();
        match.getPlayerTurn().setGodPower(hephaestus);
        match.getBoard().getCell(1, 1).setWorker(worker);
        match.getPlayers().get(0).setGodPower(hephaestus);
        match.getBoard().getCell(3, 3).setWorker(worker1);
        GameSaver.checkForGames(lobby);
        match.StartTurn();
        match.SelectWorker(msgPacket);
        msgPacket = new MsgToServer("Pino", 4, -5, 1, 1);
        match.Move(msgPacket);
        Assertions.assertEquals(1, match.getLastAction());
        msgPacket = new MsgToServer("Pino", 0, -5, 1, 1);
        match.Move(msgPacket);
        Assertions.assertEquals(1, match.getLastAction());
        msgPacket = new MsgToServer("Pino", 2, -5, 1, 1);
        match.Move(msgPacket);
        Assertions.assertEquals(1, match.getLastAction());
        triton.ResetGod();
        match.getBoard().getCell(0,1).setBuilding(3);
        match.getBoard().getCell(1,0).setBuilding(2);
        msgPacket = new MsgToServer("Pino", 1,-5,-5,-5);
        match.Move(msgPacket);
        Assertions.assertEquals(10, match.getLastAction());
    }

    @Test
    public void BuildTest() throws IOException {
        ArrayList<String> players = new ArrayList<>();
        Lobby lobby = new Lobby();
        players.add("Gino");
        players.add("Pino");
        lobby.AddPlayer(players.get(0));
        lobby.AddPlayer(players.get(1));
        Match match = new Match(players);
        match.StartGame();
        MsgToServer msgPacket = new MsgToServer("Pino", 0, -5, -5, -5);
        Worker worker = new Worker();
        worker.setPlayer(match.getPlayerTurn());
        Worker worker1 = new Worker();
        worker1.setPlayer(match.getPlayers().get(0));
        Hephaestus hephaestus = new Hephaestus();
        God god = new Hestia();
        match.getPlayerTurn().setGodPower(hephaestus);
        match.getBoard().getCell(1, 1).setWorker(worker);
        match.getPlayers().get(0).setGodPower(god);
        match.getBoard().getCell(3,3).setWorker(worker1);
        GameSaver.checkForGames(lobby);
        match.StartTurn();
        match.SelectWorker(msgPacket);
        match.Build(msgPacket);
        Assertions.assertEquals(2, match.getLastAction());
        msgPacket = new MsgToServer("Pino", 0,1,-5,-5);
        match.Build(msgPacket);
        Assertions.assertEquals(1, match.getLastAction());
        match.NextPlayer();
        msgPacket = new MsgToServer("Pino", 0,0,-5,-5);
        match.Build(msgPacket);
        Assertions.assertEquals(1, match.getLastAction());
        match.NextPlayer();
        hephaestus.ResetGod();
        msgPacket = new MsgToServer("Pino", -1,1,-5,-5);
        match.Build(msgPacket);
        Assertions.assertEquals(-1, match.getLastAction());
        msgPacket = new MsgToServer("Pino", -1,-5,-5,-5);
        match.Build(msgPacket);
        Assertions.assertEquals(-1, match.getLastAction());
        msgPacket = new MsgToServer("Pino", 0,-5,-5,-5);
        worker.setLastMovement(1);
        match.getBoard().getCell(1,1).setBuilding(3);
        match.Build(msgPacket);
        Assertions.assertEquals(10, match.getLastAction());
        match.getBoard().getCell(3, 3).setWorker(null);
        match.getBoard().getCell(0,0).setBuilding(-1);
        match.getBoard().getCell(0,0).setBuilding(2);
        msgPacket = new MsgToServer("Pino", 0,-5,-5,-5);
        match.getBoard().getCell(1,0).setDome(true);
        match.getBoard().getCell(0,0).setDome(true);
        match.getBoard().getCell(0,1).setDome(true);
        match.getBoard().getCell(2,2).setDome(true);
        match.getBoard().getCell(2,1).setDome(true);
        match.getBoard().getCell(1,2).setDome(true);
        match.getBoard().getCell(0,2).setDome(true);
        match.getBoard().getCell(2,0).setDome(true);
        match.Build(msgPacket);
        Assertions.assertEquals(-10, match.getLastAction());
    }

    @Test
    public void PacketTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        match.getBoard().getCell(0, 0).setDome(true);

        match.SendPacket("Gino", "Test", "TestAlt", null);
        match.SendPacket("Gino", "Test", "TestAlt", match.getBoard());
    }

    @Test
    public void setBoardTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        Board board = new Board();
        match.setBoard(board);
        Assertions.assertEquals(board, match.getBoard());
    }

    @Test
    public void SelectWorkerTest() {
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        MsgToServer msg = new MsgToServer("Pino", 0, 0, 0, 0);
        match.StartGame();
        match.PlaceWorker(msg);
        match.getPlayers().get(0).setGodPower(new God());
        match.getPlayers().get(1).setGodPower(new God());
        Assertions.assertEquals(-1, match.getLastAction(), "PlaceWorker error");
        msg = new MsgToServer("Pino", 0, 0, 1, 1);
        match.PlaceWorker(msg);
        Assertions.assertEquals(1, match.getLastAction(), "PlaceWorker error");
        match.NextPlayer();
        msg = new MsgToServer("Pino", 2, -5, -5, -5);
        match.SelectWorker(msg);
        Assertions.assertEquals(-1, match.getLastAction(), "SelectWorker error");
        msg = new MsgToServer("Pino", 1, -5, -5, -5);
        match.SelectWorker(msg);
        Assertions.assertEquals(2, match.getLastAction(), "SelectWorker error");
    }

    @Test
    public void getMsgErrorTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        Assertions.assertEquals("InitialMsgError", match.getMsgError());
    }

    @Test
    public void BeforeMoveTest(){
        ArrayList<String> players = new ArrayList<>();
        players.add("Gino");
        players.add("Pino");
        Match match = new Match(players);
        match.StartGame();
        MsgToServer msg = new MsgToServer("Pino", 0,0,1,1);
        match.getPlayerTurn().setGodPower(new God());
        match.PlaceWorker(msg);
        msg = new MsgToServer("Gino", 4,4,3,3);
        match.getPlayerTurn().setGodPower(new God());
        match.PlaceWorker(msg);
        msg = new MsgToServer("Pino", 0,0,-5,-5);
        match.SelectWorker(msg);
        match.BeforeMove(msg);
        Assertions.assertEquals(1, match.getLastAction());
        msg = new MsgToServer("Pino", 0,1,-5,-5);
        match.getPlayerTurn().setGodPower(new Prometheus());
        match.BeforeMove(msg);
        Assertions.assertEquals(1, match.getLastAction());

    }
}
