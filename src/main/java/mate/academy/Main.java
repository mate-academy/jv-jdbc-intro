package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(1L, "Kobzar", BigDecimal.valueOf(25));
        bookDao.create(book);

        Book book2 = new Book(2L, "Harry Potter", BigDecimal.valueOf(40));
        bookDao.update(book2);

        bookDao.findAll();
        bookDao.findById(1L);
        bookDao.deleteById(1L);
    }
}
