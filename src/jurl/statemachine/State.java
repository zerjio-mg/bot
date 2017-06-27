package jurl.statemachine;

public interface State {

    String getName();

    Event getDefaultTriggerEvent();

    State setToState(State toState);

    State setToState(String stateName);

    Event run(Event event);
}
