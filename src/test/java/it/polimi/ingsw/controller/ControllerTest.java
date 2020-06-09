package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameSaver;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.MsgToServer;
import it.polimi.ingsw.view.server.ServerMultiplexer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerTest {


    @Test
    public void setServerMultiplexerTest(){
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller);
        controller.setServerMultiplexer(serverMultiplexer);
    }

    @Test
    public void setLobbyTest(){
        Controller controller = new Controller();
        Lobby lobby = new Lobby();
        controller.setLobby(lobby);
    }

    @Test
    public void createMatchTest() throws IOException {
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller);
        controller.setServerMultiplexer(serverMultiplexer);
        serverMultiplexer.playersThread = new ArrayList<>();
        Lobby lobby = new Lobby();
        lobby.AddPlayer("GinoTest");
        lobby.AddPlayer("PinoTest");
        GameSaver.checkForGames(lobby);
        controller.createMatch(true);


        Controller controller1 = new Controller();
        ServerMultiplexer serverMultiplexer1 = new ServerMultiplexer(controller1);
        controller1.setServerMultiplexer(serverMultiplexer1);
        controller1.setLobby(lobby);
        serverMultiplexer1.playersThread = new ArrayList<>();
        controller1.createMatch(false);
    }

    @Test
    public void updateAndRedirectMessageTest() throws IOException {
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller);
        controller.setServerMultiplexer(serverMultiplexer);
        Lobby lobby = new Lobby();
        lobby.AddPlayer("GinoTest1");
        lobby.AddPlayer("PinoTest");
        lobby.setnPlayer(2);
        GameSaver.checkForGames(lobby);
        serverMultiplexer.playersThread = new ArrayList<>();
        controller.setLobby(lobby);
        controller.createMatch(false);
        MsgToServer msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("GinoTest1", 0, 0, 4, 4);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("PinoTest", 3, 3, 2, 2);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller()), msg);
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller()), msg);


    }

}
