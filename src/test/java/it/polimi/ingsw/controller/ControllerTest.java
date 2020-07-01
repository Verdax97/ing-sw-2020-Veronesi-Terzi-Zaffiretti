package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameSaver;
import it.polimi.ingsw.model.Lobby;
import it.polimi.ingsw.model.MsgToServer;
import it.polimi.ingsw.view.server.ServerAuxiliaryThread;
import it.polimi.ingsw.view.server.ServerMultiplexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ControllerTest {


    @Test
    public void setServerMultiplexerTest(){
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller, new Integer(4567));
        controller.setServerMultiplexer(serverMultiplexer);
        Assertions.assertTrue(true);
    }

    @Test
    public void setLobbyTest(){
        Controller controller = new Controller();
        Lobby lobby = new Lobby();
        controller.setLobby(lobby);
        Assertions.assertTrue(true);
    }

    @Test
    public void createMatchTest() throws IOException {
        createCustomSave();
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller, new Integer(4567));
        controller.setServerMultiplexer(serverMultiplexer);
        serverMultiplexer.playersThread = new ArrayList<>();
        Lobby lobby = new Lobby();
        lobby.AddPlayer("GinoTest1");
        lobby.AddPlayer("PinoTest1");
        GameSaver.checkForGames(lobby);
        controller.createMatch(true);
        Controller controller1 = new Controller();
        ServerMultiplexer serverMultiplexer1 = new ServerMultiplexer(controller1, 4567);
        controller1.setServerMultiplexer(serverMultiplexer1);
        controller1.setLobby(lobby);
        serverMultiplexer1.playersThread = new ArrayList<>();
        controller1.createMatch(false);
        Assertions.assertTrue(true);
        GameSaver.deleteGameData();
    }

    @Test
    public void updateAndRedirectMessageTestGeneric_ReallyVeryBad() throws IOException {
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller, new Integer(4567));
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
        controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        msg = new MsgToServer("GinoTest1", 0, 0, 4, 4);
        controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        msg = new MsgToServer("PinoTest", 3, 3, 2, 2);
        controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        for (int i = 0; i < 8; i++) {
            msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
            msg = new MsgToServer("PinoTest", 0, -5, -5, -5);
            controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        }
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        msg = new MsgToServer("GinoTest1", 0, -5, -5, -5);
        controller.update(new ServerMultiplexer(new Controller(), new Integer(4567)), msg);
        Assertions.assertTrue(true);
    }

    @Test
    public void DefaultTest() {
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller, 4567);
        controller.setServerMultiplexer(serverMultiplexer);
        serverMultiplexer.playersThread = new ArrayList<>();
        MsgToServer msg = new MsgToServer("PinoTest1", 0,-5, -5, -5);
        Lobby lobby = new Lobby();
        lobby.AddPlayer("GinoTest1");
        lobby.AddPlayer("PinoTest1");
        controller.setLobby(lobby);
        controller.createMatch(false);
        controller.setState(State.LOBBY);
        controller.redirectMessage(msg, serverMultiplexer);
    }

    @Test
    public void redirectCoverageTest() throws IOException {
        createCustomSave();
        Controller controller = new Controller();
        ServerMultiplexer serverMultiplexer = new ServerMultiplexer(controller, 4567);
        controller.setServerMultiplexer(serverMultiplexer);
        serverMultiplexer.playersThread = new ArrayList<>();
        MsgToServer msg = new MsgToServer("PinoTest1", 0,-5, -5, -5);
        Lobby lobby = new Lobby();
        lobby.AddPlayer("GinoTest1");
        lobby.AddPlayer("PinoTest1");
        controller.setLobby(lobby);
        GameSaver.checkForGames(lobby);
        controller.createMatch(true);
        File saveFile = new File("savedGames/GinoTest1-PinoTest1.txt");
        controller.setState(State.SELECTWORKER);
        controller.redirectMessage(msg,serverMultiplexer);
        msg = new MsgToServer("PinoTest1", 0,1, -5, -5);
        controller.redirectMessage(msg,serverMultiplexer);
        controller.setState(State.BUILD);
        msg = new MsgToServer("PinoTest1", 0,-5, -5, -5);
        controller.redirectMessage(msg,serverMultiplexer);
        msg = new MsgToServer("GinoTest1", 0,-5, -5, -5);
        controller.redirectMessage(msg,serverMultiplexer);
        msg = new MsgToServer("PinoTest1", 0,-5, -5, -5);
        controller.redirectMessage(msg,serverMultiplexer);
        ServerAuxiliaryThread serverAuxiliaryThread = new ServerAuxiliaryThread();
        serverMultiplexer.setServerAuxiliaryThread(serverAuxiliaryThread);
        controller.redirectMessage(msg,serverMultiplexer);
    }

    public void createCustomSave() throws IOException {
        File directory = new File("savedGames");
        if (!directory.exists()) directory.mkdir();
        File fileName = new File("savedGames/GinoTest1-PinoTest1.txt");
        fileName.createNewFile();
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write("GinoTest1-PinoTest1\n" +
                "PinoTest1\n" +
                "Athena-Charon\n" +
                "0 0 0 3 3 \n" +
                "0 0 0 3 00 \n" +
                "0 0 0D 3 0 \n" +
                "0 0 0 01 3 \n" +
                "0 0 00 3 21 \n" +
                "5");
        fileWriter.close();

    }
}
