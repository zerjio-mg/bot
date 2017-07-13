import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jurl {

    public static void main(String[] args) {

        new Jurl().run();
    }

    public void run() {

        Map<String, String> map = build("uno", 100, "dos", 222, "tres", "pipu", "cuatro", new Integer(666));

        for(String key : map.keySet()) {
            System.out.printf("[%s] => [%s]\n", key, map.get(key));
        }
    }

    public Map<String, String> build(Object... pairKeyValue) {

        if ((pairKeyValue.length % 2) != 0) {
            throw new RuntimeException("ARGHHHHH");
        }

        Map<String, String> map = new HashMap<>();

        for(int index = 0; index < pairKeyValue.length; index +=2) {
            map.put(pairKeyValue[index].toString(), pairKeyValue[index+1].toString());
        }

        return map;
    }

    public <T> T generic(ArrayList<T> array, int index) {

        return array.get(index);
    }

    public void run_2() {

        System.out.println(request(this::pim, "a", "b"));
        System.out.println(request(this::pam, "c", "d"));
    }

    private String request(BiFunction<String, String, String> operation, String one, String two) {

        return operation.apply(one, two);
    }

    private String pim(String one, String two) {
        return String.format("PIM[%s, %s]", one, two);
    }

    private String pam(String one, String two) {
        return String.format("PAM[%s, %s]", one, two);
    }

    public void run_1() {

        Map requestParameters = new HashMap();

        requestParameters.put("gameID", "GAME_ID");
        requestParameters.put("userID", "USER_ID");

        String uri = buildUri("pimpampum", requestParameters);

        System.out.printf("URI [%s]\n", uri);
    }

    private String buildUri(String method, Map<String, String> requestParameters) {

        String resource = getResource();

        StringBuffer resourceParsed = new StringBuffer();

        Pattern pattern = Pattern.compile("(\\{(\\w*)\\})");

        Matcher m = pattern.matcher(resource);
        while (m.find()) {

            String key = m.group(2);

            if (!requestParameters.containsKey(key)) {
                throw new RuntimeException(String.format("URI key {%s} not found", key));
            }

            m.appendReplacement(resourceParsed, Matcher.quoteReplacement(requestParameters.get(key)));
        }

        m.appendTail(resourceParsed);

        return String.format("%s://%s:%s/%s/%s",
                "http",
                "localhost",
                "8000",
                resourceParsed.toString(),
                method
        );
    }

    String getResource() {
        return "game/{gameID}/user/{userID}";
    }
}
