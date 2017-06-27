package jurl.statemachine;

public interface StateMachine {

    State setInitState(State state);

    State setFinishState(State state);

    State addState(State state);

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
