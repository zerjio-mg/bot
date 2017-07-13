package jurl.testbot;

class BaseActions implements BotActions {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void boot(Context context) {
        BotLogger.debug(context, "%s.boot()", getName());
    }

    @Override
    public void cleanup(Context context) {
        BotLogger.debug(context, "%s.cleanup()", getName());
    }

    @Override
    public void beforeScript(Context context) {
        BotLogger.debug(context, "%s.beforeScript() : '%s", getName(), context.getCurrentScript());
    }

    @Override
    public void afterScript(Context context) {
        BotLogger.debug(context, "%s.afterScript() : '%s", getName(), context.getCurrentScript());
    }

    @Override
    public void beforeTest(Context context) {
        BotLogger.debug(
            context,
            "%s.beforeTest() : [%d][%d] '%s'",
            getName(),
            context.getTotalTestCount(),
            context.getCurrentTestCount(),
            context.getCurrentScript()
        );
    }

    @Override
    public void afterTest(Context context) {
        BotLogger.debug(
            context,
            "%s.afterTest() : [%d][%d] '%s'",
            getName(),
            context.getTotalTestCount(),
            context.getCurrentTestCount(),
            context.getCurrentScript()
        );
    }

    protected void out(String format, Object... args) {
//        BotLogger.out(format, args);
    }
}
