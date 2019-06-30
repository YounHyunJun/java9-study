package java9.flow.grokonez.simple;

import java9.reactive.flow.grokonez.simple.MyPublisher;
import java9.reactive.flow.grokonez.simple.MySubscriber;
import org.junit.Test;

public class MyPubSubTests {

    @Test
    public void pubsubTest() throws InterruptedException {
        MyPublisher publisher = new MyPublisher();
        MySubscriber subscriberA = new MySubscriber("A");
        MySubscriber subscriberB = new MySubscriber("B");

        publisher.subscribe(subscriberA);
        publisher.subscribe(subscriberB);

        publisher.waitUntilTerminated();
    }


}
