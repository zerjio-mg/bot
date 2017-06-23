package jurl.concurrency.notifier;

public class DelayTask extends NotifierTask {

    private Notifier notifier;

    public DelayTask(String name, String event, Notifier notifier) {

        super(name, event);

        this.notifier = notifier;
    }

    @Override
    public void work() {

        notifier.notifyEventInBackground(event);
    }
}
