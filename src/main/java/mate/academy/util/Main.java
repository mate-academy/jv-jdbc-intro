package mate.academy.util;

import java.math.BigDecimal;
import mate.academy.util.dao.BookDao;
import mate.academy.util.domain.Book;
import mate.academy.util.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("The witcher", new BigDecimal("195.00"));
        bookDao.create(book);
        bookDao.create(book);
        bookDao.update(book);
        bookDao.deleteById(1);
        bookDao.findAll();
        bookDao.findById(2);
    }
}
