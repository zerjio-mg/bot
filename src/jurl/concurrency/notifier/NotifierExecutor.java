package jurl.concurrency.notifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NotifierExecutor implements Notifier {

    private static final int SHUTDOWN_NOW_TIMEOUT = 5;

    private List<Dispatcher> dispatchers;

    private ExecutorService singleThreadPool;

    private ScheduledExecutorService delayedThreadPool;

    public NotifierExecutor() {

        dispatchers = new ArrayList<>();

        singleThreadPool = Executors.newSingleThreadExecutor(new MyThreadFactory("SingleThread"));

        delayedThreadPool = Executors.newScheduledThreadPool(3, new MyThreadFactory("DelayedThread"));
    }

    public void subscribeDispatcher(Dispatcher dispatcher) {

        dispatchers.add(dispatcher);
    }

    public void notifyEvent(String event) {

        Logger.dbg(" - notifyEvent(%s) BEGIN", event);

        try {

            singleThreadPool
                .submit(new DispatchTask("FOREGROUND", event, dispatchers))
                .get();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Logger.dbg(" - notifyEvent(%s) END", event);
    }

    public void notifyEventInBackground(String event) {

        Logger.dbg(" - notifyEventInBackground(%s) BEGIN", event);

        singleThreadPool.execute(new DispatchTask("BACKGROUND", event, dispatchers));

        Logger.dbg(" - notifyEventInBackground(%s) END", event);
    }

    public void notifyEventWithDelay(String event, long delay) {

        Logger.dbg(" - notifyEventWithDelay(%s) BEGIN", event);

        delayedThreadPool.schedule(
            new DelayTask("DELAYED", event, this),
            delay,
            TimeUnit.MILLISECONDS
        );

        Logger.dbg(" - notifyEventWithDelay(%s) END", event);
    }

    public boolean shutdown(int timeOutSeconds) {


        Logger.dbg(" - Notifier.shutdown START");

        shutdownExecutor(singleThreadPool, timeOutSeconds);
        shutdownExecutor(delayedThreadPool, timeOutSeconds);

        Logger.dbg(" - Notifier.shutdown END");

        return singleThreadPool.isShutdown() && delayedThreadPool.isShutdown();
    }

    private void shutdownExecutor(ExecutorService executor, int timeOutSeconds) {

        String executorName = executor.getClass().getSimpleName();

        Logger.dbg(" - Notifier.shutdown executor %s", executorName);

        try {

            executor.shutdown();
            if (!executor.awaitTermination(timeOutSeconds, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(SHUTDOWN_NOW_TIMEOUT, TimeUnit.SECONDS)) {
                    Logger.dbg(" - shutdown error : %s executor did not terminate", executorName);
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
