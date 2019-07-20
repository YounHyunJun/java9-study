package java9.reactive.flow.jun.first;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookPublisher implements Flow.Publisher<Integer> {

    private List<Flow.Subscriber> subscribers = Collections.synchronizedList(new ArrayList<>());
    private List<Integer> values = IntStream.iterate(0, i -> i + 1).limit(12).boxed().collect(Collectors.toList());
    private int loc = 0;

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        subscribers.add(subscriber);
        subscriber.onSubscribe(new BookSubscription(subscriber));
    }

    private class BookSubscription implements Flow.Subscription {

        private Flow.Subscriber<? super Integer> subscriber;
        private AtomicBoolean isCancel;

        public BookSubscription(Flow.Subscriber<? super Integer> subscriber) {
            this.subscriber = subscriber;
            isCancel = new AtomicBoolean(false);
        }

        @Override
        public void request(long n) {
            if (isCancel.get() || values.size() <= loc) {
                return;
            }

            if (n < 0) {
                subscriber.onError(new IllegalArgumentException());
            } else {
                for (int i = 0; i < n; i++) {
                    subscriber.onNext(values.get(loc));
                }
            }
        }

        @Override
        public void cancel() {
            isCancel.set(true);
        }
    }

}
