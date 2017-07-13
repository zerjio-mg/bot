package jurl.statemachine;

public class BaseEvent<T> implements Event {

    private String name;

    private T data;

    public BaseEvent(String name) {
        this(name, null);
    }

    public BaseEvent(String name, T data) {

        this.name = name;
        this.data = data;
    }
    public String getName() {
        return name;
    }

    public T getData() {
        return data;
    }
}
