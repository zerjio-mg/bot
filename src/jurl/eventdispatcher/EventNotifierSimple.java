package jurl.eventdispatcher;

import java.util.Collection;

public class EventNotifierSimple implements EventNotifier {

    private EventNotificationErrorHandler errorHandler;

    public EventNotifierSimple() {

        this(new EventNotificationErrorHandlerSimple());
    }

    public EventNotifierSimple(EventNotificationErrorHandler errorHandler) {

        this.errorHandler = errorHandler;
    }

    public void notifyByEvent(Event event, EventListenersContainer listeners) {

        notify(event, listeners.getListeners(event.getEventName()));
    }

    public void notifyByEventType(Event event, EventListenersContainer listeners) {

        notify(event, listeners.getListeners(event.getEventType()));
    }

    public void notify(Event event, Collection<EventListenerItem> listeners) {

        for(EventListenerItem listener : listeners) {

            try {
                listener.notifyEvent(event);
            } catch (Exception exception) {
                errorHandler.notificationError(event, listener, exception);
            }
        }
    }
}
