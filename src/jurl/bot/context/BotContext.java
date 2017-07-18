package jurl.bot.context;

import jurl.bot.engine.BotRuntime;

public interface BotContext {

    String getName();

    void boot(BotRuntime botContext);

    void cleanup(BotRuntime botContext);

    void beforeScript(BotRuntime botContext);

    void afterScript(BotRuntime botContext);

    void beforeTest(BotRuntime botContext);

    void afterTest(BotRuntime botContext);
}
