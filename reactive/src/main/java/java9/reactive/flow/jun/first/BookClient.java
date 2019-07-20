package java9.reactive.flow.jun.first;

public class BookClient {

    public static void main(String[] args) {

        BookPublisher bookPublisher = new BookPublisher();
        BookProcessor bookProcessor = new BookProcessor();
        BookSubscriber bookSubscriber = new BookSubscriber();

        bookPublisher.subscribe(bookProcessor);
        bookProcessor.subscribe(bookSubscriber);

    }
}
