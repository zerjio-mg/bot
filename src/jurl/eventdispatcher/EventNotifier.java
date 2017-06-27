package jurl.eventdispatcher;

import java.util.Collection;

public interface EventNotifier {

    void notifyByEvent(Event event, EventListenersContainer listeners);

    void notifyByEventType(Event event, EventListenersContainer listeners);

    void notify(Event event, Collection<EventListenerItem> listeners);
}
