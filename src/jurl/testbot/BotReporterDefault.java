package jurl.testbot;

public class BotReporterDefault implements BotReporter {

    @Override
    public void report(BotContextContainer context, long spentSeconds) {

        long hours = spentSeconds / 3600;
        long minutes = (spentSeconds % 3600) / 60;
        long seconds = (spentSeconds % 3600) % 60;

        BotLogger.newLine();
        BotLogger.out("Test finished.");

        if (context.getFailsCount() > 0) {

            BotLogger.out("\nThere were errors [%d]:\n", context.getFailsCount());

            for (String fail : context.getFails()) {
                BotLogger.error(fail);
            }
        }

        BotLogger.newLine();
        BotLogger.out("Spent time: %02d:%02d:%02d", hours, minutes, seconds);
        BotLogger.out("Executed %d tests", context.getTotalTestCount());
        BotLogger.out("Failed %d tests", context.getFailsCount());
    }
}
