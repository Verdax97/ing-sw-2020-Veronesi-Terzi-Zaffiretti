package it.polimi.ingsw.view.GUI;

import javafx.event.Event;
import javafx.event.EventType;

public class ConnectionEvent extends CustomEvent {

    public static final EventType<CustomEvent> CUSTOM_EVENT_TYPE_1 = new EventType(CUSTOM_EVENT_TYPE, "CustomEvent1");


    public ConnectionEvent() {
        super(CUSTOM_EVENT_TYPE_1);
    }

    @Override
    public void invokeHandler(LauncherApp handler) {
        handler.onConnection();
    }
}
