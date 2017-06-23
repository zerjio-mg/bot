package jurl.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ThreadPools {

    enum Pool {
        SINGLE,
        FIXED,
        CACHED
    };

    private long startingTimeInMilliseconds;

    class SimpleThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            log("SimpleThreadFactory.newThread");
            return new Thread(r);
        }
    }

    class SimpleTask implements Runnable {

        private String name;

        public SimpleTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                log("SimpleTask.run@%s start", name);
                Thread.sleep(2000);
                log("SimpleTask.run@%s end", name);
            } catch (Exception e) {
                log("SimpleTask.run@%s Exception : %sn", name, e.getMessage());
            }
        }
    }

    public static void main(String[] args) {

        new ThreadPools().run();
    }

    public void run() {

        startingTimeInMilliseconds = System.currentTimeMillis();

        log("jurl.concurrency.ThreadPools start");

        ExecutorService executor = newExecutor(Pool.SINGLE);

        enqueueTasks(executor);

        shutdownExecutor(executor);

        log("jurl.concurrency.ThreadPools end");
    }

    private ExecutorService newExecutor(Pool poolType) {

        switch(poolType) {
            case SINGLE:
                return Executors.newSingleThreadExecutor(new SimpleThreadFactory());
            case FIXED:
                return Executors.newFixedThreadPool(3, new SimpleThreadFactory());
            case CACHED:
                return Executors.newCachedThreadPool(new SimpleThreadFactory());
        }

        return null;
    }

    private void enqueueTasks(ExecutorService executor) {

        log("jurl.concurrency.ThreadPools.run send tasks to executor");

        executor.execute(new SimpleTask("uno"));
        executor.execute(new SimpleTask("dos"));
        executor.execute(new SimpleTask("tres"));
        executor.execute(new SimpleTask("cuatro"));
        executor.execute(new SimpleTask("cinco"));

        log("jurl.concurrency.ThreadPools.run all tasks sent");
    }

    private void shutdownExecutor(ExecutorService executor) {

        log("jurl.concurrency.ThreadPools.run wait for executor shutdown");

        try {
            executor.shutdown();
            if (!executor.awaitTermination(20, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("Threads pool did not terminate");
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        log("jurl.concurrency.ThreadPools.run executor down");
    }

    private void log(String format, Object... args) {

        long currentTimeInMilliseconds = System.currentTimeMillis() - startingTimeInMilliseconds;

        System.out.printf(String.format("[%05d] %s\n", currentTimeInMilliseconds, format), args);
    }
}