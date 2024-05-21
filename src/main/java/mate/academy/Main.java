package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookService bookService = (BookService) injector.getInstance(BookService.class);
        System.out.println(bookService.findById(1L));

        Book book = new Book("Mega Book", BigDecimal.valueOf(10.10));
        System.out.println(bookService.create(book));

        System.out.println(bookService.findAll());

        Book bookNew = new Book("Mega Book 2", BigDecimal.valueOf(100.10));
        bookNew.setId(1L);
        System.out.println(bookService.update(bookNew));

        System.out.println(bookService.deleteById(3L));
    }
}
