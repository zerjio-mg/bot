package jurl.eventdispatcher;

import java.util.LinkedHashMap;
import java.util.Map;

public class EventDispatcherSimple implements EventDispatcher {

    private Map<String, EventListenerItem> listenersToAll;

    private EventListenersContainer<EventType> listenersByEventType;

    private EventListenersContainer<String> listenersByEvent;

    private EventNotifier eventNotifier;

    public EventDispatcherSimple() {

        this(new EventNotifierSimple());
    }

    public EventDispatcherSimple(EventNotifier eventNotifier) {

        this.eventNotifier = eventNotifier;

        listenersToAll = new LinkedHashMap();
        listenersByEventType = new EventListenersContainer();
        listenersByEvent = new EventListenersContainer();
    }

    public void subscribeToAllEvents(String listenerName, EventListener listener) {

        if (listenersToAll.containsKey(listenerName)) {
            return;
        }

        listenersToAll.put(listenerName, new EventListenerItem(listenerName, listener));
    }

    public void unsubscribeFromAllEvents(String listenerName) {

        listenersToAll.remove(listenerName);
    }

    public void subscribeToEventType(String listenerName, EventListener listener, EventType eventType) {

        listenersByEventType.addListener(eventType, new EventListenerItem(listenerName, listener));
    }

    public void unsubscribeFromEventType(String listenerName, EventType eventType) {

        listenersByEventType.removeListener(eventType, listenerName);
    }

    public void subscribeToEvent(String listenerName, EventListener listener, String event) {

        listenersByEvent.addListener(event, new EventListenerItem(listenerName, listener));
    }

    public void unsubscribeFromEvent(String listenerName, String event) {

        listenersByEvent.removeListener(event, listenerName);
    }

    public void notify(Event event) {

        eventNotifier.notifyByEvent(event, listenersByEvent);
        eventNotifier.notifyByEventType(event, listenersByEventType);
        eventNotifier.notify(event, listenersToAll.values());
    }
}
