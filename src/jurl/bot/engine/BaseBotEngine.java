package jurl.bot.engine;

import jurl.bot.context.BotSentenceHandler;
import jurl.bot.context.BotSentencesContainer;
import jurl.bot.context.BotContext;
import jurl.bot.logger.BotLogger;
import jurl.bot.reader.BotFileReader;
import jurl.bot.reader.BotReader;
import jurl.bot.report.BotReporterDefault;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseBotEngine implements BotEngine {

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

    private BaseBotRuntime context;

    private boolean skipTest;

    public BaseBotEngine(BotContext[] contexts) {

        pattern = Pattern.compile(SENTENCE_PATTERN);

        sentencesContainer = new BotSentencesContainer();
        for (BotContext ctx: contexts) {
            sentencesContainer.addGroup(ctx);
        }
    }

    @Override
    public void list(String[] sources) {

        sentencesContainer.list(System.out);
    }

    @Override
    public void check(String[] sources) {

        throw new NotImplementedException();
    }

    @Override
    public void run(String[] sources) {

        long start = System.currentTimeMillis();

        context = new BaseBotRuntime();

        sentencesContainer.boot(context);

        for (String script : sources) {
            processBotScript(script);
        }

        sentencesContainer.cleanup(context);

        long end = System.currentTimeMillis();

        new BotReporterDefault().report(context, (end - start) / 1000);
    }

    private void processBotScript(String script) {

        BotLogger.out("\nScript: %s", script);

        beforeScript(script);

        process(new BotFileReader(script));

        afterScript();
    }

    private void process(BotReader reader) {

        try {
            reader.open();
            String line;
            while((line = reader.read()) != null) {
                context.setCurrentScriptLine(reader.getCurrentLineCount());
                processSentence(line);
            }
            reader.close();

        } catch (Exception e) {
            BotLogger.error(context, e.getMessage());
        }

        afterTest();
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
                    beforeTest(sentence);
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

    private void beforeScript(String script) {

        context.setCurrentScript(script);
        context.resetCurrentTestCount();

        sentencesContainer.beforeScript(context);
    }

    private void beforeTest(String test) {

        context.setCurrentTest(test);
        context.incCurrentTestCount();

        sentencesContainer.beforeTest(context);
    }

    private void afterTest() {

        if (context.getCurrentTestCount() == 0) {
            return;
        }

        sentencesContainer.afterTest(context);

        skipTest = false;
    }

    private void afterScript() {

        sentencesContainer.afterScript(context);
    }
}
