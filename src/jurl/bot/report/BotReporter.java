package jurl.bot.report;

import jurl.bot.engine.BaseBotRuntime;

public interface BotReporter {

    void report(BaseBotRuntime context, long spentSeconds);
}
