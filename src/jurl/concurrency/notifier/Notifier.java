package jurl.concurrency.notifier;

public interface Notifier {

    void subscribeDispatcher(Dispatcher dispatcher);

    void notifyEvent(String event);

    void notifyEventInBackground(String event);

    void notifyEventWithDelay(String event, long delay);

    boolean shutdown(int timeOutSeconds);
}
