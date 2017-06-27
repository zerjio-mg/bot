package jurl.eventdispatcher;

import java.util.*;

public class EventListenersContainer <T> {

    private static List<EventListenerItem> emptyListenersList = new ArrayList();

    private Map<T, Map<String, EventListenerItem>> mapToListeners;

    public EventListenersContainer() {

        mapToListeners = new HashMap();
    }

    public void addListener(T key, EventListenerItem listener) {

        Map<String, EventListenerItem> listeners = mapToListeners.get(key);

        if (listeners == null) {
            listeners = new LinkedHashMap();
            mapToListeners.put(key, listeners);
        }

        if (listeners.containsKey(listener.getListenerName())) {
            return;
        }

        listeners.put(listener.getListenerName(), listener);
    }

    public void removeListener(T key, String listenerName) {

        Map<String, EventListenerItem> listeners = mapToListeners.get(key);

        if (listeners != null) {
            listeners.remove(listenerName);
        }
    }

    public Collection<EventListenerItem> getListeners(T key) {

        Map<String, EventListenerItem> listeners = mapToListeners.get(key);

        if (listeners != null) {
            return emptyListenersList;
        }

        return listeners.values();
    }
}
