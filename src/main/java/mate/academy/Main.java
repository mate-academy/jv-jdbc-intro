package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final BookService BOOK_SERVICE = (BookService) INJECTOR
            .getInstance(BookService.class);

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Kobzar");
        book.setPrice(new BigDecimal("60.50"));
        System.out.println(BOOK_SERVICE.create(book));
        book = BOOK_SERVICE.findById(1L);
        book.setPrice(new BigDecimal("45.80"));
        Book updatedBook = BOOK_SERVICE.update(book);
        System.out.println(updatedBook);
        System.out.println(BOOK_SERVICE.deleteById(1L));
        BOOK_SERVICE.findAll().forEach(System.out::println);
    }
}
