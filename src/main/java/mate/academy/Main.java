package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookService bookService = (BookService) injector
            .getInstance(BookService.class);

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Kobzar");
        book.setPrice(new BigDecimal("60.50"));
        System.out.println(bookService.create(book));
        book = bookService.findById(1L);
        book.setPrice(new BigDecimal("45.80"));
        Book updatedBook = bookService.update(book);
        System.out.println(updatedBook);
        System.out.println(bookService.deleteById(1L));
        bookService.findAll().forEach(System.out::println);
    }
}
