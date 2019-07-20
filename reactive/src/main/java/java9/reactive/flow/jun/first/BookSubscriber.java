package java9.reactive.flow.jun.first;

import java.util.concurrent.Flow;

public class BookSubscriber implements Flow.Subscriber<Book> {

    Flow.Subscription subscription;
    private int DEMAND;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(DEMAND);
    }

    @Override
    public void onNext(Book item) {
        System.out.println(item);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Subscriber Error");
    }

    @Override
    public void onComplete() {
        System.out.println("Subscriber completed");
    }

}
