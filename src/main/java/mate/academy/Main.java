package mate.academy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.impl.BookServiceImpl;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) throws SQLException {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        BookService bookService = new BookServiceImpl(bookDao);
        bookService.create(new Book(1L, "Effective java", new BigDecimal("590.52")));
        bookService.create(new Book(2L, "Thinking in java", new BigDecimal("1450.25")));
        Optional<Book> optionalBook = bookService.findBy(1L);
        bookService.update(new Book(1L, "Effective java 2", new BigDecimal("590.52")));
        bookService.deleteById(2L);
        List<Book> list = bookService.findAll();
        System.out.println(list);
    }
}
