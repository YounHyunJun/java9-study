package java9.reactive.flow.grokonez.simple;

import java.util.Random;
import java.util.concurrent.Flow;

public class MySubscriber implements Flow.Subscriber<Integer> {

    private static final String LOG_MESSAGE_FORMAT = "Subscriber %s >> [%s] %s%n";

    private static final int DEMAND = 3;
    private static final Random RANDOM = new Random();

    private String name;
    private Flow.Subscription subscription;

    private int count;

    public MySubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        log("Subscribed");
        this.subscription = subscription;

        count = DEMAND;
        requestItems(DEMAND);
    }

    private void requestItems(int n) {
        log("Requesting %d new Items...", n);
        subscription.request(n);
    }

    @Override
    public void onNext(Integer item) {
        if (item != null) {
            log(item.toString());

            synchronized (this) {
                count--;

                if (count == 0) {
                    if (RANDOM.nextBoolean()) {
                        count = DEMAND;
                        requestItems(count);
                    } else {
                        count = 0;
                        log("Cancelling subscription...");
                        subscription.cancel();
                    }
                }
            }
        } else  {
            log("Null Item!");
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log("Subscriber Error >> %s", throwable);
    }

    @Override
    public void onComplete() {
        log("Completed");
    }

    private void log(String message, Object... args) {
        String fullMessage = String.format(LOG_MESSAGE_FORMAT, this.name, Thread.currentThread().getName(), message);
        System.out.printf(fullMessage, args);
    }
}
