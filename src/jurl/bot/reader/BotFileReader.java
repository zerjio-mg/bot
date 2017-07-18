package jurl.bot.reader;

import java.io.BufferedReader;
import java.io.FileReader;

public class BotFileReader implements BotReader {

    String script;

    BufferedReader reader;

    int lineCount;

    public BotFileReader(String script) {
        this.script = script;
        this.lineCount = 0;
    }

    @Override
    public BotFileReader open() throws Exception {
        reader = new BufferedReader(new FileReader(script));

        return this;
    }

    @Override
    public String read() throws Exception {

        if (reader == null) {
            throw new Exception("BotReader is not opened");
        }

        String line;

        while ((line = reader.readLine()) != null) {
            lineCount ++;
            line = line.trim();
            if (isValidLine(line)) {
                return line;
            }
        }

        return line;
    }

    private boolean isValidLine(String line) {

        return !line.isEmpty() && !line.startsWith(";");
    }

    @Override
    public BotFileReader close() throws Exception {

        if (reader != null) {
            reader.close();
            reader = null;
        }

        return this;
    }

    @Override
    public int getCurrentLineCount() {
        return lineCount;
    }
}
