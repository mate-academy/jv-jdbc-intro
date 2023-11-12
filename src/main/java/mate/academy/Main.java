package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(new Book("Great book", new BigDecimal(2000)));
        bookDao.create(new Book("Normal book", new BigDecimal(1000)));
        bookDao.create(new Book("Awesome book", new BigDecimal(2500)));
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
        bookDao.update(new Book(2L,"Bad book", new BigDecimal(0)));
        bookDao.deleteById(3L);
        System.out.println(bookDao.findAll());
    }
}
