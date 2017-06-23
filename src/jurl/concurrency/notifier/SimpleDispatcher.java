package jurl.concurrency.notifier;

public class SimpleDispatcher implements Dispatcher {

    public void notifyEvent(String event) {

        Resource.count ++;

        Logger.out("EVENT [%s]", event);
    }
}
