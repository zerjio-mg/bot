package jurl.designpatterns.proxy;

public class Main {

    public static void main(String[] args) {

        Interface subjectA = new Subject("A");
        Interface subjectB = new Subject("B");

        Proxy proxy = new Proxy(subjectA);

        Worker worker = new Worker(proxy);

        worker.run();
        worker.run();

        proxy.setSubject(subjectB);

        worker.run();
        worker.run();
    }
}
