package java9.flow.baeldung;

import java9.reactive.flow.baeldung.EndSubscriber;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;


public class FlowTests {

    @Test
    public void FlowTest() throws InterruptedException {

        // 주어진 조건
        SubmissionPublisher<String> publisher = new SubmissionPublisher<>();
        EndSubscriber<String> subscriber = new EndSubscriber<>();

        publisher.subscribe(subscriber);

        List<String> items = List.of("1", "x", "2", "x", "3", "x");

        assertThat(publisher.getNumberOfSubscribers()).isEqualTo(1);
        items.forEach(publisher::submit);
        publisher.close();

        await().until(subscriber::isCompleted);

    }

}
