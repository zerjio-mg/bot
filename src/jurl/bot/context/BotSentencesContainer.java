package jurl.bot.context;

import jurl.bot.annotation.BotSentence;
import jurl.bot.engine.BotRuntime;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BotSentencesContainer {

    private List<BotContext> groups;

    private List<BotSentenceHandler> handlers;

    public BotSentencesContainer() {

        groups = new ArrayList();
        handlers = new ArrayList();
    }

    public List<BotContext> getGroups() {
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

    public void addGroup(BotContext botContext) {

        groups.add(botContext);

        Method[] methods = botContext.getClass().getMethods();
        for (Method method : methods) {
            BotSentence statement = method.getAnnotation(BotSentence.class);
            if (statement != null) {
                handlers.add(new BotSentenceHandler(botContext, method, statement.sentence()));
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

    public void boot(BotRuntime botContext) {
        for (BotContext group : getGroups()) {
            group.boot(botContext);
        }
    }

    public void beforeScript(BotRuntime botContext) {
        for (BotContext group : getGroups()) {
            group.beforeScript(botContext);
        }
    }

    public void beforeTest(BotRuntime botContext) {
        for (BotContext group : getGroups()) {
            group.beforeTest(botContext);
        }
    }

    public void afterTest(BotRuntime botContext) {
        for (BotContext group : getGroups()) {
            group.afterTest(botContext);
        }
    }

    public void afterScript(BotRuntime botContext) {
        for (BotContext group : getGroups()) {
            group.afterScript(botContext);
        }
    }

    public void cleanup(BotRuntime botContext) {
        for (BotContext group : getGroups()) {
            group.cleanup(botContext);
        }
    }
}
