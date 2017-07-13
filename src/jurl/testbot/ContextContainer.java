package jurl.testbot;

import java.util.HashMap;
import java.util.Map;

public class ContextContainer implements Context {

    private Map<String, Object> data;

    private String scriptName;

    private int currentScriptLine;

    private String currentTestName;

    private int currentTestCount;

    private int totalTestCount;

    private int failsCount;

    public ContextContainer() {
        data = new HashMap();
    }

    @Override
    public Context setCurrentScript(String script) {
        scriptName = script;
        currentScriptLine = 0;

        return this;
    }

    @Override
    public String getCurrentScript() {
        return scriptName;
    }

    @Override
    public Context setCurrentScriptLine(int lineCount) {
        currentScriptLine = lineCount;

        return this;
    }

    @Override
    public int getCurrentScriptLine() {
        return currentScriptLine;
    }

    @Override
    public Context setCurrentTest(String test) {

        currentTestName = test;

        return this;
    }

    @Override
    public String getCurrentTest() {
        return currentTestName;
    }

    @Override
    public Context resetCurrentTestCount() {

        currentTestCount = 0;

        return this;
    }

    @Override
    public int getCurrentTestCount() {
        return currentTestCount;
    }

    @Override
    public int incCurrentTestCount() {
        totalTestCount ++;
        currentTestCount ++;

        return currentTestCount;
    }

    @Override
    public int getTotalTestCount() {
        return totalTestCount;
    }

    @Override
    public int incFailsCount() {
        return ++failsCount;
    }

    @Override
    public int getFailsCount() {
        return failsCount;
    }

    @Override
    public Context set(String key, Object value) {

        data.put(key, value);

        return this;
    }

    @Override
    public Object get(String key) {
        return data.get(key);
    }
}
