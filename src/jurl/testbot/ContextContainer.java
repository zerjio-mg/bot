package jurl.testbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextContainer implements Context {

    private Map<String, Object> globalData;

    private Map<String, Object> testData;

    private List<String> fails;

    private String scriptName;

    private int currentScriptLine;

    private String currentTestName;

    private int currentTestCount;

    private int totalTestCount;

    private int failsCount;

    public ContextContainer() {
        globalData = new HashMap();
        testData = new HashMap();
        fails = new ArrayList();
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
    public int addFail(String message) {

        fails.add(String.format("[%s:%d] %s", getCurrentScript(), getCurrentScriptLine(), message));

        return ++failsCount;
    }

    @Override
    public List<String> getFails() {
        return fails;
    }

    @Override
    public int getFailsCount() {
        return failsCount;
    }

    @Override
    public Context setGlobalData(String key, Object value) {

        globalData.put(key, value);

        return this;
    }

    @Override
    public Context setTestData(String key, Object value) {

        testData.put(key, value);

        return this;
    }

    @Override
    public Object get(String key) {

        Object value = testData.get(key);

        return (value == null)? globalData.get(key) : value;
    }

    @Override
    public void clearTestData() {
        testData.clear();
    }
}
