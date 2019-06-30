package java9.reactive.flow.grokonez.submission;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static java.lang.Thread.currentThread;

public class MyPublisher extends SubmissionPublisher<Integer> {

    private static final String LOG_MESSAGE_FORMAT = "Publisher >> [%s] %s%n";
    private final int MAX_ITEM_TO_PUBLISH = 5;

    private final ScheduledFuture<?> periodicTask;
    private final ScheduledExecutorService scheduler;

    private final AtomicInteger i;

    Supplier<? extends Integer> supplier = new Supplier<>() {
        @Override
        public Integer get() {
            return i.incrementAndGet();
        }
    };

    public MyPublisher(Executor executor, int maxBufferCapacity, long period, TimeUnit unit) {
        super(executor, maxBufferCapacity);

        i = new AtomicInteger(0);

        scheduler = new ScheduledThreadPoolExecutor(1);
        periodicTask = scheduler.scheduleAtFixedRate(() -> {
            Integer item = supplier.get();

            log("estimateMaximumLog: " + super.estimateMaximumLag());
            log("estimateMinimumDemand: " + super.estimateMinimumDemand());

            if (item == MAX_ITEM_TO_PUBLISH) {
                close();
            }
        }, 0, period, unit);

    }

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        super.subscribe(subscriber);
    }

    @Override
    public void close() {
        log("shutting down...");

        List<Flow.Subscriber<? super Integer>> subscribers = getSubscribers();
        for (Flow.Subscriber<? super Integer> subscriber : subscribers) {
            log("Subscriber " + subscriber.toString() + " isSubscribed(): " + isSubscribed(subscriber));
        }

        periodicTask.cancel(false);
        scheduler.shutdown();

        super.close();
    }

    private void log(String message, Object... args) {
        String fullMessage = String.format(LOG_MESSAGE_FORMAT, currentThread().getName(), message);
        System.out.printf(fullMessage, args);
    }

}
