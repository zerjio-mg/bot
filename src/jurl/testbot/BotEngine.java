package jurl.testbot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
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

    BotSentencesContainer sentencesContainer;

    private Pattern pattern;

    private BotContextContainer context;

    private boolean skipTest;

    public BotEngine() {

        sentencesContainer = new BotSentencesContainer();
        pattern = Pattern.compile(SENTENCE_PATTERN);

        sentencesContainer.addGroup(new SimpleSentencesGroup());
        sentencesContainer.addGroup(new MoreSentencesGroup());
    }

    public void list(String[] args) {

        sentencesContainer.list(System.out);
    }

    public void run(String[] args) {

        String folder = "";

        if (args.length > 0) {
            folder = String.format("%s/", args[0]);
        }

        long start = System.currentTimeMillis();

        context = new BotContextContainer();

        sentencesContainer.boot(context);

        processBotScript(String.format("%s%s", folder, "test.bot"));
        processBotScript(String.format("%s%s", folder, "test_1.bot"));
        processBotScript(String.format("%s%s", folder, "test_2.bot"));

        sentencesContainer.cleanup(context);

        long end = System.currentTimeMillis();

        new BotReporterDefault().report(context, (end - start) / 1000);
    }

    private void processBotScript(String script) {

        BotLogger.out("\nScript: %s", script);

        context.setCurrentScript(script);

        context.resetCurrentTestCount();
        sentencesContainer.beforeScript(context);

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
        sentencesContainer.afterScript(context);
    }

    private void processSentence(String line) {

        Matcher match = pattern.matcher(line);
        if (match.matches()) {
            String declaration = match.group(1);
            String sentence = match.group(2);

            if (isValidDeclaration(declaration)) {

                if (isTestDeclaration(declaration)) {
                    BotLogger.newLine();
                    afterTest();
                }

                if (skipTest ) {
                    return;
                }

                BotLogger.out("%7s   %s", declaration, sentence);

                if (isTestDeclaration(declaration)) {
                    context.setCurrentTest(declaration);
                    context.incCurrentTestCount();
                    sentencesContainer.beforeTest(context, sentence);
                    return;
                }

                BotSentenceHandler bs = sentencesContainer.lookup(sentence);
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

    private void afterTest() {

        if (context.getCurrentTestCount() == 0) {
            return;
        }

        sentencesContainer.afterTest(context);

        skipTest = false;
    }
}
