package jurl.eventdispatcher;

public interface EventDispatcher {

    void subscribeToAllEvents(String listenerName, EventListener listener);

    void unsubscribeFromAllEvents(String listenerName);

    void subscribeToEventType(String listenerName, EventListener listener, EventType eventType);

    void unsubscribeFromEventType(String listenerName, EventType eventType);

    void subscribeToEvent(String listenerName, EventListener listener, String event);

    void unsubscribeFromEvent(String listenerName, String event);

    void notify(Event event);
}
