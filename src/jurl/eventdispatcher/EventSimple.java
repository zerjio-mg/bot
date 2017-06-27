package jurl.eventdispatcher;

public class EventSimple<T> implements Event {

    private EventType eventType;

    private String eventName;

    private T eventData;

    public EventSimple(String eventName) {

        this(EventType.SIMPLE, eventName);
    }

    public EventSimple(EventType eventType, String eventName) {

        this(eventType, eventName, null);
    }

    public EventSimple(EventType eventType, String eventName, T eventData) {

        this.eventType = eventType;
        this.eventName = eventName;
        this.eventData = eventData;
    }

    @Override
    public EventType getEventType() {

        return eventType;
    }

    @Override
    public String getEventName() {

        return eventName;
    }

    @Override
    public T getEventData() {

        return eventData;
    }

    @Override
    public String toString() {

        return String.format("Event@%s[%s]", eventType, eventName);
    }
}
