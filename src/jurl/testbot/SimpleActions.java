package jurl.testbot;

public class SimpleActions extends BaseActions {

    @BotStatement(statement = "The user {user} starts match")
    public void TheUserStartsMatch(Context context, Arguments arguments) {

        out(
            "> The user <%s> starts match]",
            arguments.getString("user")
        );
    }

    @BotStatement(statement = "Something happens")
    public void SomethingHappens(Context context, Arguments arguments) {

        out("> Something happens");
    }

    @BotStatement(statement = "More things happen")
    public void MoreThingsHappen(Context context, Arguments arguments) {

        out("> More things happen");

//        throw new BotException("Testing exception caught");
    }

    @BotStatement(statement = "The match finishes")
    public void TheMatchFinishes(Context context, Arguments arguments) {

        out("> The match finishes");
    }

    @BotStatement(statement = "The user {user} gets {cards} cards")
    public void TheUserGetsCards(Context context, Arguments arguments) {

        out(
            "> The user <%s> gets <%d> cards",
            arguments.getString("user"),
            arguments.getInteger("cards") * 10
        );
    }
}
