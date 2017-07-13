package jurl.statemachine;

public interface State {

    static final String INIT = "state.init";

    static final String END = "state.end";

    String getName();

    Event handleEvent(Event event);
}
