package jurl.bot.engine;

import jurl.bot.context.BotContext;
import jurl.bot.reader.BotReaderHelper;

import java.io.File;

public class BotLauncher {

    public static void launch(String[] contextsClassNames, String[] args) {

        Class[] contextClasses = new Class[contextsClassNames.length];

        for (int index = 0; index < contextClasses.length; index++) {
            try {
                contextClasses[index] = Class.forName(contextsClassNames[index]);
            } catch (Exception e) {
                throw new RuntimeException(
                    String.format(
                        "Error loading context '%s' : %s",
                        contextsClassNames[index],
                        e.getMessage()
                    )
                );
            }
        }

        launch(contextClasses, args);
    }

    public static void launch(Class[] contextsClasses, String[] args) {

        BotContext[] contexts = new BotContext[contextsClasses.length];

        for (int index = 0; index < contexts.length; index++) {
            try {
                contexts[index] = (BotContext)contextsClasses[index].newInstance();
            } catch (Exception e) {
                throw new RuntimeException(
                    String.format(
                        "Error instantiating context '%s' : %s",
                        contextsClasses[index].getCanonicalName(),
                        e.getMessage()
                    )
                );
            }
        }

        launch(contexts, args);
    }

    /**
     * launch [ options ] command [ arguments ]
     *
     * options:
     *
     *      --scripts   Directory were look for bot scripts (.bot)
     *
     * command:
     *
     *      list
     *      check
     *      run
     *
     * arguments:
     *
     *
     * @param args
     */
    public static void launch(BotContext[] contexts, String[] args) {


        File file = new File("jurl");
        file.
        BotEngine botEngine = BotEngineFactory.newBotEngine(contexts);

        if (args.length == 1 && args[0].equals("list")) {
            botEngine.list(args);
        } else {
            if (args.length > 0) {
                botEngine.run(BotReaderHelper.findScriptsInDirectory(args[0]).toArray(new String[0]));
            } else {
                botEngine.run(BotReaderHelper.findScripts().toArray(new String[0]));
            }
        }
    }

    private static void launch(BotEngine botEngine, BotReader reader) {

    }
}
