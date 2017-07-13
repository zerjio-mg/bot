package jurl.statemachine;

import java.util.HashMap;
import java.util.Map;

public class BaseState implements State {

    private String name;

    private EventHandler eventHandler;

    private Map<Event, State> fromStates;

    private Map<Event, State> toStates;

    public BaseState(String name, EventHandler eventHandler) {

        this.name = name;
        this.eventHandler = eventHandler;

        fromStates = new HashMap<>();
        toStates = new HashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Event handleEvent(Event event) {

        return eventHandler.handleEvent(event);
    }
}
