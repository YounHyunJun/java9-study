package java9.reactive.flow.grokonez.submission;

import java.util.Random;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

public class MySubscriber implements Flow.Subscriber<Object> {

    private static final String LOG_MESSAGE_FORMAT = "~~ Subscriber %s >> [%s] %s%n";
    private static final Random RANDOM = new Random();

    private Flow.Subscription subscription;
    private AtomicInteger count;

    private String name;
    private int DEMAND = 0;

    public MySubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        log("Subscribed...");
        this.subscription = subscription;

        request(DEMAND);
    }

    public void setDEMAND(int n) {
        this.DEMAND = n;
        count = new AtomicInteger(DEMAND);
    }

    public void request(int n) {
        log("request new " + n + " items...");
        subscription.request(n);
    }

    @Override
    public void onNext(Object item) {
        log("itemValue: " + item);
        if (count.decrementAndGet() == 0) {
            if (RANDOM.nextBoolean()) {
                request(DEMAND);
                count.set(DEMAND);
            } else {
                log("Cancel subscribe...");
                subscription.cancel();
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        log("Error: " + t.getMessage())
    }

    @Override
    public void onComplete() {
        log("Completed");
    }

    private void log(String message, Object... args) {
        String fullMessage = String.format(LOG_MESSAGE_FORMAT, this.name, currentThread().getName(), message);
        System.out.printf(fullMessage, args);
    }
}
