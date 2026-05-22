package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.impl.BookServiceImpl;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        BookService bookService = new BookServiceImpl(bookDao);

        Book book = new Book();
        book.setTitle("Little Prince");
        book.setPrice(BigDecimal.valueOf(10.45));
        bookService.create(book);

        Optional<Book> foundBook = bookService.findById(1L);

        List<Book> books = bookService.findAll();

        bookService.deleteById(2L);

        book.setPrice(BigDecimal.valueOf(12.99));
        bookService.update(book);
    }
}
