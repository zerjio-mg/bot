package jurl.testbot;

public class BotLogger {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static boolean verbose = false;

    public static void newLine() {
        System.out.println();
    }

    public static void out(String message, Object...args) {

        System.out.printf(
                "%s%s%s\n",
                ANSI_GREEN,
                String.format(message, args),
                ANSI_RESET
        );
    }

    public static void warning(Context context, String message, Object...args) {

        System.out.printf(
                "%sWARNING: [%s:%s] %s%s\n",
                ANSI_YELLOW,
                context.getCurrentScript(),
                context.getCurrentScriptLine(),
                String.format(message, args),
                ANSI_RESET
        );
    }

    public static void error(String message, Object...args) {

        System.out.printf(
                "%sERROR: %s%s\n",
                ANSI_RED,
                String.format(message, args),
                ANSI_RESET
        );
    }

    public static void error(Context context, String message, Object...args) {

        System.out.printf(
                "%sERROR: [%s:%s] %s%s\n",
                ANSI_RED,
                context.getCurrentScript(),
                context.getCurrentScriptLine(),
                String.format(message, args),
                ANSI_RESET
        );
    }

    public static void debug(Context context, String message, Object...args) {

        if (!verbose) {
            return;
        }

        System.out.printf(
                "%sDEBUG: [%s:%s] %s%s\n",
                ANSI_BLUE,
                context.getCurrentScript(),
                context.getCurrentScriptLine(),
                String.format(message, args),
                ANSI_RESET
        );
    }
}
