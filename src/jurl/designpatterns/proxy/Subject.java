package jurl.designpatterns.proxy;

public class Subject implements Interface {

    private int count;

    private String name;

    public Subject(String name) {
        count = 0;
        this.name = name;
    }

    public int serviceOne() {
        return ++ count;
    }

    public String serviceTwo(int value, String data) {

        return String.format("%s[%03d]-{%d}{%s}", name, count, value, data);
    }
}
