package jurl.concurrency.notifier;

import java.util.List;

public class DispatchTask extends NotifierTask {

    private List<Dispatcher> dispatchers;

    public DispatchTask(String name, String event, List<Dispatcher> dispatchers) {

        super(name, event);

        this.dispatchers = dispatchers;
    }

    @Override
    public void work() {

        for(Dispatcher dispatcher : dispatchers) {
            dispatcher.notifyEvent(event);
        }
    }
}
