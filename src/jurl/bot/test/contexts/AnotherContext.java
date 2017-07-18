package jurl.bot.test.contexts;

import jurl.bot.context.Arguments;
import jurl.bot.context.BotBaseContext;
import jurl.bot.engine.BotRuntime;
import jurl.bot.annotation.BotSentence;
import jurl.bot.exception.BotException;

public class AnotherContext extends BotBaseContext {

    @BotSentence(sentence = "The user {from_user} gifts {cards} cards to user {to_user}")
    public void TheUserStartsMatch(BotRuntime botContext, Arguments arguments) {

        out(
            "> The user <%s> gifts <%d> cards to user <%s>",
            arguments.getString("from_user"),
            arguments.getInteger("cards"),
            arguments.getString("to_user")
        );

        if (arguments.getInteger("cards") == 666) {
            throw new BotException("Testing exception caught");
        }
    }
}
