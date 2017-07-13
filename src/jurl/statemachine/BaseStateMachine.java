package jurl.statemachine;

import java.util.HashMap;
import java.util.Map;

public class BaseStateMachine implements StateMachine {

    private String name;

    private Map<String, State> states;

    private State initState;

    private State endState;

    private State currentState;

    private State fromState;

    private Event lastEvent;

    public BaseStateMachine(String name) {

        this.name = name;

        states = new HashMap<>();
    }

    public BaseStateMachine init(String stateName) throws StateMachineException {
        return init(stateName, (event) -> { return event; } );
    }

    public BaseStateMachine init(String stateName, EventHandler eventHandler) throws StateMachineException {

        if (initState != null) {
            throw new StateMachineException("Init state already exists");
        }

        fromState = initState = newState(stateName, eventHandler);

        return this;
    }

    public BaseStateMachine from(String stateName, EventHandler eventHandler) throws StateMachineException {
        return this;
    }

    public BaseStateMachine from(String stateName) throws StateMachineException {
        return this;
    }

    public BaseStateMachine to(String stateName) throws StateMachineException {

        if (!states.containsKey(stateName)) {
            throw new StateMachineException(String.format("State '%s' must be created previously", stateName));
        }

        return this;
    }

    public BaseStateMachine to(String stateName, EventHandler eventHandler) throws StateMachineException {

        if (endState != null) {
            throw new StateMachineException("End state already exists");
        }

        State toState = newState(stateName, eventHandler);

        return this;
    }

    public BaseStateMachine when(String eventName) throws StateMachineException {
        return this;
    }

    public BaseStateMachine end(String stateName) throws StateMachineException {
        return init(stateName, (event) -> { return event; } );
    }

    public BaseStateMachine end(String stateName, EventHandler eventHandler) throws StateMachineException {

        if (endState != null) {
            throw new StateMachineException("End state already exists");
        }

        endState = newState(stateName, eventHandler);

        return this;
    }

    @Override
    public void commit() throws StateMachineException {

    }

    public State getState(String stateName) {
        return states.get(stateName);
    }

    public State getCurrentState() {
        return currentState;
    }

    public Event getLastEvent() {
        return lastEvent;
    }

    public boolean isRunning() {
        return isStarted() && !isFinished();
    }

    public boolean isStarted() {

        if (currentState == null) {
            return false;
        }

        return currentState != initState;
    }

    public boolean isFinished() {

        if (currentState == null) {
            return false;
        }

        return currentState == endState;
    }

    public State start() {
        return getCurrentState();
    }

    public State next() {
        return getCurrentState();
    }

    public State stop() {
        return getCurrentState();
    }

    public State rewind() {
        return getCurrentState();
    }

    public void run() {
    }

    private State newState(String stateName, EventHandler eventHandler) throws StateMachineException {

        if (states.containsKey(stateName)) {
            throw new StateMachineException(String.format("State '%s' already exists", stateName));
        }

        State state = new BaseState(stateName, eventHandler);

        return state;
    }
}
