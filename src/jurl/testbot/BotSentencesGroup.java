package jurl.testbot;

public interface BotSentencesGroup {

    String getName();

    void boot(BotContext botContext);

    void cleanup(BotContext botContext);

    void beforeScript(BotContext botContext);

    void afterScript(BotContext botContext);

    void beforeTest(BotContext botContext);

    void afterTest(BotContext botContext);
}
