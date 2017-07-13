package jurl.testbot;

public class Main {

    public static void main(String[] args) {

        if (args.length == 1 && args[0].equals("list")) {
            new BotEngine().list(args);
        } else {
            new BotEngine().run(args);
        }
    }
}
