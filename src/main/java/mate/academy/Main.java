package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        // initialize field values using setters or constructor
        Book book = new Book(1L, "Kobzar", BigDecimal.valueOf(25));
        Book book2 = new Book(2L, "Harry Potter", BigDecimal.valueOf(40));
        // test other methods from BookDao
        bookDao.create(book);
        bookDao.update(book2);
        bookDao.findAll();
        bookDao.findById(1L);
        bookDao.deleteById(book.getId());

    }
}
