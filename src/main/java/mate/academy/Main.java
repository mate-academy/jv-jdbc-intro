package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Nice book");
        book.setPrice(new BigDecimal(300));

        BookService bookService = (BookService) injector.getInstance(BookService.class);

        bookService.create(book);
        bookService.create(book);
        bookService.create(book);

        bookService.get(book.getId());

        bookService.delete(book.getId() - 1);

        book.setTitle("Alpha Omega");
        book.setPrice(new BigDecimal(150));

        bookService.update(book);

        System.out.println(bookService.findAll());
    }
}
