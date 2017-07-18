package jurl.bot.context;

import java.util.HashMap;
import java.util.Map;

public class ArgumentsContainer implements Arguments {

    private Map<String, String> arguments;

    public ArgumentsContainer() {
        arguments = new HashMap();
    }

    public void clear() {
        arguments.clear();
    }

    public void set(String key, String value) {
        arguments.put(key, value);
    }

    @Override
    public String getString(String key) {
        return arguments.get(key);
    }

    @Override
    public int getInteger(String key) {

        try {
            return Integer.parseInt(arguments.get(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException(String.format("Wrong integer format in key '%s'", key));
        }
    }
}
