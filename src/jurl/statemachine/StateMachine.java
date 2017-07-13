package jurl.statemachine;

public interface StateMachine {

    StateMachine init(String stateName) throws StateMachineException;

    StateMachine init(String stateName, EventHandler eventHandler) throws StateMachineException;

    StateMachine from(String stateName) throws StateMachineException;

    StateMachine from(String stateName, EventHandler eventHandler) throws StateMachineException;

    StateMachine to(String stateName) throws StateMachineException;

    StateMachine to(String stateName, EventHandler eventHandler) throws StateMachineException;

    StateMachine when(String eventName) throws StateMachineException;

    StateMachine end(String stateName) throws StateMachineException;

    StateMachine end(String stateName, EventHandler eventHandler) throws StateMachineException;

    void commit() throws StateMachineException;

    State getState(String name);

    State getCurrentState();

    Event getLastEvent();

    boolean isRunning();

    boolean isStarted();

    boolean isFinished();

    State start();

    State next();

    State stop();

    State rewind();

    void run();
}
