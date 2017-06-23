package jurl.concurrency.notifier;

abstract public class NotifierTask implements Runnable {

    public static long DELAY = 2000;

    private static long counter = 0;

    private long id ;

    private String name;

    protected String event;

    public NotifierTask(String name, String event) {

        this.id = ++ NotifierTask.counter;

        this.name = name;
        this.event = event;
    }

    @Override
    public void run() {

        Logger.dbg("   > Task[%s-%d] START", name, id);

        try {

            if (DELAY > 0) {
                Thread.sleep(DELAY);
            }

            work();

        } catch (Exception e) {
            Logger.dbg("   > Task[%s-%d] ERROR : %s", name, id, e.getMessage());
        }

        Logger.dbg("   > Task[%s-%d] END", name, id);
    }

    abstract void work();
}
