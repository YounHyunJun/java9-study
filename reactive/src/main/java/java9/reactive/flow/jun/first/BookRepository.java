package java9.reactive.flow.jun.first;

import java.util.Map;

public class BookRepository {

    public static Map<Integer, Book> books = null;

    static {
        books = Map.of(
                1, new Book(1, "정글북"),
                2, new Book(2, "곰"),
                3, new Book(3, "런던"),
                4, new Book(4, "홍콩"),
                5, new Book(5, "서울"),
                6, new Book(6, "잠자리"),
                7, new Book(7, "해가 뜨는 어느날"),
                8, new Book(8, "보고싶은날"),
                9, new Book(9, "하루"),
                10, new Book(10, "금요일")
        );
    }


    public Book get(Integer id) {
        return books.get(id);
    }

}
