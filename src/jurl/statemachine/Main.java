package jurl.statemachine;

public class Main {

    class TestHandler implements EventHandler {

        @Override
        public Event handleEvent(Event event) {
            return event;
        }
    }

    public static void main(String[] args) {

    }

    private Event method(Event event) {

        return event;
    }

    private void test(StateMachine sm) throws StateMachineException {

        sm.init("init", this::method)
            .to("A", new TestHandler()).when(Event.START)
            .from("A")
            .to("B", (event) -> { System.out.println(event.getName()); return event; })
            .when("e1")
            .to("C", new TestHandler())
            .when("e2")
            .from("B")
            .to("B")
            .when("e3")
            .to("A")
            .when("e4")
            .end("end", this::method);

        sm.commit();

    }
}
