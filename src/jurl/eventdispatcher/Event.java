package jurl.eventdispatcher;

public interface Event <T> {

    EventType getEventType();

    String getEventName();

    T getEventData();
}
