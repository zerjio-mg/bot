package jurl.bot.reader;

public interface BotReader {

    BotReader open() throws Exception;

    String read() throws Exception;

    BotReader close() throws Exception;

    int getCurrentLineCount();
}
