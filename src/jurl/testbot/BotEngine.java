package jurl.testbot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BotEngine {

    private static final String SENTENCE_PATTERN = "(\\w*)\\s*(.*)";

    private static final String TEST_DECLARATION = "test";

    private String[] validDeclarations = {
            TEST_DECLARATION,
            "given",
            "when",
            "then",
            "and"
    };

    private List<BotActions> actions;

    private List<BotSentence> sentences;

    private Pattern pattern;

    private ContextContainer context;

    private boolean skipTest;

    public BotEngine() {

        actions = new ArrayList();
        sentences = new ArrayList();

        pattern = Pattern.compile(SENTENCE_PATTERN);
    }

    public void run() {

        long start = System.currentTimeMillis();

        context = new ContextContainer();

        registerActions(new SimpleActions());
        registerActions(new MoreActions());

        boot();

        processBotScript("test.bot");
        processBotScript("test_1.bot");
        processBotScript("test_2.bot");

        cleanup();

        long end = System.currentTimeMillis();

        long spendSeconds = (end - start) / 1000;

        long hours = spendSeconds / 3600;
        long minutes = (spendSeconds % 3600) / 60;
        long seconds = (spendSeconds % 3600) % 60;

        BotLogger.newLine();
        BotLogger.out("Test finished.");

        if (context.getFailsCount() > 0) {

            BotLogger.out("\nThere were errors [%d]:\n", context.getFailsCount());

            for (String fail : context.getFails()) {
                BotLogger.error(fail);
            }
        }

        BotLogger.newLine();
        BotLogger.out("Spent time: %02d:%02d:%02d", hours, minutes, seconds);
        BotLogger.out("Executed %d tests", context.getTotalTestCount());
        BotLogger.out("Failed %d tests", context.getFailsCount());
    }

    private void registerActions(BotActions botActions) {

        actions.add(botActions);

        Method[] methods = botActions.getClass().getMethods();
        for (Method method : methods) {
            BotStatement statement = method.getAnnotation(BotStatement.class);
            if (statement != null) {
                sentences.add(new BotSentence(botActions, method, statement.statement()));
            }
        }
    }

    private void processBotScript(String script) {

        BotLogger.out("\nScript: %s\n", script);

        context.setCurrentScript(script);

        beforeScript();

        try {

            FileReader reader = new FileReader(script);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            for (int lineCount = 1; (line = bufferedReader.readLine()) != null; lineCount++) {

                line = line.trim();
                if (!line.isEmpty() && !line.startsWith(";")) {
                    context.setCurrentScriptLine(lineCount);
                    processSentence(line);
                }
            }

            reader.close();

        } catch (IOException e) {
            BotLogger.error(context, "Script '%s' not found", script);
        }

        afterTest();
        afterScript();
    }

    private void processSentence(String line) {

        Matcher match = pattern.matcher(line);
        if (match.matches()) {
            String declaration = match.group(1);
            String sentence = match.group(2);

            if (isValidDeclaration(declaration)) {

                if (!skipTest) {
                    BotLogger.out("%7s   %s", declaration, sentence);
                }

                if (isTestDeclaration(declaration)) {
                    afterTest();
                    beforeTest(sentence);
                    BotLogger.newLine();
                    return;
                }

                if (skipTest) {
                    return;
                }

                BotSentence bs = lookupBotSentence(sentence);
                if (bs != null) {
                    if (bs.execute(sentence, context)) {
                        return;
                    }

                    skipTest = true;

                    return;

                } else {
                    BotLogger.error(context, "Undefined sentence '%s'", sentence);
                }
            } else {
                BotLogger.error(context, "Invalid declaration '%s'", declaration);
            }
        } else {
            BotLogger.error(context, "Wrong format[Wrong format");
        }

        BotLogger.error(context, "You must fix the issue on script '%s' to continue ...", context.getCurrentScript());
        System.exit(1);

        return;
    }

    private boolean isTestDeclaration(String declaration) {
        return declaration.toLowerCase().equals(TEST_DECLARATION);
    }

    private boolean isValidDeclaration(String declaration) {

        declaration = declaration.toLowerCase();

        for (String d : validDeclarations) {
            if (d.equals(declaration)) {
                return true;
            }
        }
        return false;
    }

    private BotSentence lookupBotSentence(String sentence) {

        for (BotSentence s : sentences) {
            if (s.matches(sentence)) {
                return s;
            }
        }

        return null;
    }

    private void boot() {
        for (BotActions action : actions) {
            action.boot(context);
        }
    }

    private void beforeScript() {

        context.resetCurrentTestCount();

        for (BotActions action : actions) {
            action.beforeScript(context);
        }
    }

    private void beforeTest(String test) {

        skipTest = false;

        context.setCurrentTest(test);
        context.incCurrentTestCount();

        for (BotActions action : actions) {
            action.beforeTest(context);
        }
    }

    private void afterTest() {

        if (context.getCurrentTestCount() == 0) {
            return;
        }

        for (BotActions action : actions) {
            action.afterTest(context);
        }
    }

    private void afterScript() {
        for (BotActions action : actions) {
            action.afterScript(context);
        }
    }

    private void cleanup() {
        for (BotActions action : actions) {
            action.cleanup(context);
        }
    }
}
