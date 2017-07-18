package jurl.bot.test;

import jurl.bot.engine.BotLauncher;

public class Test {

    public static void main(String[] args) {

        BotLauncher.launch(
            new Class[]{
                jurl.bot.test.contexts.SimpleContext.class,
                jurl.bot.test.contexts.AnotherContext.class,
            },
            args
        );
    }
}
