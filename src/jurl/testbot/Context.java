package jurl.testbot;

import java.util.List;

public interface Context {

    Context setCurrentScript(String script);

    String getCurrentScript();

    Context setCurrentScriptLine(int lineCount);

    int getCurrentScriptLine();

    Context setCurrentTest(String test);

    String getCurrentTest();

    Context resetCurrentTestCount();

    int getCurrentTestCount();

    int incCurrentTestCount();

    int getTotalTestCount();

    int addFail(String message);

    List<String> getFails();

    int getFailsCount();

    Context setGlobalData(String key, Object value);

    Context setTestData(String key, Object value);

    Object get(String key);

    void clearTestData();
}
