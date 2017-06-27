package jurl.eventdispatcher;

public interface EventNotificationErrorHandler {

    void notificationError(Event event, EventListenerItem listener, Exception exception);
}
