package jurl.testbot;

public class SimpleSentencesGroup extends BotBaseSentencesGroup {

    @BotSentence(sentence = "The user {user} starts match")
    public void TheUserStartsMatch(BotContext botContext, Arguments arguments) {

        out(
            "> The user <%s> starts match]",
            arguments.getString("user")
        );
    }

    @BotSentence(sentence = "Something happens")
    public void SomethingHappens(BotContext botContext, Arguments arguments) {

        out("> Something happens");
    }

    @BotSentence(sentence = "More things happen")
    public void MoreThingsHappen(BotContext botContext, Arguments arguments) {

        out("> More things happen");

//        throw new BotException("Testing exception caught");
    }

    @BotSentence(sentence = "The match finishes")
    public void TheMatchFinishes(BotContext botContext, Arguments arguments) {

        out("> The match finishes");
    }

    @BotSentence(sentence = "The user {user} gets {cards} cards")
    public void TheUserGetsCards(BotContext botContext, Arguments arguments) {

        out(
            "> The user <%s> gets <%d> cards",
            arguments.getString("user"),
            arguments.getInteger("cards") * 10
        );
    }
}
