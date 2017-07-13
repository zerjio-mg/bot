package jurl.testbot;

import java.util.List;

public interface BotContext {

    BotContext setCurrentScript(String script);

    String getCurrentScript();

    BotContext setCurrentScriptLine(int lineCount);

    int getCurrentScriptLine();

    BotContext setCurrentTest(String test);

    String getCurrentTest();

    BotContext resetCurrentTestCount();

    int getCurrentTestCount();

    int incCurrentTestCount();

    int getTotalTestCount();

    int addFail(String message);

    List<String> getFails();

    int getFailsCount();

    BotContext setGlobalData(String key, Object value);

    BotContext setTestData(String key, Object value);

    Object get(String key);

    void clearTestData();
}
