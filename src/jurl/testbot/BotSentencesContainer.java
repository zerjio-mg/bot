package jurl.testbot;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BotSentencesContainer {

    private List<BotSentencesGroup> groups;

    private List<BotSentenceHandler> handlers;

    public BotSentencesContainer() {

        groups = new ArrayList();
        handlers = new ArrayList();
    }

    public List<BotSentencesGroup> getGroups() {
        return groups;
    }

    public List<BotSentenceHandler> getHandlers() {
        return handlers;
    }

    public void list(PrintStream out) {

        String actions = null;
        for(BotSentenceHandler sentence: getHandlers()) {
            if (!sentence.getSubject().getName().equals(actions)) {
                actions = sentence.getSubject().getName();
                out.printf("\n[%s]\n", actions);
            }
            out.printf("  - %s\n", sentence.getBaseSentence());
        }

        out.println();
    }

    public void addGroup(BotSentencesGroup botSentencesGroup) {

        groups.add(botSentencesGroup);

        Method[] methods = botSentencesGroup.getClass().getMethods();
        for (Method method : methods) {
            BotSentence statement = method.getAnnotation(BotSentence.class);
            if (statement != null) {
                handlers.add(new BotSentenceHandler(botSentencesGroup, method, statement.sentence()));
            }
        }
    }

    public BotSentenceHandler lookup(String sentence) {

        for (BotSentenceHandler handler : getHandlers()) {
            if (handler.matches(sentence)) {
                return handler;
            }
        }

        return null;
    }

    public void boot(BotContext botContext) {
        for (BotSentencesGroup group : getGroups()) {
            group.boot(botContext);
        }
    }

    public void beforeScript(BotContext botContext) {
        for (BotSentencesGroup group : getGroups()) {
            group.beforeScript(botContext);
        }
    }

    public void beforeTest(BotContext botContext, String test) {
        for (BotSentencesGroup group : getGroups()) {
            group.beforeTest(botContext);
        }
    }

    public void afterTest(BotContext botContext) {
        for (BotSentencesGroup group : getGroups()) {
            group.afterTest(botContext);
        }
    }

    public void afterScript(BotContext botContext) {
        for (BotSentencesGroup group : getGroups()) {
            group.afterScript(botContext);
        }
    }

    public void cleanup(BotContext botContext) {
        for (BotSentencesGroup group : getGroups()) {
            group.cleanup(botContext);
        }
    }
}
