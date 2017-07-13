package jurl.designpatterns.proxy;

public class Worker {

    private Interface subject;

    public Worker(Interface subject) {
        this.subject = subject;
    }

    public void run() {
        System.out.printf("proxy.serviceOne : %s\n", subject.serviceOne());
        System.out.printf("proxy.serviceOne : %s\n", subject.serviceOne());
        System.out.printf("proxy.serviceTwo : %s\n", subject.serviceTwo(666, "jurl"));
    }
}
