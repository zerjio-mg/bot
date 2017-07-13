package jurl.testbot;

public class MoreActions extends BaseActions {

    @BotStatement(statement = "The user {from_user} gifts {cards} cards to user {to_user}")
    public void TheUserStartsMatch(Context context, Arguments arguments) {

        BotLogger.warning(
            context,
            "The user <%s> gifts <%d> cards to user <%s>",
            arguments.getString("from_user"),
            arguments.getInteger("cards"),
            arguments.getString("to_user")
        );

        throw new BotException("Testing exception caught");
    }
}
