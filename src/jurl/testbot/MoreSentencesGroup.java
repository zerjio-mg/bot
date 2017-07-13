package jurl.testbot;

public class MoreSentencesGroup extends BotBaseSentencesGroup {

    @BotSentence(sentence = "The user {from_user} gifts {cards} cards to user {to_user}")
    public void TheUserStartsMatch(BotContext botContext, Arguments arguments) {

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
