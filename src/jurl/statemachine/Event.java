package jurl.statemachine;

public interface Event<T> {

    String getName();

    T getData();
}
