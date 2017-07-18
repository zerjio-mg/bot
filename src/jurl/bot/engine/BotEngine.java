package jurl.bot.engine;

public interface BotEngine {

    void list(String[] sources);

    void check(String[] sources);

    void run(String[] sources);
}
