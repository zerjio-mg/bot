package jurl.testbot;

public interface BotActions {

    String getName();

    void boot(Context context);

    void cleanup(Context context);

    void beforeScript(Context context);

    void afterScript(Context context);

    void beforeTest(Context context);

    void afterTest(Context context);
}
