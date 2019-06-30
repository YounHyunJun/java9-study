package java9.reactive.flow.baeldung;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;

public class  EndSubscriber<T> implements Flow.Subscriber<T> {

    private Flow.Subscription subscription;
    public List<T> consumedElements = new LinkedList<>();
    private boolean completed = false;

    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    public void onNext(T item) {
        System.out.println("Got : " + item);
        subscription.request(1);
    }

    public void onError(Throwable t) {
        t.printStackTrace();
    }

    public void onComplete() {
        System.out.println("Done");
        completed = true;
    }

    public boolean isCompleted() {
        return completed;
    }
}
