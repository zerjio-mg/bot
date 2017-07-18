package jurl.bot.engine;

import jurl.bot.context.BotContext;

public class BotEngineFactory {

    public static BotEngine newBotEngine(BotContext[] contexts) {
        return new BaseBotEngine(contexts);
    }
}
