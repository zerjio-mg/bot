package jurl.eventdispatcher;

public class EventListenerItem implements EventListener {

    private String listenerName;

    private EventListener listener;

    public EventListenerItem(String listenerName, EventListener listener) {

        this.listenerName = listenerName;
        this.listener = listener;
    }

    public String getListenerName() {

        return listenerName;
    }

    @Override
    public void notifyEvent(Event event) {

        listener.notifyEvent(event);
    }

    @Override
    public String toString() {

        return String.format("EventListenerItem[%s]", listenerName);
    }
}
