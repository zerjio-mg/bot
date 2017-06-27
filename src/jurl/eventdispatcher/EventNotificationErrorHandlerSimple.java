package jurl.eventdispatcher;

public class EventNotificationErrorHandlerSimple implements EventNotificationErrorHandler {

    public void notificationError(Event event, EventListenerItem listener, Exception exception) {

        System.out.printf(
            "Event Notification Error event[%s], listener[%s], exception[%s]",
            event,
            listener,
            exception.getMessage()
        );
    }
}
