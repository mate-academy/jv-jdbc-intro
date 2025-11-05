package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Tom Sawyer", new BigDecimal("300.77"));
        bookDao.create(book);
        bookDao.findById(1L);
        bookDao.findAll();
        book.setPrice(new BigDecimal("500.25"));
        bookDao.update(book);
        bookDao.deleteById(1L);
    }
}
