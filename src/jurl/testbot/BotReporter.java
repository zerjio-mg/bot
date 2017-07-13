package jurl.testbot;

public interface BotReporter {

    void report(BotContextContainer context, long spentSeconds);
}
