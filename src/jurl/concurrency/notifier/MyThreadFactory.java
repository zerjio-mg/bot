package jurl.concurrency.notifier;

import java.util.concurrent.ThreadFactory;

class MyThreadFactory implements ThreadFactory {

    private String name;

    private int count;

    public MyThreadFactory(String name) {

        this.name = name;
        count = 0;
    }

    public Thread newThread(Runnable task) {

        String threadName = String.format("%s[%d]", name, ++count);

        Logger.dbg(" * MyThreadFactory.newThread : %s", threadName);

        return new Thread(task, threadName);
    }
}

