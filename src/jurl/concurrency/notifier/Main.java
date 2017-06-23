package jurl.concurrency.notifier;

public class Main {

    interface Task {
        void appy(String name, long delay);
    }

    public static void main(String[] args) {

        NotifierTask.DELAY = 10;

        Logger.setVerbose(false);

        Logger.out("BEGIN");

        Notifier notifier = new NotifierExecutor();

        notifier.subscribeDispatcher(new SimpleDispatcher());


/** /
        int tasks = test_1(notifier);
/** /
        int tasks = test_2(notifier, 30);
/**/
        int tasks = test_3(notifier, 1000);
/**/

        Logger.out("SHUTDOWN ...");
        Logger.out("SHUTDOWN ret : %s", notifier.shutdown(5));

        Logger.out("END");

        Logger.out("RESOURCE : actual [%d] expected [%d]", Resource.count, tasks);

        if (Resource.count != tasks) {
            Logger.out("OUCH !!! Seems we had some concurrent issues with the resource :(");
        }
    }

    private static int test_1(Notifier notifier) {

        notifier.notifyEventWithDelay("1", 5000);
        notifier.notifyEventInBackground("2");
        notifier.notifyEvent("3");
        notifier.notifyEventWithDelay("4", 1000);
        notifier.notifyEventWithDelay("5", 200);
        notifier.notifyEventInBackground("6");
        notifier.notifyEventInBackground("7");
        notifier.notifyEventWithDelay("8", 50);
        notifier.notifyEventInBackground("9");
        notifier.notifyEvent("10");
        notifier.notifyEventInBackground("11");

        waitSomeSeconds(11, 5000);

        return 11;
    }

    private static int test_2(Notifier notifier, int totalTasks) {

        for(int count = 0; count < totalTasks; count++) {
            notifier.notifyEventInBackground(String.valueOf(count+1));
        }

        waitSomeSeconds(totalTasks, 0);

        return totalTasks;
    }

    private static int test_3(Notifier notifier, int totalTasks) {

        Task[] tasks = new Task[totalTasks];
        long[] delays = new long[totalTasks];

        long longerDelay = 0L;

        for(int i = 0; i < tasks.length; i++) {

            switch((int)(Math.random() * 3)) {

                case 0:
                    tasks[i] = (name, delay) -> notifier.notifyEvent(name);
                    Logger.out("[%02d] notifyEvent()", i);
                    break;

                case 1:
                    tasks[i] = (name, delay) -> notifier.notifyEventInBackground(name);
                    Logger.out("[%02d] notifyEventInBackground()", i);
                    break;

                case 2:
                    delays[i] = 100L + (((int)(Math.random() * 9)) * 100L);
                    tasks[i] = (name, delay) -> notifier.notifyEventWithDelay(name, delay);
                    longerDelay = (longerDelay > delays[i])? longerDelay : delays[i];
                    Logger.out("[%02d] notifyEventWithDelay(%d)", i, delays[i]);
                    break;

            }
        }

        for(int i = 0; i < tasks.length; i++) {
            tasks[i].appy(String.valueOf(i+1), delays[i]);
        }

        waitSomeSeconds(totalTasks, longerDelay) ;

        return tasks.length;
    }

    private static void waitSomeSeconds(int totalTasks, long longerDelay) {

        long timeSpentWithSyncCalls = totalTasks * NotifierTask.DELAY;

        long expectedTime = (timeSpentWithSyncCalls > longerDelay)? timeSpentWithSyncCalls : longerDelay;

        waitSomeSeconds((expectedTime / 1000) + 1) ;
    }

    private static void waitSomeSeconds(long seconds) {

        Logger.out("WAIT %d seconds ...", seconds);

        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
