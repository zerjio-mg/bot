package jurl.concurrency.notifier;

public class Logger {

    private static long startingTimeInMilliseconds = System.currentTimeMillis();

    private static boolean verbose = true;

    public static void setVerbose(boolean verbose) {

        Logger.verbose = verbose;
    }

    public static void dbg(String format, Object... args) {
        if (Logger.verbose) {
            out(format, args);
        }
    }

    public static void out(String format, Object... args) {

        System.out.printf(
            String.format(
                "[%05d] %s\n",
                System.currentTimeMillis() - startingTimeInMilliseconds,
                format
            ),
            args
        );
    }
}
