package it.polimi.ingsw.view.GUI;

import javafx.event.Event;
import javafx.event.EventType;

public class ConnectionEvent extends CustomEvent{

    public static final EventType<CustomEvent> CUSTOM_EVENT_TYPE_CONNECTION = new EventType(CUSTOM_EVENT_TYPE, "CustomEventConnection");

    public ConnectionEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

    @Override
    public void invokeHandler(LauncherApp handler) {
        handler.onConnection();
    }
}
