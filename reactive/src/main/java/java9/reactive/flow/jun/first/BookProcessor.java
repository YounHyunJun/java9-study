package java9.reactive.flow.jun.first;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookProcessor implements Flow.Processor<Integer, Book> {

    private List<Flow.Subscriber<? super Book>> subscribers = Collections.synchronizedList(new ArrayList<>());
    private int DEMAND = 3;
    int count = 0;
    Flow.Subscription subscription;
    private final BookRepository bookRepository;

    public BookProcessor(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Book> subscriber) {
        subscribers.add(subscriber);
        subscriber.onSubscribe(new BookProcessorSubscription(subscriber));
        count = DEMAND;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(DEMAND);
    }

    @Override
    public void onNext(Integer item) {
        for (Flow.Subscriber<? super Book> subscriber : subscribers) {
            subscriber.onNext(bookRepository.get(item));
        }

        count--;
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("processor Error araised");
    }

    @Override
    public void onComplete() {
        System.out.println("processor completed");
    }

    private class BookProcessorSubscription implements Flow.Subscription {

        private Flow.Subscriber subscriber;
        private AtomicBoolean isCancel;

        public BookProcessorSubscription(Flow.Subscriber subscriber) {
            this.subscriber = subscriber;
            isCancel = new AtomicBoolean(false);
        }

        @Override
        public void request(long n) {
            if (isCancel.get()) {
                return;
            } else {
                subscription.request(n);
            }
        }

        @Override
        public void cancel() {
            isCancel.set(true);
        }
    }


}
