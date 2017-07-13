package jurl.statemachine;

public interface Event<T> {

    static final String START = "event.start";

    static final String FINISH = "event.finish";

    String getName();

    T getData();
}
