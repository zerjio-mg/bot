package jurl.bot.context;

import jurl.bot.engine.BotRuntime;
import jurl.bot.logger.BotLogger;

public class BotBaseContext implements BotContext {

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public void boot(BotRuntime botContext) {
        BotLogger.debug(botContext, "%s.boot()", getName());
    }

    @Override
    public void cleanup(BotRuntime botContext) {
        BotLogger.debug(botContext, "%s.cleanup()", getName());
    }

    @Override
    public void beforeScript(BotRuntime botContext) {
        BotLogger.debug(botContext, "%s.beforeScript() : '%s", getName(), botContext.getCurrentScript());
    }

    @Override
    public void afterScript(BotRuntime botContext) {
        BotLogger.debug(botContext, "%s.afterScript() : '%s", getName(), botContext.getCurrentScript());
    }

    @Override
    public void beforeTest(BotRuntime botContext) {
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
    public void afterTest(BotRuntime botContext) {
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
