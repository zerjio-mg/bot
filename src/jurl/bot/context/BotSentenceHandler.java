package jurl.bot.context;

import jurl.bot.engine.BotRuntime;
import jurl.bot.logger.BotLogger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BotSentenceHandler {

    private static final String PATTERN_SENTENCE_KEY = "(\\{(\\w*)})";

    private static final int PATTERN_SENTENCE_KEY_GROUP = 2;

    private static final String PATTERN_SENTENCE_VALUE = "((['\"]?)(.*)(\\2))";

    private static final int PATTERN_SENTENCE_VALUE_TOTAL_GROUPS = 4;
    private static final int PATTERN_SENTENCE_VALUE_GROUP_OFFSET = 2;

    private List<String> parameters;

    private String baseSentence;

    private String regexp;

    private Pattern pattern;

    private BotContext subject;

    private Method action;

    public BotSentenceHandler(BotContext subject, Method action, String sentence) {

        parameters = new ArrayList();

        this.subject = subject;
        this.action = action;
        this.baseSentence = sentence;

        parseSentence();
    }

    public BotContext getSubject() {
        return subject;
    }

    public String getBaseSentence() {
        return baseSentence;
    }

    /**
     * SENTENCE:    The user {user} gets {cards} cards
     *
     * PATTERN:     The user ((['"]?)(.+)(\2)) gets ((['"]?)(.+)(\2)) cards
     *
     * DATA:        The user pim gets 5 cards
     *              The user 'pim' gets 5 cards
     *              The user "pim" gets 5 cards
     */
    private void parseSentence() {

        StringBuffer sb = new StringBuffer();

        Matcher m = Pattern.compile(PATTERN_SENTENCE_KEY).matcher(baseSentence);
        while (m.find()) {
            String key = m.group(PATTERN_SENTENCE_KEY_GROUP);
            parameters.add(key);
            m.appendReplacement(sb, Matcher.quoteReplacement(PATTERN_SENTENCE_VALUE));
        }

        m.appendTail(sb);

        regexp = sb.toString();
        pattern = Pattern.compile(regexp);
    }

    /**
     *
     * @param sentence
     * @return
     */
    public boolean matches(String sentence) {
        return pattern.matcher(sentence).matches();
    }

    /**
     *
     * @param sentence
     * @param botContext
     */
    public boolean execute(String sentence, BotRuntime botContext) {

        try {
            action.invoke(subject, botContext, parseArguments(sentence));
        } catch (Exception e) {

            Throwable t = e.getCause();
            if (t == null) {
                t = e;
            }

            BotLogger.newLine();
            BotLogger.error(
                    botContext,
                t.getMessage()
            );

            botContext.addFail(t.getMessage());

            return false;
        }

        return true;
    }

    /**
     *
     * @param sentence
     *
     * @return
     */
    public Arguments parseArguments(String sentence) {

        Matcher m = pattern.matcher(sentence);
        if (m.matches()) {
            ArgumentsContainer arguments = new ArgumentsContainer();

            for (int i = 0, groupOffset = 1;
                 i < parameters.size();
                 i++, groupOffset += PATTERN_SENTENCE_VALUE_TOTAL_GROUPS) {
                arguments.set(parameters.get(i), m.group(groupOffset + PATTERN_SENTENCE_VALUE_GROUP_OFFSET));
            }

            return arguments;
        }

        return null;
    }
}
