package jurl.testbot;

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

    int incFailsCount();

    int getFailsCount();

    Context set(String key, Object value);

    Object get(String key);
}
