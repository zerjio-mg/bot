package jurl.bot.engine;

import java.util.List;

public interface BotRuntime {

    BotRuntime setCurrentScript(String script);

    String getCurrentScript();

    BotRuntime setCurrentScriptLine(int lineCount);

    int getCurrentScriptLine();

    BotRuntime setCurrentTest(String test);

    String getCurrentTest();

    BotRuntime resetCurrentTestCount();

    int getCurrentTestCount();

    int incCurrentTestCount();

    int getTotalTestCount();

    int addFail(String message);

    List<String> getFails();

    int getFailsCount();

    BotRuntime setGlobalData(String key, Object value);

    BotRuntime setTestData(String key, Object value);

    Object get(String key);

    void clearTestData();
}
