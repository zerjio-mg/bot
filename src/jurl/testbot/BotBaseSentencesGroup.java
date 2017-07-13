package jurl.testbot;

class BotBaseSentencesGroup implements BotSentencesGroup {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void boot(BotContext botContext) {
        BotLogger.debug(botContext, "%s.boot()", getName());
    }

    @Override
    public void cleanup(BotContext botContext) {
        BotLogger.debug(botContext, "%s.cleanup()", getName());
    }

    @Override
    public void beforeScript(BotContext botContext) {
        BotLogger.debug(botContext, "%s.beforeScript() : '%s", getName(), botContext.getCurrentScript());
    }

    @Override
    public void afterScript(BotContext botContext) {
        BotLogger.debug(botContext, "%s.afterScript() : '%s", getName(), botContext.getCurrentScript());
    }

    @Override
    public void beforeTest(BotContext botContext) {
        BotLogger.debug(
                botContext,
            "%s.beforeTest() : [%d][%d] '%s'",
            getName(),
            botContext.getTotalTestCount(),
            botContext.getCurrentTestCount(),
            botContext.getCurrentScript()
        );
    }

    @Override
    public void afterTest(BotContext botContext) {
        BotLogger.debug(
                botContext,
            "%s.afterTest() : [%d][%d] '%s'",
            getName(),
            botContext.getTotalTestCount(),
            botContext.getCurrentTestCount(),
            botContext.getCurrentScript()
        );
    }

    protected void out(String format, Object... args) {
//        BotLogger.out(format, args);
    }
}
