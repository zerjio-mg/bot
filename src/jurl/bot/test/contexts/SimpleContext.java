package jurl.bot.test.contexts;

import jurl.bot.context.Arguments;
import jurl.bot.context.BotBaseContext;
import jurl.bot.engine.BotRuntime;
import jurl.bot.annotation.BotSentence;

public class SimpleContext extends BotBaseContext {

    @BotSentence(sentence = "The user {user} starts match")
    public void TheUserStartsMatch(BotRuntime botContext, Arguments arguments) {

        out(
            "> The user <%s> starts match]",
            arguments.getString("user")
        );
    }

    @BotSentence(sentence = "Something happens")
    public void SomethingHappens(BotRuntime botContext, Arguments arguments) {

        out("> Something happens");
    }

    @BotSentence(sentence = "More things happen")
    public void MoreThingsHappen(BotRuntime botContext, Arguments arguments) {

        out("> More things happen");

//        throw new BotException("Testing exception caught");
    }

    @BotSentence(sentence = "The match finishes")
    public void TheMatchFinishes(BotRuntime botContext, Arguments arguments) {

        out("> The match finishes");
    }

    @BotSentence(sentence = "The user {user} gets {cards} cards")
    public void TheUserGetsCards(BotRuntime botContext, Arguments arguments) {

        out(
            "> The user <%s> gets <%d> cards",
            arguments.getString("user"),
            arguments.getInteger("cards") * 10
        );
    }
}
